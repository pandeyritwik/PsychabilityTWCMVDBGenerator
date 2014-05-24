package com.psychability.twc.store;

import java.util.ArrayList;
import java.util.List;

import com.psychability.twc.entities.Station;
import com.psychability.twc.utility.Utility;

public class StationStore {
	private static List<Station> stations;
	private static int position;

	public static void setStations(int stationCount) {
		if (stationCount == 0) {
			stationCount = 10;
		}
		stations = new ArrayList<Station>();
		for (int i = 0; i < stationCount; i++) {
			Station station = new Station();
			station.setStationName("Random Station " + String.valueOf(i + 1));
			station.setStationId(Utility.getRandomUUID(20));
			stations.add(station);
		}
		position = 0;
	}

	public static synchronized Station getNextStation() {
		if (position == stations.size()) {
			position = 0;
		}
		return stations.get(position++);
	}
}
