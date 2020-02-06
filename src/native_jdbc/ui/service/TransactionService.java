package native_jdbc.ui.service;

import java.io.EOFException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.daoImpl.DepartmentDaoImpl;
import native_jdbc_hikaricp.daoImpl.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.dto.LogUtil;

public class TransactionService {
	
	
	private String deptSql ="insert into department values(?,?,?)";
	private String empSql= "insert into employee(empno,empname,title,manager,salary,dno) values(?,?,?,?,?,?)";
	private String empDelSql ="delete from employee where empno =?";
	private String deptDelSql ="delete from department  where  deptno = ? ";
	//add 하는 것 
    public int transAddEmpAndDept(Employee emp, Department dept) {
    	
    	int res =0;
    	Connection con =null;
    	
    	PreparedStatement pstmt =null;
		try {
			con = MySqlDataSource.getConnection();
		} catch (SQLException e1) { //연결이 안될때
		//	e1.printStackTrace();
		}
    	try{
    //Department 인서트문장//사원을 입력할때 해당 부서가 있어야 함 
    		con.setAutoCommit(false); //반드시
			pstmt = con.prepareStatement(deptSql);
    		pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
//			System.out.println(pstmt);
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
    //employee
    	  pstmt = con.prepareStatement(empSql);
    	  pstmt.setInt(1, emp.getEmpNo());
    	  pstmt.setString(2, emp.getEmpName());
    	  pstmt.setString(3, emp.getTitle());
    	  pstmt.setInt(4, emp.getManager().getEmpNo());
    	  pstmt.setInt(5, emp.getSalary());
    	  pstmt.setInt(6, emp.getDept().getDeptNo());
    	   res += pstmt.executeUpdate(); //누적
    	   if(res == 2) {
    		   con.commit(); //성공했기때문에 commit
//    		   con.setAutoCommit(true);
    		   LogUtil.prnLog("result"+res+"commit");
    	   }else { //둘중 하나 실패 혹은 둘 다 실패
    		   throw new SQLException(); //여기서 ROLLBACK하는게 아님
    	   }
    	} catch (SQLException e) {
			try {
				con.rollback();
//	    		   con.setAutoCommit(true);	
				LogUtil.prnLog("result "+res+" rollback");
				
			} catch (SQLException e1) {
			//	e1.printStackTrace();
			}
		}finally {
			try {con.setAutoCommit(true);
			    pstmt.close();
			    con.close();
			     
			} catch (SQLException e) {}
		}
    	return res; //에러안뜨게 -1했다가 res추가 후 res로 바꿈 
    }
    
    public int transAddEmpAndDeptWithConnection(Employee emp, Department dept) {
    	DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
    	EmployeeDao empDao = EmployeeDaoImpl.getInstance();
    	int res = 0;
    	Connection con =null;
        try {
        	   con = MySqlDataSource.getConnection();
		      con.setAutoCommit(false);
	
		      res = deptDao.insertDepartment(con, dept);
		      res += empDao.insertEmployee(con, emp);
		      
		      if(res ==2) {
		    	  con.commit();
		    	  con.setAutoCommit(true);
		    	  LogUtil.prnLog("result"+res+"commit");
		      }else {
		    	  throw new SQLException();
		      }
		} catch (SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result "+res+" rollback");
//				throw e;
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
    	return res;
    }
    //삭제하는것 
    public int transRemoveEmpAndDept(Employee emp, Department dept) {
         //1.사원 삭제 
    	//2.부서 삭제
    	int res =0;
    Connection con = null;
    PreparedStatement pstmt = null;
     try {
    	 con= MySqlDataSource.getConnection();
    	 con.setAutoCommit(false);
    	 pstmt = con.prepareStatement(empDelSql);
    	 pstmt.setInt(1, emp.getEmpNo());
    	 LogUtil.prnLog(pstmt);
    	 res = pstmt.executeUpdate();
    	 
    	 //부서삭제
    	 pstmt = con.prepareStatement(deptDelSql);
    	 pstmt.setInt(1, dept.getDeptNo());
    	 LogUtil.prnLog(pstmt);
    	 res+= pstmt.executeUpdate();
    	 
    	 
    	 if(res == 2) {
    		 con.commit();
    		 con.setAutoCommit(true);
    		 LogUtil.prnLog("res는 총"+res+"이고 commit");
    	 }else {
    		 throw new SQLException();
    		 
    	 }
    	 
     }catch(SQLException e) {
    	 try {
			con.rollback();
			con.setAutoCommit(true);
			LogUtil.prnLog("res는"+res+"이고 rollback");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     }
    	
    	 return res;
    }
    
    
    public int transRemoveEmpAndDeptWithConnection(Employee emp, Department dept) {
        //1.사원 삭제 
   	     //2.부서 삭제
    	DepartmentDao deptDao = DepartmentDaoImpl.getInstance();
    	EmployeeDao empDao = EmployeeDaoImpl.getInstance();
    	int res =0;
    	Connection con = null;
    	
    	try {
    		 con = MySqlDataSource.getConnection();
    		 con.setAutoCommit(false);
    		 res= empDao.deleteEmployee(con, emp);
    		 res += deptDao.deleteDepartment(con, dept);
    		 if(res == 2) {
        		 con.commit();
        		 con.setAutoCommit(true);
        		 LogUtil.prnLog("res는 총"+res+"이고 commit");
        	 }else {
        		 throw new SQLException();
        		 
        	 }	 
  	
    	}catch(SQLException e) {
    		 try {
				con.rollback();
				con.setAutoCommit(true);
				LogUtil.prnLog("res는"+res+"이고 rollback");
			} catch (SQLException e1) {
	
				e1.printStackTrace();
			}
    		
    	}
   	
   	 return res;
   }
  
}
