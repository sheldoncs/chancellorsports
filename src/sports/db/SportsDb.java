package sports.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sports.util.BannerStudentInfo;
import sports.util.MessageLogger;
import sports.util.NewDateFormatter;

public class SportsDb {

	protected Connection conn;
	protected Connection mysqlconn;
	
	public String getActiveByYear() {

		String selectStatement = "SELECT SPRIDEN.SPRIDEN_ID,SGBSTDN.SGBSTDN_TERM_CODE_EFF,  SPRIDEN.SPRIDEN_LAST_NAME, "
				+ "SPRIDEN.SPRIDEN_FIRST_NAME, SPRIDEN.SPRIDEN_MI AS MI, "
				+ "SGBSTDN.SGBSTDN_LEVL_CODE, SGBSTDN.SGBSTDN_COLL_CODE_1 AS FACULTY, "
				+ "SGBSTDN.SGBSTDN_CAMP_CODE, SGBSTDN.SGBSTDN_STST_CODE, SGBSTDN.SGBSTDN_STYP_CODE, "
				+ "SPRIDEN.SPRIDEN_ACTIVITY_DATE,SPBPERS.SPBPERS_BIRTH_DATE, STVMAJR.STVMAJR_DESC, "
				+ "STVNATN.STVNATN_NATION, SPRIDEN.SPRIDEN_PIDM, SGBSTDN.SGBSTDN_FULL_PART_IND "
				+ "FROM ((SPBPERS INNER JOIN (SGBSTDN INNER JOIN SPRIDEN ON "
				+ "SGBSTDN.SGBSTDN_PIDM = SPRIDEN.SPRIDEN_PIDM) ON "
				+ "SPBPERS.SPBPERS_PIDM = SGBSTDN.SGBSTDN_PIDM) INNER JOIN STVMAJR ON "
				+ "SGBSTDN.SGBSTDN_MAJR_CODE_1 = STVMAJR.STVMAJR_CODE) INNER JOIN (GOBINTL INNER JOIN STVNATN ON "
				+ "GOBINTL.GOBINTL_NATN_CODE_LEGAL = STVNATN.STVNATN_CODE) ON "
				+ "SGBSTDN.SGBSTDN_PIDM = GOBINTL.GOBINTL_PIDM WHERE  "
				+ "((SGBSTDN.SGBSTDN_STYP_CODE) In ('N','T','R','F','S','C','E','V','O') AND (SPRIDEN.SPRIDEN_ID NOT LIKE '2000%') AND "
				+ "((SGBSTDN.SGBSTDN_TERM_CODE_EFF) >= '202010')) "
				+ "AND ((SPRIDEN.SPRIDEN_CHANGE_IND) Is Null) AND "
				+ "((SPBPERS.SPBPERS_DEAD_IND) Is Null) AND ((SPBPERS.SPBPERS_BIRTH_DATE) Is Not null) AND (STVNATN.STVNATN_NATION is not Null) "
				+ "and ( SGBSTDN.SGBSTDN_PIDM, SGBSTDN.SGBSTDN_TERM_CODE_EFF )  = "
				+ "( select SGBSTDN1.SGBSTDN_PIDM, "
				+ "Max( SGBSTDN1.SGBSTDN_TERM_CODE_EFF ) AS Max_SGBSTDN_TERM_CODE_EFF "
				+ "from SATURN.SGBSTDN SGBSTDN1 "
				+ "where SGBSTDN1.SGBSTDN_PIDM = SGBSTDN.SGBSTDN_PIDM "
				+ "group by SGBSTDN1.SGBSTDN_PIDM )  "
				+ "order by SPRIDEN.SPRIDEN_LAST_NAME, SPRIDEN.SPRIDEN_FIRST_NAME,SGBSTDN.SGBSTDN_TERM_CODE_EFF";

		return selectStatement;
	}

