package sports.util;

import java.sql.Date;



public class BannerStudentInfo {

	private String id;
	private String lastname;
	private String firstname; 
    private String initial;
    private String middleName;
    private String faculty;
    private String level;
    private String status;
    private String styp;
    private String term;
    private String activityDate;
    private Date birthDate;
    private String campusCode;
    private String action;
    private String student;
    private String major;
    private String country;
    private String pin;
    private String email;
    private int emailCnt;
    private int pidm;
    private boolean toTable;
    private String campus;
    private String operation;
    private boolean toPrint;
    private String houseAttribute;
    
    public String getHouseAttribute() {
		return houseAttribute;
	}
	public void setHouseAttribute(String houseAttribute) {
		this.houseAttribute = houseAttribute;
	}
	private String studentType;
    
    public void setToTable(boolean b){
    	this.toTable=b;
    }
    public boolean getToTable(){
    	return toTable;
    }
    public void setCampus(String s){
    	this.campus=s;
    }
    public String getCampus(){
    	return campus;
    }
    public void setStudentType(String s){
    	this.studentType=s;
    }
    public String getStudentType(){
    	return studentType;
    }
    public void setEmailCnt(int cnt){
    	this.emailCnt=cnt;
    }
    public int getEmailCnt(){
    	return emailCnt;
    }
    public void setPIDM(int pidm){
      this.pidm=pidm;	
    }
    public int getPIDM(){
    	return pidm;
    }
    public void setMajor(String major){
    	this.major=major;
    }
    public String getMajor(){
    	return major;
    }
    public void setCountry(String country){
    	this.country=country;
    }
    public String getCountry(){
    	return country;
    }
    public void setActivityDate(String date){
    	this.activityDate=date;
    }
    public String getActivityDate(){
    	return activityDate;
    }
    public void setBirthDate(Date date){
    	
    	
    	this.birthDate=date;
    }
    public Date getBirthDate(){
    	return birthDate;
    }
    public void setCampusCode(String string){
    	this.campusCode=string;
    }
    public String getCampusCode(){
    	return campusCode;
    }
    public void setTerm(String string){
    	this.term=string;
    }
    public String getTerm(){
    	return term;
    }
    public String getId(){
      return id;	
    }
    public void setId(String id){
    	this.id = id;
    }
    
	public String getLastname(){
		return lastname;
	}
	public void setLastname(String string){
		this.lastname = string;
	}
	
	public String getFirstname(){
		return firstname;
	}
	public void setFirstname(String string){
		this.firstname = string;
	}
	
	public String getInitial(){
		return initial;
	}
	public void setInitial(String string){
	  this.initial = string;	
	}
	public String getMiddleName(){
		return middleName;
	}
	public void setMiddleName(String string){
	  this.middleName = string;	
	}
	
	public String getFaculty(){
		return faculty;
	}
	public void setFaculty(String string){
		this.faculty = string;
	}
	
	public String getLevel(){
	  return level;	
	}
	public void setLevel(String string){
		  this.level = string;	
	}
	
	public String getStyp(){
		return styp;
	}
	public void setStyp(String string){
		this.styp = string;
	}
	
	public void setStatus(String s){
	   status = s;	
	}
	public String getStatus(){
		return status;
	}
	public void setAction(String s){
		   action = s;	
	}
	public String getAction(){
			return action;
	}
	public void setStudent(String s){
		   student = s;	
	}
	public String getStudent(){
			return student;
	}
	public String getSamAccountName(){
		return id;
	}
	public String getRDN(){
		String RDN = lastname+"\\, "+firstname;
		
		return RDN;
	}
	
	public String getDisplayName(){
		String dn = lastname.toLowerCase()+", "+firstname.toLowerCase();
		return dn;
	}
	public String getCN(){
		//return lastname.toUpperCase() + "\\," + firstname.replace(firstname.charAt(0), firstname.substring(0, 1).toUpperCase().charAt(0));
	    return firstname.toLowerCase()+" "+lastname.toLowerCase();
	}
	
	public void setPin(String string){
		pin = string;
	}
    
	public String getEmailPrefix(){
		return firstname.toLowerCase()+"."+lastname.toLowerCase();
	}
	public void setEmail(String str){
	  	this.email=str;
	}
	public String getEmail(){
		return email;
	}
	public void setOperation(String o){
		operation=o;
	}
	public String getOperation(){
	  return operation;	
	}
	public void setToPrint(boolean p){
		toPrint = p;
	}
	public boolean getToPrint(){
		return toPrint;
	}
}
