package me.alexkoh.nus.nusmods;

import org.jsoup.Connection.Method;

import com.google.gson.JsonObject;

import me.alexkoh.nus.util.Request;

public class Module {

	private String code;
	private String title;
	private String department;
	private String description;

	public Module(String code, String year) {
		this.code = code;

		String url = "https://nusmods.com/api/" + year + "/modules/" + code + "/index.json";
		Request req = new Request(url, Method.GET);
		req.connect();

		JsonObject jsonObject = req.getJsonResponse().getAsJsonObject();

		title = jsonObject.get("ModuleTitle").getAsString();
		department = jsonObject.get("Department").getAsString();
		description = jsonObject.get("ModuleDescription").getAsString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}