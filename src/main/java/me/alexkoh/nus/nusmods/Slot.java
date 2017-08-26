package me.alexkoh.nus.nusmods;

import com.google.gson.JsonObject;

public class Slot {

	private Module module;
	private String group;
	private String type;
	private String week;
	private String day;
	private String venue;
	private int start;
	private int end;

	public Slot(String code, String year, JsonObject jsonObject) {
		module = new Module(code, year);
		group = jsonObject.get("ClassNo").getAsString();
		type = jsonObject.get("LessonType").getAsString();
		week = jsonObject.get("WeekText").getAsString();
		day = jsonObject.get("DayText").getAsString();
		venue = jsonObject.get("Venue").getAsString();
		start = jsonObject.get("StartTime").getAsInt();
		end = jsonObject.get("EndTime").getAsInt();
	}

	public Module getModule() {
		return module;
	}

	public String getGroup() {
		return group;
	}

	public String getType() {
		return type;
	}

	public String getWeek() {
		return week;
	}

	public String getDay() {
		return day;
	}

	public String getVenue() {
		return venue;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}