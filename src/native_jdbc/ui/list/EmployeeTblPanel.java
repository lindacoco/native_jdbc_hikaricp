package native_jdbc.ui.list;

import javax.swing.SwingConstants;

import native_jdbc_hikaricp.dto.Department;
import native_jdbc_hikaricp.dto.Employee;

public class EmployeeTblPanel extends AbstractTblPanel<Employee> {

	@Override
	protected void setTblWidthAlign() {
		// empno, empname, title, manager, salary, dno

		tableSetWidth(100, 150, 50, 150, 100, 100);
		tableCellAlign(SwingConstants.CENTER, 0, 1, 2, 3, 5);
		tableCellAlign(SwingConstants.RIGHT, 4);
	}

	@Override
	protected String[] getColNames() {
		return new String[] { "사원명", "사원명", "직급", "직속상사", "급여", "부서" };
	}

	@Override
	protected Object[] toArray(Employee item) {
		String manager;

		if (item.getManager().getEmpName() == null) {
			manager = "";
		} else {
			manager=String.format("%s(%d)", item.getManager().getEmpName(), item.getManager().getEmpNo());
		}
		return new Object[] { 
				item.getEmpNo(), item.getEmpName(), item.getTitle(), manager, // 직속상사명(사원번호)로 표시하고 싶다
				String.format("%,d", item.getSalary()),
				String.format("%s(%d)", item.getDept().getDeptName(), item.getDept().getDeptNo()) // 부서명(부서번호)
		};
	}

	@Override
	public void updateRow(Employee item, int updateIdx) {
		// 업데이트 하고자하는 row인덱스번호
		model.setValueAt(item.getEmpNo(), updateIdx, 0); // 사원번호
		model.setValueAt(item.getEmpName(), updateIdx, 1);
		model.setValueAt(item.getTitle(), updateIdx, 2);
		model.setValueAt(item.getManager().getEmpNo(), updateIdx, 3); // 이거
		model.setValueAt(item.getSalary(), updateIdx, 4);
		model.setValueAt(item.getDept().getDeptNo(), updateIdx, 5); // 이거
		// 수정하는 위치를 받아서 그 위치를 수정하라...?;
	}

	@Override
	public Employee getSelectedItem() {
		int selectedIdx = getSelectedRowIdx();
		int empNo = (int) model.getValueAt(selectedIdx, 0);
		String empName = (String) model.getValueAt(selectedIdx, 1);
		String title = (String) model.getValueAt(selectedIdx, 2);
		Employee manager = new Employee((int) model.getValueAt(selectedIdx, 3));
		int salary = (int) model.getValueAt(selectedIdx, 4);
		Department dept = new Department();
		dept.setDeptNo((int) model.getValueAt(selectedIdx, 5));
		// 생성자를 안만들어서 이런식으로 넣어줌
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

}
