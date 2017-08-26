package me.alexkoh.nus.bus;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Method;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.alexkoh.nus.util.Request;

public class BusApi {

	public static String BASE_URL = "https://nextbus.comfortdelgro.com.sg/eventservice.svc/";

	public static List<BusStop> getBusStops() {

		// construct absolute url
		String endpoint = "BusStops";
		String url = BASE_URL + endpoint;

		// connect
		Request req = new Request(url, Method.GET);
		req.connect();

		// parse to json
		JsonObject jsonResponse = req.getJsonResponse().getAsJsonObject();
		JsonArray busStopArr = jsonResponse.get("BusStopsResult").getAsJsonObject().get("busstops").getAsJsonArray();

		// populate bus stop list
		List<BusStop> busStopList = new ArrayList<BusStop>();

		for(JsonElement element : busStopArr) {
			busStopList.add(new BusStop(element));
		}

		// return result
		return busStopList;
	}

	public static List<BusArrival> getArrivalTime(BusStop busStop) {
		return getArrivalTime(busStop.getName());
	}

	public static List<BusArrival> getArrivalTime(String busStop) {

		// construct absolute url
		String endpoint = "Shuttleservice";
		String url = BASE_URL + endpoint;

		// connect
		Request req = new Request(url, Method.GET);
		req.addParameter("busstopname", busStop);
		req.connect();

		// parse to json
		JsonObject jsonResponse = req.getJsonResponse().getAsJsonObject();
		JsonArray arrivalTimeArr = jsonResponse.get("ShuttleServiceResult").getAsJsonObject().get("shuttles").getAsJsonArray();

		// populate bus arrival list
		List<BusArrival> busArrivalList =  new ArrayList<BusArrival>();

		for(JsonElement element : arrivalTimeArr) {
			busArrivalList.add(new BusArrival(element));
		}

		// return result
		return busArrivalList;
	}
}