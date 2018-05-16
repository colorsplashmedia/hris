package dai.hris.service.payroll.impl;

import java.sql.SQLException;
import java.util.List;

import dai.hris.dao.payroll.MedicareShareAllowanceDAO;
import dai.hris.model.MedicareShareAllowance;
import dai.hris.service.payroll.IMedicareShareAllowanceService;

public class MedicareShareAllowanceService implements IMedicareShareAllowanceService {

	@Override
	public void saveOrUpdate(MedicareShareAllowance model) throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		dao.saveOrUpdate(model);
		dao.closeConnection();
	}
    
	@Override
	public int getCount(int empId) throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		int result = dao.getCount(empId);
		dao.closeConnection();
		return result;
	}
    
	@Override
	public List<MedicareShareAllowance> getMedicareShareAllowanceListByEmpId(int empId, int jtStartIndex, int jtPageSize, String jtSorting) throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		List<MedicareShareAllowance> result = dao.getMedicareShareAllowanceListByEmpId(empId, jtStartIndex, jtPageSize, jtSorting);
		dao.closeConnection();
		return result;
	}
	

	@Override
	public List<MedicareShareAllowance> getMedicareShareAllowanceListByDateRange(String dateFrom, String dateTo) throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		List<MedicareShareAllowance> result = dao.getMedicareShareAllowanceListByDateRange(dateFrom, dateTo);
		dao.closeConnection();
		return result;
	}
	
	@Override
	public void add(MedicareShareAllowance msa) throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		dao.add(msa);
		dao.closeConnection();
	}

	@Override
	public void update(MedicareShareAllowance msa) throws SQLException,
			Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		dao.update(msa);
		dao.closeConnection();
	}

	@Override
	public void delete(MedicareShareAllowance msa) throws SQLException,
			Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		dao.delete(msa);
		dao.closeConnection();
	}

	@Override
	public int[] batchUpdate(List<MedicareShareAllowance> msaList)
			throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		int[] result = dao.batchUpdate(msaList);
		dao.closeConnection();
		return result;
	}

	@Override
	public int[] batchInsert(List<MedicareShareAllowance> msaList)
			throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		int[] result = dao.batchInsert(msaList);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<MedicareShareAllowance> getAllByEmployeeId(int empId)
			throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		List<MedicareShareAllowance> result = dao.getAllByEmployeeId(empId);
		dao.closeConnection();
		return result;
	}

	@Override
	public List<MedicareShareAllowance> getAllByJobTitleId(int jtId)
			throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		List<MedicareShareAllowance> result = dao.getAllByJobTitleId(jtId);
		dao.closeConnection();
		return result;
	}
	
	@Override
	public void saveMedicareShare(MedicareShareAllowance model) throws SQLException, Exception {
		MedicareShareAllowanceDAO dao = new MedicareShareAllowanceDAO();
		dao.saveMedicareShare(model);
		dao.closeConnection();
	}

}

