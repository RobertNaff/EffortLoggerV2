package application;

import java.time.*;

//IMPORTANT NOTE: the start times and end times are in ISO standard
//Java should have documentation on how to translate this into integers and strings

//the duration will be in the form of 'PT[time in hours]H[time in minutes]M[time in seconds]S
//ex: 'PT23H6M45.5S' 23 hours, 6 minutes and 45.5 seconds

//Instance will be in the form of [YEAR]-[MONTH]-[DAY]T[HOUR]:[TIME]:[SECONDS]Z
//EX: '1970-01-01T00:00:00Z' = january 1st, 1970, 00:00:00

public class Log {
	private Duration logDuration; //duration of the effort log
	private Instant startTime; //the start time of the log
	private Instant endTime; //end time of the log
	private Clock clock; //clock used to record instants
//	private User creator; //presumably will be used to record the user who created the log	
	private String[] keyWords; //list of keywords related to this log. for planning poker	
	private int userCount; //counts how many users are accessing a log. issues a warning if > 0
	private String description; //description of the log
	private String category; //the category of the log, such as defect, effortlog, etc.
	
	//basic constructor
	public Log() {
		this.startTime = Instant.MIN; //sets to minimum time
		this.endTime = Instant.MIN; //sets to minimum time
		this.clock = Clock.tickSeconds(ZoneId.systemDefault()); //sets clock to default time zone
		this.logDuration = Duration.ZERO; //sets duration to 0, equivalent to "PT0S"
		this.keyWords = new String[0]; //sets to an empty array
		this.userCount = 0;
		this.category = "";
		this.description = "";
		
		
	}
	
//getters
	public Duration getLogDuration() {
		return this.logDuration;
	}
	
	public Instant getStartTime() {
		return this.startTime;
	}
	
	public Instant getEndTime() {
		return this.endTime;
	}
	
	public String[] getKeyWords() {
		return this.keyWords;
	}
	
	public int getUserCount() {
		return this.userCount;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getCategory() {
		return this.category;
	}
	
/*	public User getCreator(){
 * 		return this.creator;
 * 	}
	*/
	
//setters
	
	//sets the start time to something specific
	//string must be of the form [YEAR]-[MONTH]-[DAY]T[HOUR]:[TIME]:[SECONDS]Z
	//EX: '1970-01-01T00:00:00Z'
	//i have no idea how to get that from a text field in the user interface
	public boolean setStartTime(String newStartTime) {
		Instant newInstant;
		
		newInstant = Instant.parse(newStartTime);
		
		//if the string is faulty itll return false
		//this isnt actually the faulty one but i couldnt find what it returned if the string was in ISO form
		if(newInstant.equals(Instant.MIN))
			return false;
		
		this.startTime = newInstant;
		calculateDuration();
		return true;
	}
	
	//sets endtime
	public boolean setEndTime(String newEndTime) {
		Instant newInstant;
		
		newInstant = Instant.parse(newEndTime);
		
		//if the string is faulty itll return false
		if(newInstant.equals(Instant.MIN))
			return false;
		
		this.endTime = newInstant;
		calculateDuration();
		return true;
	}
	
	
	//sets the start time to the current time
	public Instant retrieveStartTime() {
		this.startTime = clock.instant();
		calculateDuration();
		return this.startTime;
	}
	
	//sets the end time instant to the current time
	public Instant retrieveEndTime() {
		this.endTime = clock.instant();
		calculateDuration();
		return this.endTime;
	}
	
	public void setKeyWords(String[] newKeyWords) {
		this.keyWords = newKeyWords;
	}
	
	//sets description
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}
	
	//sets category
	public void setCategory(String newCategory) {
		this.category = newCategory;
	}
	
	//increases usercount by 1
	public void incrementUserCount() {
		this.userCount++;
	}
	
	
	//decreases usercount by 1
	public void decrementUserCount() {
		if(this.userCount != 0)
			this.userCount--;
	}
	
	//calculates the time between the start and the end times and returns it
	//the duration will be in the form of 'PT[time in hours]H[time in minutes]M[time in seconds]S
	//ex: 'PT23H6M45.5S' 23 hours, 6 minutes and 45.5 seconds
	private Duration calculateDuration() {
		logDuration = Duration.between(this.startTime, this.endTime);
		return logDuration;
	}
}
