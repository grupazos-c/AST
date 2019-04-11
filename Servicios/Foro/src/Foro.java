
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.sql.*;

public class Foro {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/Foro?useSSL=false";

	private static final String USER = "cliente";
	private static final String PASS = "fBys{iB198Ha";

	private static final String PREFIX = "[ Foro ]: ";

	private static Connection conn = null;
	private static Statement stmt = null;

    /**Almacen de posts, la key es el postID */
    private static HashMap<Integer, Post> posts = new HashMap<Integer, Post>();

    /**
     * Función de subida de Post
     * @param post cadena con el post a subir
     * @param tags etiquetas con las que se asociarán el post
     * @return postID del post subido
     */
    public int subirPost( String post, ArrayList<String> tags) {
        if (post == null) {
            return 0;
        }
        nuevoPost(post,tags);
        int key = posts.size() + 1;
        Post nuevoPost = new Post(post,tags);
        posts.put(key, nuevoPost);
        for (String var : tags){
            if(var.equalsIgnoreCase("noticia")) {
                try {
					noticia("Un usuario anónimo",post);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(PREFIX + "La conexión con Noticia ha dado un error");
					return -1;
				}
            }
        }
        return key;
    }

    /**
     * Función de lectura de post
     * @param postID Id del post a leer
     * @return post leído
     */
    public Post leerPost(int postID) {
        Post post = posts.get(postID);
        return post;
    }
    
    public ArrayList<Integer> buscarXTag(String busqueda) {
        ArrayList<Integer> respuesta = new ArrayList<Integer>();
        Iterator<Entry<Integer, Post>> it = posts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Post> pair = (Map.Entry<Integer, Post>)it.next();
            ArrayList<String> tags = pair.getValue().getTags();
            for (String var : tags) {
                if (var.equalsIgnoreCase(busqueda)) {
                    respuesta.add(pair.getKey());
                }
            }
        }
        return respuesta;
    }

    public ArrayList<Integer> buscar(String busqueda) {
        ArrayList<Integer> respuesta = new ArrayList<Integer>();
        Iterator<Entry<Integer, Post>> it = posts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Post> pair = (Map.Entry<Integer, Post>)it.next();
            if (pair.getValue().getPost().contains(busqueda)) {
                respuesta.add(pair.getKey());
            }
        }
        return respuesta;
    }   
    
    /**
     * Cuenta los posts subidos
     * @return
     * @throws SQLException 
     */
    private int countPosts() throws SQLException {
    	
    	String SQL = "{call countPosts (?)}";
    	CallableStatement cstmt = conn.prepareCall (SQL);
        cstmt.registerOutParameter(1, Types.INTEGER);

        cstmt.executeUpdate();

        int output = cstmt.getInt(1);

        System.out.println(PREFIX + "Resultado de la llamada --> " +output);
		return (output);
	}

    /**
     * Conexión con el servicio Notcia para subir nuestro post como notica
     *    //TODO El titular aún no está definido
     * @param titular
     * @param cuerpo
     * @throws Exception
     */
    private static void noticia(String titular, String cuerpo) throws Exception {
        
    	String encodedURL=java.net.URLEncoder.encode(titular, "UTF-8");
		URL url = new URL("http://localhost:7162/axis2/services/Noticia/setTitular?titular=" + encodedURL);
        System.out.println(PREFIX +"Paquete HTTP creado con URL: " + url.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        int status = con.getResponseCode();
        System.out.println(PREFIX + "Salida de titulo: " + status);

        encodedURL=java.net.URLEncoder.encode(cuerpo, "UTF-8");
        url = new URL("http://localhost:7162/axis2/services/Noticia/setDescripcion?descripcion=" + encodedURL);
        System.out.println(PREFIX +"Paquete HTTP creaso con URL: " + url.toString());
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

       status = con.getResponseCode();
       System.out.println(PREFIX + "Salida de descripcion: " + status);

       return;
    }
    /**
     * Inicio de conexion con servidor SQL
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private static void init() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);

        System.out.println(PREFIX + "Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        System.out.println(PREFIX + "Creating statement...");
        stmt = conn.createStatement();
    }
    /**
     * Cierre de conexión con el servidor SQL
     * @throws SQLException
     */
    private static void close() throws  SQLException{
    	 stmt.close();
         conn.close();
    }
    
    /**
     * Guardar el Post en SQL
     * @param post
     * @param tags
     */
    private void nuevoPost(String post, ArrayList<String> tags) {
    	try {
    		init();			//Comenzamos la conexión y nos aseguramos que todo funcione
    		int numposts = countPosts() + 1;
        	String comando; //comando a ejecutar en mySQL
        	comando = ("insert into Posts values ('" + numposts + "', '" + post + "');");
        	
        	stmt.executeUpdate(comando);
        	
        	for (Iterator<String> iterator = tags.iterator(); iterator.hasNext();) {
				String tag = (String) iterator.next().toUpperCase();
				
				
				comando = ("insert into Posts values ('" + numposts + "', '" + tag + "');");
	        	
	        	stmt.executeUpdate(comando);
			}
        	
    	}catch(ClassNotFoundException e){
    		System.out.println(PREFIX + "ERROR: El JDBC_Driver no funciona correctamente");
    		e.printStackTrace();
    	}catch (SQLException e) {
    		System.out.println(PREFIX + "ERROR: La conexión con SQL ha fracasado");
    		e.printStackTrace();    		
		}
  		
  	}
    
}