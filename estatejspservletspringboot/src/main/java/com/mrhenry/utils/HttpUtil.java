package com.mrhenry.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {
	private String value;

	public HttpUtil(String value) {
		this.value = value;
	}

	// convert JSON --> String
	public static HttpUtil of(BufferedReader reader) {
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return new HttpUtil(sb.toString());
	}

	// mapping String đã được chuyển từ JSON với Object(model)
	public <T> T toModel(Class<T> tClass) {
		try {
			return new ObjectMapper().readValue(value, tClass);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	// mapping Json đã được chuyển từ String (ở hàm toModel()) với Object(model)
	public <T> T toJson(HttpServletResponse response, Object object) {
		try {
			// return new ObjectMapper().writeValue(response.getOutputStream(),
			// toModel(tClass));
			new ObjectMapper().writeValue(response.getOutputStream(), object);
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
