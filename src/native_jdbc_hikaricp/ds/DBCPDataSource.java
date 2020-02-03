package native_jdbc_hikaricp.ds;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();
    
    static {
    	
	    try(InputStream is = ClassLoader.getSystemResourceAsStream("dbcp.properties")){
	    	Properties prop = new Properties();
	    	prop.loadFromXML(is);
	    	
	    	
	    	
	    	ds.setUrl(prop.getProperty("jdbcUrl"));
	    	ds.setUsername(prop.getProperty("dbUser"));
	    	ds.setPassword(prop.getProperty("dbPwd"));
	    	ds.setMinIdle(Integer.parseInt(prop.getProperty("minIdle")));
	    	ds.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
	    	ds.setMaxOpenPreparedStatements(Integer.parseInt(prop.getProperty("MaxOpenPrepareStatements")));
	    	
	    }catch (IOException e) {
	    	System.err.println(e.getMessage());
	    }
   }
   
   private DBCPDataSource() {} //외부에서 new DataSource()못하게 막는것
     public static Connection getConnection() throws SQLException{
    	 return ds.getConnection();
     }
    
    
}
