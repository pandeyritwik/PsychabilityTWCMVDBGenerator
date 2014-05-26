package com.psychability.twc.mvdbgenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimeZone;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class ProcessAudienceDataGenerator
{
    static String host = "localhost";
    static int port = 27017;
    static String db = "TWC";
    static String hhcollection = "Household";
    static String mvdbColl = "MVDB";
    static String processColl = "PROCESSED_AUDIENCE_DATA";
    static int duration = 300;


    @SuppressWarnings ( "unchecked")
    public static void main( String[] args ) throws Exception
    {
        MongoClient client = new MongoClient( host, port );
        DB twcDB = client.getDB( db );
        DBCollection householdCollection = twcDB.getCollection( hhcollection );
        DBCollection mvdbCollection = twcDB.getCollection( mvdbColl );
        DBCollection processCollection = twcDB.getCollection( processColl );
        DBCursor houseHoldCursor = householdCollection.find();
        List<DBObject> houseHoldlList = new ArrayList<DBObject>();
        while ( houseHoldCursor.hasNext() ) {
            DBObject hhObject = houseHoldCursor.next();
            houseHoldlList.add( hhObject );
        }
        TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );
        Calendar cal = Calendar.getInstance();
        cal.add( Calendar.DATE, -30 );
        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.MINUTE, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MILLISECOND, 0 );
        HashMap<String, TestObject> asObject = new HashMap<String, TestObject>();
        HashMap<String, TestObject> alObject = new HashMap<String, TestObject>();
        for ( DBObject dbObject : houseHoldlList ) {
            List<Integer> asList = (List<Integer>) dbObject.get( "psy360_audience_segments" );
            List<Integer> alList = (List<Integer>) dbObject.get( "psy360_audience_lookalikes" );
            int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
            for ( int weekHour = 73; weekHour <= 96; weekHour++ ) {
                BasicDBObject queryObject = new BasicDBObject();
                queryObject.put( "house_hold_id", dbObject.get( "_id" ) );
                queryObject.put( "view_duration", new BasicDBObject( "$gt", duration ) );
                //queryObject.put( "date", cal.getTime() );
                queryObject.put( "week_hour", weekHour );
                //System.out.println( queryObject.toString() );
                DBCursor mvdbCursor = mvdbCollection.find( queryObject );
                while ( mvdbCursor.hasNext() ) {
                    DBObject mvdbObject = mvdbCursor.next();
                    String stationId = (String) mvdbObject.get( "station_id" );
                    int dmaId = (Integer) mvdbObject.get( "dma_id" );
                    StringBuilder sb = new StringBuilder( stationId ).append( "+" ).append( dmaId ).append( "+" )
                        .append( weekHour );
                    if ( asObject.containsKey( sb.toString() ) ) {
                        TestObject testObject = asObject.get( sb.toString() );
                        HashMap<Integer, Integer> asCount = testObject.getReachCount();
                        for ( Integer asId : asList ) {
                            if ( asCount.containsKey( asId ) ) {
                                int count = asCount.get( asId );
                                asCount.put( asId, count + 1 );
                            }
                        }
                    } else {
                        TestObject testObject = new TestObject();
                        for ( Integer asId : asList ) {
                            testObject.getReachCount().put( asId, 1 );
                        }
                        asObject.put( sb.toString(), testObject );
                    }
                    if ( alObject.containsKey( sb.toString() ) ) {
                        TestObject testObject = alObject.get( sb.toString() );
                        HashMap<Integer, Integer> alCount = testObject.getReachCount();
                        for ( Integer alId : alList ) {
                            if ( alCount.containsKey( alId ) ) {
                                int count = alCount.get( alId );
                                alCount.put( alId, count + 1 );
                            }
                        }
                    } else {
                        TestObject testObject = new TestObject();
                        for ( Integer alId : alList ) {
                            testObject.getReachCount().put( alId, 1 );
                        }
                        alObject.put( sb.toString(), testObject );
                    }
                }
            }
        }
        List<DBObject> processEntities = new ArrayList<DBObject>();
        Iterator<Entry<String, TestObject>> asItr = asObject.entrySet().iterator();
        while ( asItr.hasNext() ) {
            Entry<String, TestObject> asEntry = asItr.next();
            String[] keys = asEntry.getKey().split( "\\+" );
            TestObject testObject = asEntry.getValue();
            Iterator<Entry<Integer, Integer>> mongoItr = testObject.getReachCount().entrySet().iterator();
            while ( mongoItr.hasNext() ) {
                Entry<Integer, Integer> mongoEntry = mongoItr.next();
                ProcessAudienceEntity processEntity = new ProcessAudienceEntity();
                processEntity.setAudienceId( mongoEntry.getKey() );
                processEntity.setAudienceReach( mongoEntry.getValue() );
                processEntity.setDate( cal.getTime() );
                processEntity.setAudienceType( "AS" );
                processEntity.setDmaId( Integer.valueOf( keys[1] ) );
                processEntity.setStationId( keys[0] );
                processEntity.setWeekHour( Integer.valueOf( keys[2] ) );
                processEntities.add( processEntity.getBasicDBObject() );
            }
        }
        Iterator<Entry<String, TestObject>> alItr = alObject.entrySet().iterator();
        while ( alItr.hasNext() ) {
            Entry<String, TestObject> alEntry = alItr.next();
            String[] keys = alEntry.getKey().split( "\\+" );
            TestObject testObject = alEntry.getValue();
            Iterator<Entry<Integer, Integer>> mongoItr = testObject.getReachCount().entrySet().iterator();
            while ( mongoItr.hasNext() ) {
                Entry<Integer, Integer> mongoEntry = mongoItr.next();
                ProcessAudienceEntity processEntity = new ProcessAudienceEntity();
                processEntity.setAudienceId( mongoEntry.getKey() );
                processEntity.setAudienceReach( mongoEntry.getValue() );
                processEntity.setDate( cal.getTime() );
                processEntity.setAudienceType( "AL" );
                processEntity.setDmaId( Integer.valueOf( keys[1] ) );
                processEntity.setStationId( keys[0] );
                processEntity.setWeekHour( Integer.valueOf( keys[2] ) );
                processEntities.add( processEntity.getBasicDBObject() );
            }
        }
        processCollection.insert( processEntities );
    }
}


