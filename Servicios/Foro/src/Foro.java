
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.sql.*;

/**
 * Esta clase declarará un foro que ofertará los siguientes servicios:
 * -SubirPost: Guardará el post enviado, junto con sus etiquetas en una base de datos SQL y devolverá el ID de dicho post en caso de que se guarde correctamente
 * -LeerPost: Buscará en la BD SQL el contenido de un post y sus respectivos tags a partir de su ID
 * -BuscarPost: Buscará todos los posts qeu contengan una cadena
 * -BuscarXTag: buscará todos los posts que compartan un tag
 * 
 * @author VigoCoffeeLovers
 * @version 1.0
 *
 */
public class Foro {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/Foro?useSSL=false";

	private static final String USER = "cliente";
	private static final String PASS = "fBys{iB198Ha";

	private static final String PREFIX = "[ Foro ]: "; //Defino esta variable para colocarla antes de todos los logs (para evitar el caos del Catalina.out

	private static Connection conn = null;
	private static Statement stmt = null;


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
    	try {
    		init();			//Comenzamos la conexión y nos aseguramos que todo funcione
    		
        	String comando; //comando a ejecutar en mySQL
        	comando = ("select Post from Posts where Post_ID = " + postID + ";");
        	
        	ResultSet rs = stmt.executeQuery(comando);
        	
        	rs.next();
        	String postchar = rs.getString(1);
        	
        	comando = ("select Tag from Tags where Post_ID = " + postID +";");
        	
        	rs = stmt.executeQuery(comando);
        	
        	ArrayList<String> tags = new ArrayList<String>();
        	
        	while (rs.next()) {
            	String tag = rs.getString(1);
            	tags.add(tag);
        	}
        	
        	close();
        	
        	Post post = new Post(postchar, tags);
        	return post;
        	
    	}catch(ClassNotFoundException e){
    		System.out.println(PREFIX + "ERROR: El JDBC_Driver no funciona correctamente");
    		e.printStackTrace();
    	}catch (SQLException e) {
    		System.out.println(PREFIX + "ERROR: SQL Exception");
    		e.printStackTrace();  
		}
        return null;
    }
    
    /**
     * Busqueda de posts por tag
     * @param busqueda etiqueta a buscar
     * @return posts que contienen dicha etiqueta
     */
    public ArrayList<Integer> buscarXTag(String busqueda) {
    	try {
    		init();			//Comenzamos la conexión y nos aseguramos que todo funcione
        	
        	String comando = ("select Posts.Post_ID from Posts inner join Tags on Tags.Post_ID = Posts.Post_ID where Tag = '" + busqueda.trim() +"' group by Post_ID;");
        	
        	ResultSet rs = stmt.executeQuery(comando);
        	
        	ArrayList<Integer> posts = new ArrayList<Integer>();
        	
        	while (rs.next()) {
            	int post = rs.getInt(1);
            	posts.add(post);
        	}
        	
        	close();
        	
        	return posts;
        	
    	}catch(ClassNotFoundException e){
    		System.out.println(PREFIX + "ERROR: El JDBC_Driver no funciona correctamente");
    		e.printStackTrace();
    	}catch (SQLException e) {
    		System.out.println(PREFIX + "ERROR: SQL Exception");
    		e.printStackTrace();  
		}
        return null;
    }

    /**
     * Busqueda de posts mediante cadena
     * @param busqueda cadena a buscar
     * @return posts que contengan dicha cadena (solo en el cuerpo)
     */
    public ArrayList<Integer> buscar(String busqueda) {
    	try {
    		init();			//Comenzamos la conexión y nos aseguramos que todo funcione
        	
        	String comando = ("select Post_ID from Posts where Post like '%" + busqueda.trim() + "%';");
        	
        	ResultSet rs = stmt.executeQuery(comando);
        	
        	ArrayList<Integer> posts = new ArrayList<Integer>();
        	
        	while (rs.next()) {
            	int post = rs.getInt(1);
            	posts.add(post);
        	}
        	
        	close();
        	
        	return posts;
        	
    	}catch(ClassNotFoundException e){
    		System.out.println(PREFIX + "ERROR: El JDBC_Driver no funciona correctamente");
    		e.printStackTrace();
    	}catch (SQLException e) {
    		System.out.println(PREFIX + "ERROR: SQL Exception");
    		e.printStackTrace();  
		}
        return null;
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