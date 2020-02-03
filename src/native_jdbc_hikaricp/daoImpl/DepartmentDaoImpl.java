package native_jdbc_hikaricp.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import native_jdbc_hikaricp.dao.DepartmentDao;
import native_jdbc_hikaricp.dto.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	protected static Logger logger = LogManager.getLogger();
	// MVC모델 2 하는중
	// 싱글톤 패턴으로 구현할거임..................;

	private static final DepartmentDaoImpl instance = new DepartmentDaoImpl();

	// 기본 생성자 만들고 public을 private로 바꿈 외부에서 생성부가
	private DepartmentDaoImpl() {
	}

	public static DepartmentDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<Department> selectDepartmentByAll(Connection con) throws SQLException {
		// 구현해야된다는 이야기
		String sql = "select  deptno, deptname, floor from department d"; // 쿼리날릴 sql 디비버 먼저 해보고 가져와야 고생 덜한다
		// con에서 연결됐다고 봄 그럼 쿼리날릴 준비를 해야한다
		List<Department> list = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			logger.trace(pstmt);
			
			
			while (rs.next()) {
				list.add(getDepartment(rs));
			}
		}
		return list; // 지역변수라서 에러뜸 try 위로 옮김
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

	@Override
	public int insertDepartment(Connection con, Department department) throws SQLException {
		String sql = "insert into department values(?,?,?)";

		// List<Department> list = new ArrayList<>();
		int res = -1;
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, department.getDeptNo());
			pstmt.setString(2, department.getDeptName());
			pstmt.setInt(3, department.getFloor());
			System.out.println(pstmt);
			logger.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateDepartment(Connection con, Department department) throws SQLException {
		String sql = "update  department set deptname = ?, floor = ? where deptno =?";

		// List<Department> list = new ArrayList<>();
		int res = -1;
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, department.getDeptName());
			pstmt.setInt(2, department.getFloor());
			pstmt.setInt(3, department.getDeptNo());

			// ?마크는 무조건 넘버링되는 것
			System.out.println(pstmt);
			logger.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteDepartment(Connection con, Department department) throws SQLException {
		String sql = "delete from department where deptno =?";

		// List<Department> list = new ArrayList<>();
		int res = -1;
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		
			pstmt.setInt(1, department.getDeptNo());

			// ?마크는 무조건 넘버링되는 것
			//System.out.println(pstmt);
			logger.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public Department selectDepartmentByNo(Connection con, int dno) throws SQLException {
		String sql= "select deptno, deptname, floor from department d where deptno =?";
		
		  try( PreparedStatement pstmt = con.prepareStatement(sql);)
		  {
			  pstmt.setInt(1, dno);
			  logger.trace(pstmt); //검증용 
			   try(ResultSet rs = pstmt.executeQuery()){
				   if(rs.next()) {
					   return getDepartment(rs);
				   }
			   }
		  }
				
		return null;
	}

}