class TestObject
{
    HashMap<Integer, Integer> reachCount;


    public TestObject()
    {
        reachCount = new HashMap<Integer, Integer>();
    }


    public HashMap<Integer, Integer> getReachCount()
    {
        return reachCount;
    }


    public void setReachCount( HashMap<Integer, Integer> reachCount )
    {
        this.reachCount = reachCount;
    }


}


class ProcessAudienceEntity
{

    private Date date;
    private int weekHour;
    private String stationId;
    private int dmaId;
    private String audienceType;
    private int audienceId;
    private int audienceReach;


    public Date getDate()
    {
        return date;
    }


    public void setDate( Date date )
    {
        this.date = date;
    }


    public int getWeekHour()
    {
        return weekHour;
    }


    public void setWeekHour( int weekHour )
    {
        this.weekHour = weekHour;
    }


    public String getStationId()
    {
        return stationId;
    }


    public void setStationId( String stationId )
    {
        this.stationId = stationId;
    }


    public int getDmaId()
    {
        return dmaId;
    }


    public void setDmaId( int dmaId )
    {
        this.dmaId = dmaId;
    }


    public String getAudienceType()
    {
        return audienceType;
    }


    public void setAudienceType( String audienceType )
    {
        this.audienceType = audienceType;
    }


    public int getAudienceId()
    {
        return audienceId;
    }


    public void setAudienceId( int audienceId )
    {
        this.audienceId = audienceId;
    }


    public int getAudienceReach()
    {
        return audienceReach;
    }


    public void setAudienceReach( int audienceReach )
    {
        this.audienceReach = audienceReach;
    }


    public BasicDBObject getBasicDBObject()
    {
        BasicDBObject object = new BasicDBObject();
        object.put( "date", getDate() );
        object.put( "week_hour", getWeekHour() );
        object.put( "station_id", getStationId() );
        object.put( "dma_id", getDmaId() );
        object.put( "audience_type", getAudienceType() );
        object.put( "audience_id", getAudienceId() );
        object.put( "audience_reach", getAudienceReach() );
        return object;
    }
}