	public void openConn() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@bandb-prod.ec.cavehill.uwi.edu:8000:PROD";
//			String url = "jdbc:oracle:thin:@bandb-dev.ec.cavehill.uwi.edu:8003:TEST";
			conn = DriverManager.getConnection(url, "svc_update", "e98ce36209");
			conn.setAutoCommit(false);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
			MessageLogger.out.println(ex.getMessage());
		}

	}
    public void openMSQLConn(){
    	
    	try {
			Class.forName("com.mysql.jdbc.Driver");

			mysqlconn = DriverManager.getConnection("jdbc:mysql://" + "owl2"
					+ ":3305/", "admin", "kentish");
			mysqlconn.setAutoCommit(true);
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
    	
    }
	public ArrayList<BannerStudentInfo> gatherStudents() throws SQLException {

		String selectStatement = null;

		ArrayList<BannerStudentInfo> studentList = new ArrayList<BannerStudentInfo>();

		selectStatement = getActiveByYear();

		PreparedStatement prepStmt = conn.prepareStatement(selectStatement);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			BannerStudentInfo bsi = new BannerStudentInfo();
			bsi.setId(rs.getString(1));
			bsi.setLastname(rs.getString(3));
			bsi.setFirstname(rs.getString(4));
			bsi.setPIDM(this.getPidm(rs.getString(1)));
			bsi.setTerm(this.getCurrentTerm());
			System.out.println(bsi.getFirstname()+ ", "+bsi.getLastname());
			studentList.add(bsi);
		}
		
		MessageLogger.out.println(studentList.size());
		return studentList;

	}
	
	public String getLastHouse() {

		String lastHouse = "";
		try {

			String selectStatement = "select lasthouse from lasthouse";
			PreparedStatement prepStmt = conn.prepareStatement(selectStatement);

			
			prepStmt.execute("use " + "chancellorsports");

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				lastHouse = rs.getString(1);
			}

			prepStmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return lastHouse;
	}
    public int getPidm(String id) {

		int pidm = 0;

		String sqlstmt = "select spriden_pidm, spriden_id from spriden where spriden_id = ?";


		try {

			PreparedStatement prepStmt = conn.prepareStatement(sqlstmt);
			prepStmt.setString(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				pidm = rs.getInt(1);
			}

			rs.close();
			prepStmt.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return pidm;

	}
	public void updateLastHouse(String lh){
		
		try {

			String selectStatement = "UPDATE lasthouse set lasthouse = ?";
			PreparedStatement prepStmt = conn.prepareStatement(selectStatement);

			prepStmt.setString(1, lh);

			prepStmt.execute("use " + "chancellorsports");

			prepStmt.executeUpdate();

			prepStmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	public void insertHeaderTable(int pidm, String id ,String term, ArrayList<BannerStudentInfo> currentList){
		
		
		String sqlstmt = "INSERT INTO SGRSPRT (SGRSPRT_PIDM, SGRSPRT_TERM_CODE, SGRSPRT_ACTC_CODE, SGRSPRT_SPST_CODE, SGRSPRT_ELIG_CODE,  "
				+ "SGRSPRT_ACTIVITY_DATE, SGRSPRT_USER_ID, SGRSPRT_SAEL_CODE, SGRSPRT_DATA_ORIGIN) VALUES (?,?,?,?,?,?,?,?,?)";
		
		NewDateFormatter df = new NewDateFormatter();
		
		try {
			PreparedStatement prepStmt = conn.prepareStatement(sqlstmt);
			BannerStudentInfo bsi = new BannerStudentInfo(); 
			bsi.setPIDM(pidm);
			bsi.setId(id);
			bsi.setTerm(term);
			
			if (!headerExist(pidm)) {
				
				prepStmt.setLong(1, pidm);
				prepStmt.setString(2, term);
				prepStmt.setString(3, "HOUSE");
				prepStmt.setString(4, "AC");
				prepStmt.setString(5, "EL");
				prepStmt.setDate(6, java.sql.Date.valueOf(df.getSimpleOracleDate()));
				prepStmt.setString(7, "C20003569");
				prepStmt.setString(8, "QU");
				prepStmt.setString(9, "Loaded");
				
				currentList.add(bsi);
			
			/** Use when activating the Banner Insert to SGRADVR **/

			prepStmt.executeUpdate();
			
			
			} 
			conn.commit();
			
			prepStmt.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	
	}
	public void insertHouse(int pidm, String attrib){
		
		
		String sqlstmt = "INSERT INTO SGRATHA (SGRATHA_PIDM, SGRATHA_TERM_CODE, SGRATHA_ACTC_CODE, SGRATHA_SAAT_CODE, SGRATHA_ACTIVITY_DATE, " +
				"SGRATHA_USER_ID, SGRATHA_DATA_ORIGIN) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
   
		
		NewDateFormatter df = new NewDateFormatter();

		try {
			PreparedStatement prepStmt = conn.prepareStatement(sqlstmt);
            
			if (pidm == 30345)
				System.out.println();
			
			if (!houseExist(pidm, attrib)) {
			
				prepStmt.setLong(1, pidm);
				prepStmt.setString(2, getCurrentTerm());
				prepStmt.setString(3, "HOUSE");
				prepStmt.setString(4, attrib);
				prepStmt.setDate(5, java.sql.Date.valueOf(df.getSimpleOracleDate()));
				prepStmt.setString(6, "C20003569");
				prepStmt.setString(7, "Loaded");
				
				prepStmt.executeUpdate();
				
				
			}
			conn.commit();
			/** Use when activating the Banner Insert to SGRADVR **/
            
			
			
			prepStmt.close();
            
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	private boolean houseExist(int pidm, String attrib){
		
		boolean found = false;
		
		String sqlstmt = "select * from SGRATHA where SGRATHA_PIDM = ? and SGRATHA_ACTC_CODE = ?";

		try {

			PreparedStatement prepStmt = conn.prepareStatement(sqlstmt);
			prepStmt.setInt(1, pidm);
			prepStmt.setString(2, "HOUSE");
			
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				found = true;
			}

			rs.close();
			prepStmt.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return found;
		
	}
	private boolean headerExist(int pidm){
		
		boolean found = false;
		
		String sqlstmt = "select * from SGRSPRT where SGRSPRT_PIDM = ?";

		try {

			PreparedStatement prepStmt = conn.prepareStatement(sqlstmt);
			prepStmt.setInt(1, pidm);
			
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				found = true;
			}

			rs.close();
			prepStmt.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return found;
		
	}
	public String getCurrentTerm(){
		 String term = null;
			
		 
			String sqlstmt = "select max(stvterm_code) as maxtermcode from stvterm where stvterm_start_date < ? and stvterm_end_date > ?";
			NewDateFormatter df = new NewDateFormatter();
			
			try {
				  
				  PreparedStatement prepStmt = conn.prepareStatement(sqlstmt);
				  prepStmt.setDate(1, java.sql.Date.valueOf((df.getSimpleDate())));
			      prepStmt.setDate(2, java.sql.Date.valueOf(df.getSimpleDate()));
			     
			      ResultSet rs = prepStmt.executeQuery();
			      while (rs.next()){
			    	  
			    	  term = rs.getString(1);
			      }
			      if ((term != null) && (term.indexOf("40") >=0)){
				      
			    	   term = null;
			        
			      }
			      
			      if (term == null){
						sqlstmt = "select min(stvterm_code) as maxtermcode from stvterm where stvterm_start_date > ?";
						df = new NewDateFormatter();
						System.out.println(java.sql.Date.valueOf((df.getSimpleDate())));
						prepStmt = conn.prepareStatement(sqlstmt);
						prepStmt.setDate(1, java.sql.Date.valueOf((df.getSimpleDate())));
						
						rs = prepStmt.executeQuery();
					    while (rs.next()){
					    	  term = rs.getString(1);
					    }
				  }
			      
			      term = term.substring(0,4)+"10";
			      
			      rs.close();
			      prepStmt.close();
			}
			catch(SQLException e){
				  e.printStackTrace();
			}
			
			term = term.substring(0,4)+"10";
			
			return term;
	 }

	 public void closeConn(){
		 try {
		 
			 conn.close();
		     mysqlconn.close();
		 
		 //exportDb.closeConnections();
		 //adsl.closeConnections();
		 
		 } catch(SQLException e){
			  e.printStackTrace();
			}
		 
	 }
}
