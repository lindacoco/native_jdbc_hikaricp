package native_jdbc_hikaricp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public interface EmployeeDao {
	Employee selectEmployeeByEmpNo(Connection con, Employee emp);
	
	List<Employee> selectEmployeeByAll(Connection con) throws SQLException;
	
	List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException;
	
	
	
    int insertEmployee(Connection con, Employee employee) throws SQLException;
	
	int updateEmployee(Connection con, Employee employee) throws SQLException;
	
	int deleteEmployee(Connection con, Employee employee) throws SQLException;

	
	/*procedure call*/
	List<Employee> procedureEmployeeByDno(Connection con, int dno)throws SQLException;

}


	
