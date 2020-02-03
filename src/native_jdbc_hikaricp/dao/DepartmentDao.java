package native_jdbc_hikaricp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import native_jdbc_hikaricp.dto.Department;

public interface DepartmentDao {
    //어레이리스트 리턴 select니까
	List<Department> selectDepartmentByAll(Connection con) throws SQLException; //util의 리스트
	
	int insertDepartment(Connection con, Department department) throws SQLException;
	int updateDepartment(Connection con, Department department) throws SQLException;
	int deleteDepartment(Connection con, Department department) throws SQLException;
	
	//0203 select  * from department d where deptno =3;
	Department selectDepartmentByNo(Connection con, int dno) throws SQLException;
	
}



