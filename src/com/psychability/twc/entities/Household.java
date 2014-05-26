package com.psychability.twc.entities;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;


public class Household
{
    //Device Id and HHID are considered same here.
    private String householdId;
    private int dmaId;
    private List<Integer> asIds;
    private List<Integer> alIds;
    private String demoLifeStage;
    private String demoSocialClass;
    private String demoAgeGroup;
    private Integer psychDayPart;
    private String psychGenre;
    private Double psychRating;
    private String timeShift;
    private String psychMediaHabit;
    private String psychDevice;
    private Integer psychWeeklyViewing;
    private Date createdOn;
    private Date updatedOn;


    public Household()
    {}


    public Household( String householdId, int dmaId )
    {
        this.householdId = householdId;
        this.dmaId = dmaId;
    }


    public String getHouseholdId()
    {
        return householdId;
    }


    public void setHouseholdId( String householdId )
    {
        this.householdId = householdId;
    }


    public int getDmaId()
    {
        return dmaId;
    }


    public void setDmaId( int dmaId )
    {
        this.dmaId = dmaId;
    }


    public List<Integer> getAsIds()
    {
        return asIds;
    }


    public void setAsIds( List<Integer> asIds )
    {
        this.asIds = asIds;
    }


    public List<Integer> getAlIds()
    {
        return alIds;
    }


    public void setAlIds( List<Integer> alIds )
    {
        this.alIds = alIds;
    }


    public String getDemoLifeStage()
    {
        return demoLifeStage;
    }


    public void setDemoLifeStage( String demoLifeStage )
    {
        this.demoLifeStage = demoLifeStage;
    }


    public String getDemoSocialClass()
    {
        return demoSocialClass;
    }


    public void setDemoSocialClass( String demoSocialClass )
    {
        this.demoSocialClass = demoSocialClass;
    }


    public String getDemoAgeGroup()
    {
        return demoAgeGroup;
    }


    public void setDemoAgeGroup( String demoAgeGroup )
    {
        this.demoAgeGroup = demoAgeGroup;
    }


    public Integer getPsychDayPart()
    {
        return psychDayPart;
    }


    public void setPsychDayPart( Integer psychDayPart )
    {
        this.psychDayPart = psychDayPart;
    }


    public String getPsychGenre()
    {
        return psychGenre;
    }


    public void setPsychGenre( String psychGenre )
    {
        this.psychGenre = psychGenre;
    }


    public Double getPsychRating()
    {
        return psychRating;
    }


    public void setPsychRating( Double psychRating )
    {
        this.psychRating = psychRating;
    }


    public String getTimeShift()
    {
        return timeShift;
    }


    public void setTimeShift( String timeShift )
    {
        this.timeShift = timeShift;
    }


    public String getPsychMediaHabit()
    {
        return psychMediaHabit;
    }


    public void setPsychMediaHabit( String psychMediaHabit )
    {
        this.psychMediaHabit = psychMediaHabit;
    }


    public String getPsychDevice()
    {
        return psychDevice;
    }


    public void setPsychDevice( String psychDevice )
    {
        this.psychDevice = psychDevice;
    }


    public Integer getPsychWeeklyViewing()
    {
        return psychWeeklyViewing;
    }


    public void setPsychWeeklyViewing( Integer psychWeeklyViewing )
    {
        this.psychWeeklyViewing = psychWeeklyViewing;
    }


    public Date getCreatedOn()
    {
        return createdOn;
    }


    public void setCreatedOn( Date createdOn )
    {
        this.createdOn = createdOn;
    }


    public Date getUpdatedOn()
    {
        return updatedOn;
    }


    public void setUpdatedOn( Date updatedOn )
    {
        this.updatedOn = updatedOn;
    }


    public BasicDBObject getBasicDBObject()
    {
        BasicDBObject object = new BasicDBObject();
        //	object.put("_id", _id);
        object.put( "_id", householdId );
        object.put( "dma_Id", dmaId );
        object.put( "created_on", createdOn );
        object.put( "updated_on", updatedOn );
        object.put( "psy360_audience_segments", asIds );
        object.put( "psy360_audience_lookalikes", alIds );
        object.put( "demo_life_stage", demoLifeStage );
        object.put( "demo_age_group", demoAgeGroup );
        object.put( "demo_social_class", demoSocialClass );
        object.put( "psych_day_part", psychDayPart );
        object.put( "psych_genre", psychGenre );
        object.put( "psych_rating", psychRating );
        object.put( "psych_time_shift", timeShift );
        object.put( "psych_media_habit", psychMediaHabit );
        object.put( "psych_device", psychDevice );
        object.put( "psych_weekly_viewing", psychWeeklyViewing );
        object.put( "psych_time_shift", timeShift );

        return object;
    }

}
