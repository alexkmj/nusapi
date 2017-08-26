package me.alexkoh.nus.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Request {

	private String url;
	private Method method;
	private Map<String, String> headers;
	private Map<String, String> parameters;
	private boolean error;
	private Response response;

	public Request(String url, Method method) {
		this.url = url;
		this.method = method;
		headers = new HashMap<String, String>();
		parameters = new HashMap<String, String>();
		error = false;
		response = null;
	}

	public void connect() {
		try {
			response = Jsoup.connect(url)
				.method(method)
				.headers(headers)
				.data(parameters)
				.timeout(10000)
				.postDataCharset("UTF-8")
				.ignoreContentType(true)
				.execute();
		} catch (IOException e) {
			error = true;
			System.err.println(e);
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void addParameter(String key, String value) {
		parameters.put(key, value);
	}

	public boolean isError() {
		return error;
	}

	public Response getResponse() {
		return response;
	}

	public JsonElement getJsonResponse() {
		JsonParser parser = new JsonParser();
		String responseRaw = response.body();
		return parser.parse(responseRaw);
	}
}