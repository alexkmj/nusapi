package me.alexkoh.nus.bus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BusArrival {

	private String busName;
	private String arrival;
	private String nextArrival;

	public BusArrival(String busName, String arrival, String nextArrival) {
		this.busName = busName;
		this.arrival = arrival;
		this.nextArrival = nextArrival;
	}

	public BusArrival(JsonElement element) {
		JsonObject arrivalTimeObj = element.getAsJsonObject();

		busName = arrivalTimeObj.get("name").getAsString();
		arrival = arrivalTimeObj.get("arrivalTime").getAsString();
		nextArrival = arrivalTimeObj.get("nextArrivalTime").getAsString();
	}

	public String getBusName() {
		return busName;
	}

	public String getArrival() {
		return arrival;
	}

	public String getNextArrival() {
		return nextArrival;
	}
}