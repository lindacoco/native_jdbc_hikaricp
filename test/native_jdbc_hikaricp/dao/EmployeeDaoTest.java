package native_jdbc_hikaricp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_hikaricp.daoImpl.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.dto.LogUtil;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private Connection con;
	private static EmployeeDao dao;
	private static File imagesDir;
	// 가져올 폴더
	private static File picDir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(" setUpBeforeClass");
		dao = EmployeeDaoImpl.getInstance();

		picDir = new File(System.getProperty("user.dir") + File.separator + "pics" + File.separator);
		if (!picDir.exists()) {
			picDir.mkdir(); // pic폴더를 자동으로 만들어줌
		}
		imagesDir = new File(System.getProperty("user.dir") + File.separator + "imgs" + File.separator);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(" tearDownAfterClass");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		LogUtil.prnLog(" setUp");
		con = MySqlDataSource.getConnection();

	}

	@After
	public void tearDown() throws Exception {
		LogUtil.prnLog("tearDown");
		con.close();
	}

	@Test
	public void test01SelectEmployeeByEmpNo() {
		LogUtil.prnLog("test01SelectEmployeeByEmpNo");
		Employee emp = new Employee(1999);
		try {
			Employee selectedEmp = dao.selectEmployeeByEmpNo(con, emp);
			if (selectedEmp.getPic() != null) {
				getImageToPic(selectedEmp.getPic(), selectedEmp.getEmpNo()); // 프로젝트 폴더의 picDir폴더에 사원번호.jpg파일이 생성되도록하는
																				// 메소드
			}

			Assert.assertNotNull(selectedEmp);
			LogUtil.prnLog(selectedEmp);
		} catch (RuntimeException e) {
			LogUtil.prnLog(e.getMessage());
			e.printStackTrace();
		}

	}

	private void getImageToPic(byte[] pic, int empNo) {
		File file = new File(picDir, empNo + ".jpg"); // 이름을 jpg로 픽스함
		try (FileOutputStream fis = new FileOutputStream(file)) {
			fis.write(pic);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectEmployeeGroupByDno() {
		Department dept = new Department();
		dept.setDeptNo(1);
		List<Employee> list;
		try {
			list = dao.selectEmployeeGroupByDno(con, dept);
			Assert.assertNotEquals(0, list.size());
			for (Employee e : list)
				LogUtil.prnLog(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test02InsertEmployee() throws SQLException {
		LogUtil.prnLog("testInsertDepartment");
		List<Employee> lists = dao.selectEmployeeByAll(con);
		Assert.assertNotEquals(0, lists.size());
		for (Employee e : lists)
			LogUtil.prnLog(e);

		Employee emp = new Employee(1005, "수지", "사원", new Employee(1003), 1500000, new Department(1),
				getImage("suji.jpg"));
		LogUtil.prnLog(emp);
		int res = dao.insertEmployee(con, emp);
		Assert.assertEquals(res, 1);

	}

	private byte[] getImage(String imgName) {
		File file = new File(imagesDir, imgName);

		try (InputStream is = new FileInputStream(file)) {
			byte[] pic = new byte[is.available()];
			is.read(pic);
			return pic;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void test03UpdateEmployee()  {
//		logger.debug(" test03UpdateDepartment");
//		logger.debug("testInsertDepartment");
//		Employee emp = new Employee(1004,"이유영","대리",new Employee(4377),1000000,new Department(1),getImage("lyy.jpg"));
//		
//		try{  
//			  int res = dao.updateEmployee(con, emp);
//			  Assert.assertEquals(1, res);
//			  logger.trace(res);
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
		  
				
           
	}

	@Test
	public void test04DeleteEmployee() throws SQLException {
		LogUtil.prnLog(" test04DeleteDepartment");
		Employee emp = new Employee(1004);
		int res = dao.deleteEmployee(con, emp);
		LogUtil.prnLog(res);
		Assert.assertEquals(1, res);
		

	}
	
	@Test
	public void test06procedureEmployeeByDno() throws SQLException {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		LogUtil.prnLog(" test04DeleteDepartment");
		List<Employee> list =dao.procedureEmployeeByDno(con,2);
		
		Assert.assertNotEquals(0, list.size());
		
		for(Employee e : list) {
			LogUtil.prnLog(e);
		}
		

	}
	
	



}
