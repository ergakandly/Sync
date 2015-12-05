package com.hris.attendance.manager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hris.attendance.ibatis.IbatisHelperAccess;
import com.hris.attendance.ibatis.IbatisHelperOracle;
import com.hris.attendance.model.AttendanceBean;
import com.ibatis.sqlmap.client.SqlMapClient;

public class AttendanceManager {
	private SqlMapClient ibatis;
	private SqlMapClient ibatisAccess;

	public AttendanceManager() {
		new IbatisHelperOracle();
		ibatis = IbatisHelperOracle.getSqlMapInstance();

		new IbatisHelperAccess();
		ibatisAccess = IbatisHelperAccess.getSqlMapInstance();
	}

	@SuppressWarnings("unchecked")
	public void syncData() {
		List<AttendanceBean> result = null;
		String username = "SYSTEM";
		try {
			ibatis.startTransaction();
			String syncDateMM = (String) ibatis.queryForObject("sync.getLastSync", "");
			System.out.println("Last Sync Date= "+ syncDateMM);
			
			result = ibatisAccess.queryForList("accessDB.syncDB", syncDateMM);
			System.out.println("Result Size= "+ result.size());
			
			for (int i = 0; i < result.size(); i++) {
				System.out.println("Syncing ["+i+"]");
				Map<String, Object> parameter = new HashMap<String, Object>();

				parameter.put("employeeId", result.get(i).getEmployeeId());
				parameter.put("checkIn", 	result.get(i).getAttendanceDate() + " " + result.get(i).getCheckIn() + ":00");
				parameter.put("checkOut", 	result.get(i).getAttendanceDate() + " " + result.get(i).getCheckOut() + ":00");
				
				try{
					ibatis.insert("sync.syncInsert", parameter);
				}catch(SQLException e){
					e.printStackTrace();
				}
				System.out.println("Sync Done ["+i+"]: "+ parameter.get("employeeId") +" "+parameter.get("checkIn")+" "+parameter.get("checkOut"));
			}
			ibatis.insert("sync.syncLog", "");
			ibatis.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
