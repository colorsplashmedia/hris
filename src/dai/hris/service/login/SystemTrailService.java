package dai.hris.service.login;

import java.sql.SQLException;
import java.util.List;
import dai.hris.dao.SystemTrailDAO;
import dai.hris.model.SystemTrail;

public class SystemTrailService implements ISystemTrailService {

	@Override
	public  void insertSystemTrail(SystemTrail model) throws SQLException, Exception {
		SystemTrailDAO dao = new SystemTrailDAO();
		dao.insertSystemTrail(model);
		dao.closeConnection();
	}

	@Override
	public List<SystemTrail> getSystemTrailReportDetails(SystemTrail model) throws SQLException, Exception {
		SystemTrailDAO dao = new SystemTrailDAO();
		List<SystemTrail> list = dao.getSystemTrailReportDetails(model);
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<String> getProcessTypeList() throws SQLException, Exception {
		SystemTrailDAO dao = new SystemTrailDAO();
		List<String> list = dao.getProcessTypeList();
		dao.closeConnection();
		return list;
	}
	
	@Override
	public List<String> getModuleNameList() throws SQLException, Exception {
		SystemTrailDAO dao = new SystemTrailDAO();
		List<String> list = dao.getModuleNameList();
		dao.closeConnection();
		return list;
	}
	

}
