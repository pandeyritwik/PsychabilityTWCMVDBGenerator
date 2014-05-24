package com.psychability.twc.entities;

import java.util.Date;
import java.util.List;

public class Program {
	private long programId;
	private String title;
	private List<String> programGenres;
	private String rating;
	private int runtime;
	private String networkId;
	private String networkName;
	private List<String> actors;
	private List<String> languages;
	private List<String> countries;
	private String rated;
	private Double metaScore;
	private Date releaseDate;
	private String year;
	private String plot;
	private Date createdOn;
	private Date updatedOn;
	

	public long getProgramId() {
		return programId;
	}

	public void setProgramId(long programId) {
		this.programId = programId;
	}

	public List<String> getProgramGenres() {
		return programGenres;
	}

	public void setProgramGenres(List<String> programGenres) {
		this.programGenres = programGenres;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public Double getMetaScore() {
		return metaScore;
	}

	public void setMetaScore(Double metaScore) {
		this.metaScore = metaScore;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
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

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
}
