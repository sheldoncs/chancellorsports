package sports.houses;

import java.sql.SQLException;
import java.util.ArrayList;

import sports.db.SportsDb;
import sports.util.BannerStudentInfo;

public class SirHughWooding extends UWISports {

	private ArrayList<BannerStudentInfo> shwhList = new ArrayList<BannerStudentInfo>();
	
	@Override
	public void closeConn() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void addStudent(BannerStudentInfo bsi) {
		// TODO Auto-generated method stub
		bsi.setHouseAttribute("SHWH");
		db.insertHouse(db.getPidm(bsi.getId()), bsi.getHouseAttribute());
		shwhList.add(bsi);
	}

}
