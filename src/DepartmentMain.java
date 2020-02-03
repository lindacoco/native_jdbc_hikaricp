import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc_hikaricp.ds.Hikari_DataSource2;
import native_jdbc_hikaricp.dto.Department;

public class DepartmentMain {
	public static void main(String[] args) {

//		selectDepartment();
//		insertDepartment();
//		updateDepartment();
//		deleteDepartment();
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

	private static void selectDepartment(Connection con) {
		String sql = "select deptno,deptname, floor from department"; // where deptNo=2";
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

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

	private static Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno"); // 또는 1,2,3 하지만 숫자로 주는건 좋지않다
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}
	
	

}
