package com.psychability.twc.entities;

import java.util.Date;

import com.mongodb.BasicDBObject;

public class MVDBEntity {

	private String _id;
	private Household tuner;
	private Program program;
	private Station station;
	private int viewDuration;
	private Date date;
	private int weekHour;
	private Date createdOn;
	private Date updatedOn;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Household getTuner() {
		return tuner;
	}

	public void setTuner(Household tuner) {
		this.tuner = tuner;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getViewDuration() {
		return viewDuration;
	}

	public void setViewDuration(int viewDuration) {
		this.viewDuration = viewDuration;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getWeekHour() {
		return weekHour;
	}

	public void setWeekHour(int weekHour) {
		this.weekHour = weekHour;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BasicDBObject getBasicDBObject() {
		BasicDBObject object = new BasicDBObject();
		object.put("_id", _id);
		object.put("house_hold_id", tuner.getHouseholdId());
		object.put("program_id", program.getProgramId());
		object.put("program_name", program.getTitle());
		object.put("program_genres", program.getProgramGenres());
		object.put("program_rating", program.getRating());
		object.put("program_duration", program.getRuntime());
		object.put("station_id", station.getStationId());
		object.put("view_duration", viewDuration);
		object.put("date", date);
		object.put("week_hour", weekHour);
		object.put("dma_id", tuner.getDmaId());
		object.put("created_on", createdOn);
		object.put("updated_on", updatedOn);
		return object;
	}
}
