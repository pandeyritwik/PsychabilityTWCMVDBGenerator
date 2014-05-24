package com.psychability.twc.mvdbgenerator;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.psychability.twc.entities.Household;
import com.psychability.twc.entities.MVDBEntity;
import com.psychability.twc.entities.Program;
import com.psychability.twc.store.ProgramStore;
import com.psychability.twc.store.StationStore;
import com.psychability.twc.utility.Utility;

public class HouseholdGenerator {

	
	
	
	public static void main(String[] args) throws UnknownHostException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		//new HouseholdGenerator().initializeLists();
		
		String host = "localhost";
		int port = 27017;
		String db = "TWC";
		String collection = "Household";
		int tuners = 10;
		int programs = 10;
		int stations = 5;
		int records = 10;
		int dmaId = 1001;
		for (String arg : args) {
			if (arg.toLowerCase().startsWith("-t")) {
	//			tuners = Integer.parseInt(arg.substring(2));
			} else if (arg.toLowerCase().startsWith("-p")) {
				programs = Integer.parseInt(arg.substring(2));
			} else if (arg.toLowerCase().startsWith("-s")) {
				stations = Integer.parseInt(arg.substring(2));
			} else if (arg.toLowerCase().startsWith("-r")) {
				records = Integer.parseInt(arg.substring(2));
			} else if (arg.toLowerCase().startsWith("-h")) {
				host = arg.substring(2);
			} else if (arg.toLowerCase().startsWith("-d")) {
				db = arg.substring(2);
			} else if (arg.toLowerCase().startsWith("-c")) {
				collection = arg.substring(2);
			} else if (arg.toLowerCase().startsWith("-m")) {
				dmaId = Integer.parseInt(arg.substring(2));
			}

		}

		// Connection to MongoDB
		MongoClient client = new MongoClient(host, port);
		DB twcDB = client.getDB(db);
		DBCollection householdCollection = twcDB.getCollection(collection);

		ProgramStore.setPrograms(programs);
		StationStore.setStations(stations);

		AtomicInteger writtenRecords = new AtomicInteger(0);
		Random rand = new Random();
//		for (int i = 0; i < tuners; i++) {
			new HouseholdRecordsGenerator(householdCollection, new Household(
					Utility.getRandomUUID(40) + "-"
							+ String.valueOf(rand.nextInt(2)), dmaId),
					writtenRecords, records).start();
//		}
	}

}

class HouseholdRecordsGenerator extends Thread {
	
	List<String> lifeStages = new ArrayList<String>();
	List<String> socialClasses = new ArrayList<String>();
	List<String> ageGroups = new ArrayList<String>();
	List<String> genres = new ArrayList<String>();
	List<Integer> dayParts = new ArrayList<Integer>();
	List<String> timeShifts = new ArrayList<String>();
	List<String> mediaHabits = new ArrayList<String>();
	List<String> devices = new ArrayList<String>();
	List<String> weeklyViewings = new ArrayList<String>();
	
	
	public void initializeLists(){
		lifeStages.add("Pre family");
		lifeStages.add("Young family");
		lifeStages.add("Older family");
		lifeStages.add("Post family");
		
		socialClasses.add("A");
		socialClasses.add("B");
		socialClasses.add("C1");
		socialClasses.add("C2");
		socialClasses.add("D");
		socialClasses.add("E");
		
		ageGroups.add("Less than 16");
		ageGroups.add("16-34");
		ageGroups.add("34-55");
		ageGroups.add("More than 55");
		
		dayParts.add(1);
		dayParts.add(2);
		dayParts.add(3);
		dayParts.add(4);
		dayParts.add(5);
		dayParts.add(6);
		
		genres.add("Horror");
		genres.add("Comedy");
		genres.add("Action");
		genres.add("Thriller");
		genres.add("Suspense");
		genres.add("Teen");
		genres.add("Classic");
		genres.add("Violence");
		
		devices.add("TV");
		devices.add("Mobile");
		devices.add("Tab");
		devices.add("Laptop");
		devices.add("Desktop");
		
		weeklyViewings.add("less than 10 hours");
		weeklyViewings.add("10 to 30 hours");
		weeklyViewings.add("more than 30 hours");
		
		timeShifts.add("live");
		timeShifts.add("Same Day");
		timeShifts.add("delayed");
		
		mediaHabits.add("heavy");
		mediaHabits.add("binge");
		mediaHabits.add("intermittent");
	}
	
	
	
	
	
	DBCollection householdCollection;
//	Household tuner;
	AtomicInteger writtenRecords;
	int totalRecords;
	Calendar runningCalendar = Calendar.getInstance();

	public HouseholdRecordsGenerator(){}
	public HouseholdRecordsGenerator(DBCollection householdCollection, Household household,
			AtomicInteger writtenRecords, int totalRecords) {
		
		new HouseholdRecordsGenerator().initializeLists();
		this.householdCollection = householdCollection;
//		this.tuner = tuner;
		this.writtenRecords = writtenRecords;
		this.totalRecords = totalRecords;
		// 0000, One month ago
		runningCalendar.add(Calendar.DATE, -30);
		runningCalendar.set(Calendar.HOUR_OF_DAY, 0);
		runningCalendar.set(Calendar.MINUTE, 0);
		runningCalendar.set(Calendar.SECOND, 0);
		runningCalendar.set(Calendar.MILLISECOND, 0);
	}

	public void run() {
		Random rand = new Random();
		List<DBObject> entities = new ArrayList<DBObject>();
		initializeLists();
		while (writtenRecords.get() < totalRecords) {
			writtenRecords.incrementAndGet();
			Household entity = new Household();
			
			entity.setHouseholdId(Utility.getRandomUUID(40));
			entity.setDmaId(1001);
			
			entity.setCreatedOn(runningCalendar.getTime());
			entity.setUpdatedOn(runningCalendar.getTime());
	
			System.out.println("Size Lifestages : "+lifeStages.size());
			entity.setDemoLifeStage(lifeStages.get(rand.nextInt(lifeStages.size())));
			entity.setDemoAgeGroup(ageGroups.get(rand.nextInt(ageGroups.size())));
			entity.setDemoSocialClass(socialClasses.get(rand.nextInt(socialClasses.size())));
			entity.setPsychDayPart(dayParts.get(rand.nextInt(dayParts.size())));
			entity.setPsychDevice(devices.get(rand.nextInt(devices.size())));
			entity.setPsychGenre(genres.get(rand.nextInt(genres.size())));
			entity.setPsychMediaHabit(mediaHabits.get(rand.nextInt(mediaHabits.size())));
			entity.setTimeShift(timeShifts.get(rand.nextInt(timeShifts.size())));
			entity.setPsychWeeklyViewing(rand.nextInt(3)+1);
			
			List<Integer> asIds = new ArrayList<Integer>();
			asIds.add(rand.nextInt(80)+1);
			asIds.add(rand.nextInt(80)+1);
			asIds.add(rand.nextInt(80)+1);
			entity.setAsIds(asIds);

			List<Integer> alIds = new ArrayList<Integer>();
			alIds.add(rand.nextInt(80)+1);
			alIds.add(rand.nextInt(80)+1);
			alIds.add(rand.nextInt(80)+1);
			entity.setAlIds(alIds);
			
			entities.add(entity.getBasicDBObject());
			if (entities.size() > 9) {
				householdCollection.insert(entities);
				entities.clear();
			}
		}
		if (entities.size() > 0) {
			householdCollection.insert(entities);
			entities.clear();
		}
	}
}
