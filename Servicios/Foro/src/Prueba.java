import java.sql.*;

public class Prueba {
	
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	    static final String DB_URL = "jdbc:mysql://localhost/Foro?useSSL=false";


	    static final String USER = "cliente";
	    static final String PASS = "fBys{iB198Ha";

	    public static void main(String[] args) {
	        Connection conn = null;
	        Statement stmt = null;
	        try{

	            Class.forName(JDBC_DRIVER);


	            System.out.println("Connecting to database...");
	            conn = DriverManager.getConnection(DB_URL,USER,PASS);

	            conn.setAutoCommit(false);
	            
	            conn.commit();
	            
	            System.out.println("Creating statement...");
	            stmt = conn.createStatement();
	            String[] sql=new String[5];
	            sql[0] = "insert into Tags values ('1', 'Bemvido');";
	            sql[0] = "insert into Tags values ('1', 'Welcome');";
	            sql[0] = "insert into Tags values ('1', 'Benvite');";
	            sql[0] = "insert into Tags values ('1', 'Buenoyano?');";
	            sql[0] = "insert into Tags values ('1', 'Bienvenido');";

	            for (int i = 0; i <sql.length ; i++) {
	                int ok = stmt.executeUpdate(sql[i]);
	                System.out.println("Query OK, "+ok+" row afected ("+i+")");
	            }

	            conn.commit();
	            
	            conn.setAutoCommit(true);


	            stmt.close();
	            conn.close();
	        }catch(SQLException se){
	            se.printStackTrace();
	        }catch(Exception e){
	            e.printStackTrace();
	        }finally{

	            try{
	                if(stmt!=null)
	                    stmt.close();
	            }catch(SQLException se2){
	            }
	            try{
	                if(conn!=null)
	                    conn.close();
	            }catch(SQLException se){
	                se.printStackTrace();
	            }
	        }
	        System.out.println("Goodbye!");
	    }

}
