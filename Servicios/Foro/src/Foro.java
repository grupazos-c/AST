
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.sql.*;

public class Foro {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/Foro?useSSL=false";

	private static final String USER = "cliente";
	private static final String PASS = "fBys{iB198Ha";

	private static final String PREFIX = "[ Foro ]: "; //Defino esta variable para colocarla antes de todos los logs (para evitar el caos del Catalina.out

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
        //TODO Si incluimos nuevo post borrar el hash map
        int salida = nuevoPost(post,tags);
        
        if(salida > 0) {
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
        }
        return salida;
    }

    /**
     * Función de lectura de post
     * @param postID Id del post a leer
     * @return post leído
     */
    public Post leerPost(int postID) {
    	//TODO to SQL
        Post post = posts.get(postID);
        return post;
    }
    
    public ArrayList<Integer> buscarXTag(String busqueda) {
    	//TODO TO SQL
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
    	//TODO To SQL
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
        System.out.println(PREFIX + "Post actualizado a noticia con título: " + titular + "\n\t Status: " + status);

        encodedURL=java.net.URLEncoder.encode(cuerpo, "UTF-8");
        url = new URL("http://localhost:7162/axis2/services/Noticia/setDescripcion?descripcion=" + encodedURL);
        System.out.println(PREFIX +"Paquete HTTP creaso con URL: " + url.toString());
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

       status = con.getResponseCode();
       System.out.println(PREFIX + "Post actualizado a noticia con cuerpo: " + cuerpo + "\n\t Status: " + status);

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
     * Guardar el Post en SQL
     * @param post
     * @param tags
     */
    private int nuevoPost(String post, ArrayList<String> tags) {
    	try {
    		init();			//Comenzamos la conexión y nos aseguramos que todo funcione
    		int numposts = countPosts() + 1;
    		
    		conn.setAutoCommit(false);
    		conn.commit();
    		
    		try {
        	String comando; //comando a ejecutar en mySQL
        	comando = ("insert into Posts values ('" + numposts + "', '" + post + "');");
        	
        	stmt.executeUpdate(comando);
        	
        	for (Iterator<String> iterator = tags.iterator(); iterator.hasNext();) {
				String tag = (String) iterator.next().toUpperCase();
				
				
				comando = ("insert into Tags values ('" + numposts + "', '" + tag + "');");
	        	
	        	stmt.executeUpdate(comando);
			}
    		}catch(SQLException e) {
    			conn.rollback();
    		}
        	conn.commit();
        	conn.setAutoCommit(true);
        	
        	close();
        	
        	return numposts;
        	
    	}catch(ClassNotFoundException e){
    		System.out.println(PREFIX + "ERROR: El JDBC_Driver no funciona correctamente");
    		e.printStackTrace();
    		return -2; //Error del servicio
    	}catch (SQLException e) {
    		System.out.println(PREFIX + "ERROR: SQL Exception");
    		e.printStackTrace();   
    		return -1; //Error del usuario? (Etiquetas no permitidas TODO Uso de ; en el post?? podemos cambiar el delimiter para que no pase esto?
		}
  		
  	}
    
}