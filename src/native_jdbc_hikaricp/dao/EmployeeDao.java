package native_jdbc_hikaricp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public interface EmployeeDao {
    //두개만들거임 하나는 소속된 사원만 검색하는 것 
   Employee selectEmployeeByDno(Connection con,Department dept) throws SQLException;
   
   List<Employee> selectEmployeeByAll(Connection con) throws SQLException;
   
   
   
   //부서번호에 해당하는 사원만 출력해주는 sql문 필요 
   
   List<Employee> selectEmployeeGroupByDno(Connection con, Department dept) throws SQLException;
   
}
