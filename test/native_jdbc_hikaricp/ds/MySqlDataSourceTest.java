package native_jdbc_hikaricp.ds;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class MySqlDataSourceTest {
  protected static Logger logger = LogManager.getLogger();
  
	@Test
	public void testGetConnection() {
		try(Connection con = MySqlDataSource.getConnection()) {
			logger.debug(con);
			Assert.assertNotNull(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
