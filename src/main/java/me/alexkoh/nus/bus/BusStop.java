package me.alexkoh.nus.bus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BusStop {

	private String name;
	private String description;
	private double lat;
	private double lon;

	public BusStop(String name, String description, double lat, double lon) {
		this.name = name;
		this.description = description;
		this.lat = lat;
		this.lon = lon;
	}

	public BusStop(JsonElement element) {
		JsonObject busStopObj = element.getAsJsonObject();

		name = busStopObj.get("name").getAsString();
		description = busStopObj.get("caption").getAsString();
		lat = busStopObj.get("latitude").getAsDouble();
		lon = busStopObj.get("longitude").getAsDouble();
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}
}
