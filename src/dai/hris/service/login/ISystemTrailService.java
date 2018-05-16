package dai.hris.service.login;

import java.sql.SQLException;
import java.util.List;

import dai.hris.model.SystemTrail;

public interface ISystemTrailService {

	public  void insertSystemTrail(SystemTrail model) throws SQLException, Exception;
	public List<SystemTrail> getSystemTrailReportDetails(SystemTrail model) throws SQLException, Exception;
	public List<String> getProcessTypeList() throws SQLException, Exception;
	public List<String> getModuleNameList() throws SQLException, Exception;
}
