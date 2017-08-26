package me.alexkoh.nus.nusmods;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jsoup.Connection.Method;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.alexkoh.nus.util.Request;

public class Timetable {

	private static String BASE_URL = "https://nusmods.com/timetable/";
	private String year;
	private String sem;
	private List<Slot> slotList;

	public Timetable(String yourls) throws UnsupportedEncodingException {

		// connect
		Request request = new Request(yourls, Method.GET);
		request.connect();

		// get redirected url
		String redirectedUrl = request.getResponse().url().toString();
		redirectedUrl = java.net.URLDecoder.decode(redirectedUrl, "UTF-8");

		// get year and sem
		year = redirectedUrl.replace(BASE_URL, "").substring(0, 9);
		sem = redirectedUrl.replace(BASE_URL, "").substring(13,14);

		// process slot list
		String parameters = redirectedUrl.substring(redirectedUrl.indexOf("?") + 1);
		String[] modules = parameters.split("&");

		// populate module mapping
		Map<String, List<String>> moduleMap = new HashMap<String, List<String>>();

		for(String moduleRaw : modules) {
			String code = moduleRaw.substring(0, moduleRaw.indexOf("["));
			String group = moduleRaw.substring(moduleRaw.indexOf("[") + 1, moduleRaw.indexOf("[") + 3).toLowerCase();

			group = group + moduleRaw.substring(moduleRaw.indexOf("=") + 1);

			if(moduleMap.containsKey(code)) {
				List<String> groupList = moduleMap.get(code);
				groupList.add(group);
				moduleMap.put(code, groupList);
			} else {
				List<String> groupList = new ArrayList<String>();
				groupList.add(group);
				moduleMap.put(code, groupList);
			}
		}

		// iterate module mapping and populate slot list
		Iterator<Entry<String, List<String>>> iterator = moduleMap.entrySet().iterator();

		slotList = new ArrayList<Slot>();

		while(iterator.hasNext()) {
			// get code and group list
			Map.Entry<String, List<String>> pair = (Map.Entry<String, List<String>>) iterator.next();
			String code = pair.getKey();
			List<String> groupList = pair.getValue();

			// connect to nusmods api
			String url = "https://nusmods.com/api/" + year + "/" + sem + "/modules/" + code + "/timetable.json";
			Request req = new Request(url, Method.GET);
			req.connect();

			// iterate through json response and populate slots
			JsonArray jsonArray = req.getJsonResponse().getAsJsonArray();

			for(JsonElement jsonElement : jsonArray) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				String compareGroup = jsonObject.get("LessonType").getAsString().substring(0, 2).toLowerCase();
				compareGroup = compareGroup + jsonObject.get("ClassNo").getAsString();

				for(String group : groupList) {
					if(compareGroup.contentEquals(group)) {
						slotList.add(new Slot(code, year, jsonObject));
					}
				}
			}
		}
	}

	public String getYear() {
		return year;
	}


	public String getSem() {
		return sem;
	}


	public List<Slot> getSlotList() {
		return slotList;
	}

	public static void main(String[] args) throws IOException {
		Timetable timetable = new Timetable("http://modsn.us/VWHtc");
		List<Slot> slotList = timetable.getSlotList();

		for(Slot slot : slotList) {
			System.out.println("===================Start===================");
			Module module = slot.getModule();
			System.out.println("Module: " + module.getCode() + " " + module.getTitle());
			System.out.println("Type: " + slot.getType());
			System.out.println("Grouping: " + slot.getGroup());
			System.out.println("Venue: " + slot.getVenue());
			System.out.println("Every " + slot.getWeek() + " on " + slot.getDay() + " from " + slot.getStart() + " to " + slot.getEnd());
			System.out.println("Taught by " + module.getDepartment());
			System.out.println("Description: " + module.getDescription());
			System.out.println("====================End====================");
			System.out.println("\n\n");
		}
	}
}