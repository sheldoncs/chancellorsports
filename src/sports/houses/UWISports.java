package sports.houses;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import sports.db.SportsDb;
import sports.util.BannerStudentInfo;

public abstract class UWISports {

	protected String studentId;
	protected String firstName;
	protected String lastName;
	protected SportsDb db;
	
	protected ArrayList<BannerStudentInfo> studentList;
	protected ArrayList<UWISports> houseArray;
	
	public UWISports(){
		
		db  = new SportsDb();
		db.openConn();
		db.openMSQLConn();
	}
	public void gatherStudent() throws SQLException{
		studentList = db.gatherStudents();
		
	}
	public void distributeStudents(){
		
		houseArray = new ArrayList<UWISports>();
		houseArray.add(new PrincessAlice());
		houseArray.add(new SirAllenLewis());
		houseArray.add(new SirGeorgeAlleyne());
		houseArray.add(new SirHughWooding());
		houseArray.add(new SirShridathRamphal());

		ListIterator<UWISports> houseIterator = houseArray.listIterator();

		Iterator<BannerStudentInfo> ii = studentList.iterator();

		ArrayList<BannerStudentInfo> currentList = new ArrayList<BannerStudentInfo>();
		int cnt = 0;
		while (ii.hasNext()) {

			BannerStudentInfo bsi = ii.next();

			db.insertHeaderTable(bsi.getPIDM(), bsi.getId(),bsi.getTerm(), currentList);
		}
		int max = currentList.size();

		HashMap<String, BannerStudentInfo> map = new HashMap<String, BannerStudentInfo>();
		while (cnt <= max-1) {

			int index = randInt(0, max - 1);
			BannerStudentInfo bsi = (BannerStudentInfo) currentList.get(index);
			if (!map.containsKey(bsi.getId())) {
				map.put(bsi.getId(), bsi);
				assignHouses(houseIterator, bsi);
				cnt++;
			}
			
		}
		System.out.println(cnt);
		db.closeConn();
		
	}
	public void assignHouses(ListIterator<UWISports> houseIterator, BannerStudentInfo bsi){
		
		
		if (houseIterator.hasNext()){
			
			UWISports sports = houseIterator.next();
			
			String cls = sports.getClass().toString().substring(sports.getClass().toString().lastIndexOf(".")+1, sports.getClass().toString().length());
			System.out.println(cls);
			sports.addStudent(bsi);
			
		} else {
			while (houseIterator.hasPrevious())
				houseIterator.previous();
			UWISports sports = houseIterator.next();
			sports.addStudent(bsi);
		}
		
		
	}
	public int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	public abstract void closeConn() throws SQLException;
	public abstract void addStudent(BannerStudentInfo bsi); 
	
	
}
