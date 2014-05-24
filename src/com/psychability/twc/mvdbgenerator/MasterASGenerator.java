package com.psychability.twc.mvdbgenerator;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


public class MasterASGenerator
{
    static String host = "localhost";
    static int port = 27017;
    static String db = "TWC";
    static String collection = "Household";
    static String masterAsColl = "Master_AS";


    public static void main( String[] args ) throws UnknownHostException
    {
        MongoClient client = new MongoClient( host, port );
        DB twcDB = client.getDB( db );
        DBCollection householdCollection = twcDB.getCollection( collection );
        DBCollection masterAsCollection = twcDB.getCollection( masterAsColl );
        List<String> lifeStageList = getLifeStageList( householdCollection );
        List<String> socialClassList = getSocialClassList( householdCollection );
        List<String> ageGroupList = getAgeGroupList( householdCollection );
        int i = 1;
        List<DBObject> asMasterlist = new ArrayList<DBObject>();
        for ( String lifeStage : lifeStageList ) {
            for ( String socialClass : socialClassList ) {
                for ( String ageGroup : ageGroupList ) {
                    StringBuilder sb = new StringBuilder( ageGroup );
                    sb.append( "-" );
                    sb.append( lifeStage );
                    sb.append( "-" );
                    sb.append( socialClass );
                    BasicDBObject dbObject = new BasicDBObject( "psy360_segment_id", i );
                    dbObject.append( "psy360_segment_name", sb.toString() );
                    dbObject.append( "social_class", socialClass );
                    dbObject.append( "age_group", ageGroup );
                    dbObject.append( "life_stage", lifeStage );
                    asMasterlist.add( dbObject );
                    i++;
                }
            }
        }
        masterAsCollection.insert( asMasterlist );
    }


    @SuppressWarnings ( "unchecked")
    private static List<String> getAgeGroupList( DBCollection householdCollection )
    {
        return householdCollection.distinct( "demo_age_group" );
    }


    @SuppressWarnings ( "unchecked")
    private static List<String> getSocialClassList( DBCollection householdCollection )
    {
        return householdCollection.distinct( "demo_social_class" );
    }


    @SuppressWarnings ( "unchecked")
    private static List<String> getLifeStageList( DBCollection householdCollection )
    {
        return householdCollection.distinct( "demo_life_stage" );
    }
}
