package native_jdbc_hikaricp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	//싱글톤
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();

	
	private EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	




	public static EmployeeDaoImpl getInstance() {
		return instance;
	}





	@Override
	public Employee selectEmployeeByDno(Connection con, Department dept) throws SQLException {
		// TODO Auto-generated method stub
		return null;
		
		
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
			while(rs.next()) {
				list.add(getEmloyee(rs));
			}
		}
		return list;
	}
	private Employee getEmloyee(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("manager");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
		return new Employee(empNo, empName, title, manager, salary, dept);
	}






}
