package sports.houses;

import java.sql.SQLException;

import sports.db.SportsDb;
import sports.util.BannerStudentInfo;

public class DistributeHouses extends UWISports {

	public DistributeHouses(){
		super();
	}
	@Override
	public void closeConn() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addStudent(BannerStudentInfo bsi) {
		// TODO Auto-generated method stub

	}
	public static void main(String[] args) throws SQLException {
		
		DistributeHouses sports = new DistributeHouses();
		sports.gatherStudent();
		sports.distributeStudents();
		System.exit(0);
	
	}
}
