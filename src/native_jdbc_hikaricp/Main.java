package native_jdbc_hikaricp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dao.DepartmentDaoImpl;
import native_jdbc_hikaricp.dao.EmployeeDao;
import native_jdbc_hikaricp.dao.EmployeeDaoImpl;
import native_jdbc_hikaricp.ds.C3P0DataSource;
import native_jdbc_hikaricp.ds.DBCPDataSource;
import native_jdbc_hikaricp.ds.Hikari_DataSource;
import native_jdbc_hikaricp.ds.MySqlDataSource;
import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;
import native_jdbc_hikaricp.ui.DlgEmployee;

public class Main {

	public static void main(String[] args) throws SQLException {

//		EmployeeDao dao1 = new EmployeeDaoImpl();
//		EmployeeDao dao2 = new EmployeeDaoImpl();
//		System.out.println(dao1);
//		System.out.println(dao2);

//		EmployeeDao dao3 = new EmployeeDaoImpl.getInstance();
//		EmployeeDao dao4 = new EmployeeDaoImpl.getInstance();

//	  try (Connection con = C3P0DataSource.getConnection();){
//		  System.out.println("C3P0DataSource");
//		    selectDepartment(con);
//			insertDepartment(con);
//			updateDepartment(con);
//			deleteDepartment(con);
//	  }catch(SQLException e) {
//		  e.printStackTrace();
//	  }

//		EmployeeDao dao3 =  EmployeeDaoImpl.getInstance();	
//		List<Employee> list = dao3.selectEmployeeByAll(Hikari_DataSource2.getConnection());
//		
//		
//		for(Employee e:list) {
//		System.out.println(e);
//		}

//	  
		try (Connection con = MySqlDataSource.getConnection();) {
			// System.out.println(con);
			
			DepartmentDao dao = DepartmentDaoImpl.getInstance();
			Department newDept = new Department(6,"횬소",10);
		
			dao.updateDepartment(con, newDept);
			
			

//			EmployeeDao dao = EmployeeDaoImpl.getInstance();
//			Department dept = new Department();
//			dept.setDeptNo(2);
//			List<Employee> list1 = dao.selectEmployeeGroupByDno(con, dept);
//			for (Employee e : list1) {
//				System.out.println(e);
//			}
//			System.out.println("Hikari_DataSource2");
//			selectDepartment(con);
//			insertDepartment(con);
//			updateDepartment(con);
//			deleteDepartment(con);
//			DlgEmployee dialog = new DlgEmployee();
//			dialog.setEmpList(list1);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}

//	  try (Connection con = DBCPDataSource.getConnection();){
//		//  System.out.println(con);
//		  System.out.println("DBCPDataSource");
//		    selectDepartment(con);
//			insertDepartment(con);
//			updateDepartment(con);
//			deleteDepartment(con);
//	  }catch(SQLException e) {
//		  e.printStackTrace();
//	  }

	}

	private static void deleteDepartment(Connection con) {
		String deleteSql = "delete from department where deptno=?";
		try (PreparedStatement pstmt = con.prepareStatement(deleteSql)) {
			pstmt.setInt(1, 5);
			System.out.println(pstmt);

			int res = pstmt.executeUpdate();
			System.out.println("삭제성공" + res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void updateDepartment(Connection con) {
		String updateSql = "update department set deptname =?, floor =? where deptno =?";
		try (PreparedStatement pstmt = con.prepareStatement(updateSql)) {
			pstmt.setString(1, "마케팅");
			pstmt.setInt(2, 11);
			pstmt.setInt(3, 5);
			System.out.println(pstmt);
			int res = pstmt.executeUpdate();
			System.out.println("수정정공" + res);

		} catch (SQLException e) {
			System.out.println("해당 데이터 베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망 " + e.getErrorCode());
			if (e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "해당부서가 이미 존재함");
			}
			e.printStackTrace();
		}

	}

	private static void insertDepartment(Connection con) {
		String insertSql = "insert into department values(?,?,?)";
		// db접속
		try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
			pstmt.setInt(1, 5);
			pstmt.setString(2, "총무");
			pstmt.setInt(3, 30);
			System.out.println("연결성공" + pstmt); // 귀찮아도 찍어봐야한다
			int res = pstmt.executeUpdate();
			System.out.println("res:" + res);

		} catch (SQLException e) {
			System.out.println("해당 데이터 베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망 " + e.getErrorCode());
			if (e.getErrorCode() == 1062) {
				JOptionPane.showMessageDialog(null, "해당부서가 이미 존재함");
			}
			e.printStackTrace();
		}

	}

	private static void selectDepartment(Connection con) {
		String sql = "select deptno,deptname, floor from department"; // where deptNo=2";
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

			List<Department> deptList = new ArrayList<>();
			while (rs.next()) {
				deptList.add(getDepartment(rs));
			}
			// 출력
			for (Department d : deptList) {
				System.out.println(d);
			}

		} catch (SQLException e) {
			System.out.println("해당 데이터 베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망 " + e.getErrorCode());
			e.printStackTrace();
		} finally {
			// 에러뜨더라도 반드시 finally수행함

		}

	}

	private static Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno"); // 또는 1,2,3 하지만 숫자로 주는건 좋지않다
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

}
