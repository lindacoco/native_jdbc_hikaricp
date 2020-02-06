package native_jdbc_hikaricp.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.dto.LogUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	protected static Logger logger = LogManager.getLogger();
	
	
	//싱글톤
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();

	
	private EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	




	public static EmployeeDaoImpl getInstance() {
		return instance;
	}





	
	

	@Override
	public List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException {
		String sql ="select  e.empno, e.empname, e.title, m.empname manager_name, e.manager as manager_no , e.salary, e.dno, deptname \r\n" + 
				"from employee e left join employee m on e.manager = m.empno left join department d on e.dno = d.deptno\r\n" + 
				"where e.dno =?;"; //퀘스쳔마크로 들어감 
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt =con.prepareStatement(sql);){  //select는 무조건 ResultSet으로 받아야한다. autoclosable이라서 ?안에 값을 설정할 수 없다. 
				pstmt.setInt(1,dept.getDeptNo());
				System.out.println(pstmt);
			 try(ResultSet rs = pstmt.executeQuery()){
				 while(rs.next()) {
					 list.add(getEmloyeeFull(rs));
				 }
			}
		}
		return list;
	}
	
	
	private Employee getEmloyeeFull(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager_no"));
		manager.setEmpName(rs.getString("manager_name"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
		dept.setDeptName(rs.getString("deptname"));
		return new Employee(empNo, empName, title, manager, salary, dept);
		
	}

	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		String sql ="select  empno, empname, title, manager, salary, dno from employee e ";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt =con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			logger.trace(pstmt);
			while(rs.next()) {
				list.add(getEmloyee(rs, false));
			}
		}
		return list;
	}
	private Employee getEmloyee(ResultSet rs,boolean isPic) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
	
		Employee employee = new Employee(empNo, empName, title, manager, salary, dept);
		if(isPic) {
			employee.setPic(rs.getBytes("pic"));
		}
		return employee;
	}





	@Override
	public int insertEmployee(Connection con, Employee employee) throws SQLException {
	      String sql = null;
	      //이미지가 있으면 , 없으면
	      if(employee.getPic()==null) {
	    	  sql="insert into employee(empno,empname,title,manager,salary,dno) values(?,?,?,?,?,?)";
	      }else {
	    	  sql="insert into employee values(?,?,?,?,?,?,?)";
	      }
	      
	      
	      try(PreparedStatement pstmt= con.prepareStatement(sql)){
	    	  pstmt.setInt(1, employee.getEmpNo());
	    	  pstmt.setString(2, employee.getEmpName());
	    	  pstmt.setString(3, employee.getTitle());
	    	  pstmt.setInt(4, employee.getManager().getEmpNo());
	    	  pstmt.setInt(5, employee.getSalary());
	    	  pstmt.setInt(6, employee.getDept().getDeptNo());
	    	  if(employee.getPic() !=null) {
	    		  pstmt.setBytes(7, employee.getPic());
	    	  }
	    	  return pstmt.executeUpdate();
	      }
		
	}

	@Override
	public int updateEmployee(Connection con, Employee employee) {
		String sql = null;
	      //이미지가 있으면 , 없으면
	      if(employee.getPic()==null) {
	    	  sql="update employee set empname=?, title=?, manager=?,salary=?,dno=? where empno=?";
	      }else {
	    	  sql="update employee set empname=?, title=?, manager=?,salary=?,dno=?,"
	    	  		+ " pic=? where empno=?";
	      }
	      
	      
	      try(PreparedStatement pstmt= con.prepareStatement(sql)){
	    	  
	    	  pstmt.setString(1, employee.getEmpName());
	    	  pstmt.setString(2, employee.getTitle());
	    	  pstmt.setInt(3, employee.getManager().getEmpNo());
	    	  pstmt.setInt(4, employee.getSalary());
	    	  pstmt.setInt(5, employee.getDept().getDeptNo());
	    	  if(employee.getPic() !=null) {
	    		  pstmt.setBytes(6, employee.getPic());
	    		  logger.debug(employee.getPic().length);
	    		  pstmt.setInt(7, employee.getEmpNo());
	    		  logger.debug(pstmt);
	    		  return pstmt.executeUpdate();
	    	  }
	    	  else {
	    		  pstmt.setInt(6, employee.getEmpNo());
	    		  logger.debug(pstmt);

	    		  return pstmt.executeUpdate();
	    	  }
	      } catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public int deleteEmployee(Connection con, Employee employee) throws SQLException {
	   String sql = "delete from employee where empno =?";

	   try(PreparedStatement pstmt = con.prepareStatement(sql)){
		   pstmt.setInt(1, employee.getEmpNo());
		   logger.trace(pstmt);
		   logger.debug("aa");
		return pstmt.executeUpdate();   
	   }
	
	}


	@Override
	public Employee selectEmployeeByEmpNo(Connection con, Employee emp) {
		String sql="select empno,empname,title,manager,salary,dno,pic from employee e where empno=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
		 pstmt.setInt(1, emp.getEmpNo());
		 logger.trace(pstmt);
		    try(ResultSet rs = pstmt.executeQuery()){
		    	if(rs.next()) {
		    		return getEmloyee(rs,true);
		    	}
		    }
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return null;
	}





	@Override
	public List<Employee> procedureEmployeeByDno(Connection con, int dno) throws SQLException {
		String sql="{call procedure01(?)}";
		List<Employee> list = new ArrayList<Employee>();
		try(CallableStatement cs = con.prepareCall(sql)){
			cs.setInt(1, dno);
			LogUtil.prnLog(cs);

			try (ResultSet rs = cs.executeQuery()) {
				while(rs.next()) {
					list.add(getEmloyee(rs, false));
				}
				
			}
		}
		
		return list;
	}






}
