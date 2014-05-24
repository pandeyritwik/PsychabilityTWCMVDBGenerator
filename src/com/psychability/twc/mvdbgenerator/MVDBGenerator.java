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
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.psychability.twc.entities.Household;
import com.psychability.twc.entities.MVDBEntity;
import com.psychability.twc.entities.Program;
import com.psychability.twc.store.ProgramStore;
import com.psychability.twc.store.StationStore;
import com.psychability.twc.utility.Utility;


public class MVDBGenerator
{
    public static void main( String[] args ) throws UnknownHostException, MongoException
    {

        TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );

        String host = "localhost";
        int port = 27017;
        String db = "TWC";
        String collection = "MVDB";
        int tuners = 10;
        int programs = 10;
        int stations = 5;
        int records = 1000;
        int dmaId = 1000;
        for ( String arg : args ) {
            if ( arg.toLowerCase().startsWith( "-t" ) ) {
                tuners = Integer.parseInt( arg.substring( 2 ) );
            } else if ( arg.toLowerCase().startsWith( "-p" ) ) {
                programs = Integer.parseInt( arg.substring( 2 ) );
            } else if ( arg.toLowerCase().startsWith( "-s" ) ) {
                stations = Integer.parseInt( arg.substring( 2 ) );
            } else if ( arg.toLowerCase().startsWith( "-r" ) ) {
                records = Integer.parseInt( arg.substring( 2 ) );
            } else if ( arg.toLowerCase().startsWith( "-h" ) ) {
                host = arg.substring( 2 );
            } else if ( arg.toLowerCase().startsWith( "-d" ) ) {
                db = arg.substring( 2 );
            } else if ( arg.toLowerCase().startsWith( "-c" ) ) {
                collection = arg.substring( 2 );
            } else if ( arg.toLowerCase().startsWith( "-m" ) ) {
                dmaId = Integer.parseInt( arg.substring( 2 ) );
            }

        }

        // Connection to MongoDB
        MongoClient client = new MongoClient( host, port );
        DB twcDB = client.getDB( db );
        DBCollection mvdbCollection = twcDB.getCollection( collection );

        DBCollection hhCollection = twcDB.getCollection( "Household" );
        List<String> list = new ArrayList<String>();
        DBCursor cursor = hhCollection.find();
        while ( cursor.hasNext() ) {
            list.add( cursor.next().get( "_id" ).toString() );
        }

        ProgramStore.setPrograms( programs );
        StationStore.setStations( stations );

        AtomicInteger writtenRecords = new AtomicInteger( 0 );


        Random rand = new Random();
        for ( int i = 0; i < tuners; i++ ) {
            new TunerEventGroupGenerator( mvdbCollection, new Household( list.get( rand.nextInt( list.size() ) ), dmaId ),
                writtenRecords, records ).start();
        }
    }
}


class TunerEventGroupGenerator extends Thread
{
    DBCollection mvdbCollection;
    Household tuner;
    AtomicInteger writtenRecords;
    int totalRecords;
    Calendar runningCalendar;


    public TunerEventGroupGenerator( DBCollection mvdbCollection, Household tuner, AtomicInteger writtenRecords,
        int totalRecords )
    {
        this.mvdbCollection = mvdbCollection;
        this.tuner = tuner;
        this.writtenRecords = writtenRecords;
        this.totalRecords = totalRecords;
        // 0000, One month ago
        runningCalendar = Calendar.getInstance();
        runningCalendar.add( Calendar.DATE, -30 );
        runningCalendar.set( Calendar.HOUR_OF_DAY, 0 );
        runningCalendar.set( Calendar.MINUTE, 0 );
        runningCalendar.set( Calendar.SECOND, 0 );
        runningCalendar.set( Calendar.MILLISECOND, 0 );
    }


    @Override
    public void run()
    {
        Random rand = new Random();
        List<DBObject> entities = new ArrayList<DBObject>();
        while ( writtenRecords.get() < totalRecords ) {
            writtenRecords.incrementAndGet();
            MVDBEntity entity = new MVDBEntity();
            entity.setDate( runningCalendar.getTime() );
            entity.setCreatedOn( runningCalendar.getTime() );
            entity.setUpdatedOn( runningCalendar.getTime() );
            entity.setWeekHour( Utility.getWeekHour( runningCalendar ) );

            Program program = ProgramStore.getNextProgram();
            entity.setProgram( program );

            // Min 5 minutes
            int randomDuration = 300 + rand.nextInt( program.getRuntime() - 300 );
            entity.setViewDuration( randomDuration );

            // Add viewDuration + up to 5 minutes
            runningCalendar.add( Calendar.SECOND, randomDuration + rand.nextInt( 300 ) );

            entity.setStation( StationStore.getNextStation() );
            entity.setTuner( tuner );

            entity.set_id( String.valueOf( entity.getProgram().getProgramId() ) + "_" + entity.getStation().getStationId()
                + "_" + tuner.getHouseholdId() + "_" + String.valueOf( runningCalendar.getTimeInMillis() ) );

            entities.add( entity.getBasicDBObject() );
            if ( entities.size() > 1000 ) {
                mvdbCollection.insert( entities );
                entities.clear();
            }
        }
        if ( entities.size() > 0 ) {
            mvdbCollection.insert( entities );
            entities.clear();
        }
    }
}
