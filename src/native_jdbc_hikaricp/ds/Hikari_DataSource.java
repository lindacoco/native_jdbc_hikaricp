package native_jdbc_hikaricp.ds;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Hikari_DataSource {
   private static HikariConfig config;
   private static HikariDataSource ds;
   
   
   static {
	   config = new HikariConfig();
	   config.setJdbcUrl("jdbc:mysql://localhost/mysql_study?useSSL=false");
	   config.setUsername("user_mysql_study");
       config.setPassword("rootroot");
       config.addDataSourceProperty("cachePrepStmts", true);
       config.addDataSourceProperty("prepStmtCacheSize", "250");
       config.addDataSourceProperty("prepStmtCacheSqllimit", "2048");
       ds = new HikariDataSource(config);
   }
   
   private Hikari_DataSource() {} //외부에서 new DataSource()못하게 막는것
     public static Connection getConnection() throws SQLException{
    	 return ds.getConnection();
     }
}
