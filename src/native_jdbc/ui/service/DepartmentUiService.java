package native_jdbc.ui.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dao.DepartmentDaoImpl;
import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dao.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.Hikari_DataSource2;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public class DepartmentUiService {
	private Connection con;
	private DepartmentDao deptDao;
	private EmployeeDao empDao;

	public DepartmentUiService() {
		try {
			con = Hikari_DataSource2.getConnection();
			deptDao = DepartmentDaoImpl.getInstance();
			empDao = EmployeeDaoImpl.getInstance();

		} catch (SQLException e) {
			// 예외가 뜬다는 얘기는 데이터베이스가 없거나 아이디가 틀렸거나 등등
			JOptionPane.showMessageDialog(null, "접속정보확인하세요");
		}

	}

	public List<Employee> showEmployeeGroupByDno(Department dept) {
		try {
			return empDao.selectEmployeeGroupByDno(con, dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Department> showDepartments() {
		try {
			return deptDao.selectDepartmentByAll(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void addDepartment(Department newDept) throws SQLException {

		deptDao.insertDepartment(con, newDept);

	}

	
	public void modifyDepartment(Department dept) throws SQLException{
		deptDao.updateDepartment(con, dept);
	}
	
	public void deleteDepartment(Department dept) throws SQLException{
		deptDao.deleteDepartment(con, dept);
	}
}
