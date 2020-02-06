package native_jdbc.ui.service;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.dto.LogUtil;
   @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest {
     private static TransactionService service;
   
   
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()); //TRACE [main] - [Ljava.lang.StackTraceElement;@5906ebcb
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[0]);//TRACE [main] - java.lang.Thread.getStackTrace(Thread.java:1559)
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName());//TRACE [main] - setUpBeforeClass
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		 service = new TransactionService();	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = null;		
	} 

	@Test
	public void test01TransAddEmpAndDept_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res =0;
		Department dept = new Department(1,"마케팅1",8); //존재하는 부서
		Employee emp = new Employee(1004, "수지1", "사원", new Employee(1003), 1500000, dept);
		
		//employee가 
		
		service.transAddEmpAndDept(emp, dept);
		
		Assert.assertNotEquals(2, res); //2가아니라면 성공
	}
	@Test
	public void test02TransAddEmpAndDept_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res =0;
		Department dept = new Department(5,"마케팅2",8); 
		Employee emp = new Employee(4377, "수지2", "사원", new Employee(1003), 1500000, dept); //예외뜸
		
		
		service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test03TransAddEmpAndDept_Success() { //둘다 실패하면 성공 
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(5,"마케팅3",8); 
		Employee emp = new Employee(1004, "수지3", "사원", new Employee(1003), 1500000, dept); //예외뜸
		
		
		int res = service.transAddEmpAndDept(emp, dept);
		Assert.assertEquals(2, res); 
	}

	@Test
	public void test04TransAddEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res =0;
		Department dept = new Department(1,"어려워1",8); //존재하는 부서
		Employee emp = new Employee(1009, "현서1", "사원", new Employee(1003), 1500000, dept);
		
		//employee가 
		
		service.transAddEmpAndDeptWithConnection(emp, dept);
		
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test05TransAddEmpAndDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		int res =0;
		Department dept = new Department(6,"어려워2",8); 
		Employee emp = new Employee(4377, "현서2", "사원", new Employee(1003), 1500000, dept); //예외뜸
		
		
		service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test06TransAddEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(6,"어려워3",8); //존재하는 부서
		Employee emp = new Employee(1009, "현서3", "사원", new Employee(1003), 1500000, dept); //예외뜸
		
		
		int res = service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertEquals(2, res); 
	}
	
	
	
	@Test
	public void test07transRemoveEmpAndDept_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
        int res=0;
		
		Employee emp = new Employee(4377); //사장  매니저로 키 참고중
		Department dept = new Department(6); //참고하는 사람없음 
	
		service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test08transRemoveEmpAndDept_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
        int res=0;
		
		Employee emp = new Employee(1009); //현서
		Department dept = new Department(5); //수지3이 참고할것
	
		service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test09transRemoveEmpAndDept_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		
		Employee emp = new Employee(1009);  //현서3
		Department dept = new Department(6); //참고하는 사람없음 
	
		int res=service.transRemoveEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	
	@Test
	public void test10transRemoveEmpAndDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
        int res=0;
		
		Employee emp = new Employee(4377); //사장  매니저로 키 참고중
		Department dept = new Department(9); //참고하는 사람없음 
	
		service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test11transRemoveEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
        int res=0;
		
		Employee emp = new Employee(1004); //수지3
		Department dept = new Department(0); //수지3이 참고할것
	
		service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	@Test
	public void test12transRemoveEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		
		Employee emp = new Employee(1004); 
		Department dept = new Department(5); //수지3이참고하던 부서
	
		int res=service.transRemoveEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}

}
