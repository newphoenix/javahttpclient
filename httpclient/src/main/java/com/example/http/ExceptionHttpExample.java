package com.example.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ExceptionHttpExample {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest tokenRequest = HttpRequest.newBuilder()//
				.uri(URI.create("http://localhost:8080/authenticate"))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString("""
						{
						 "username":"john",
						 "password":"justdoit"
						 }
						""")).build();

		HttpResponse<String> response = client.send(tokenRequest, BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());
		
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		
		if(response.statusCode() != 200) {	
			PrintError(response, om);
			return;
		}	
		
		JwtToken jwtToken =  om.readValue(response.body(), JwtToken.class);
				
		//----- user request
		HttpRequest grequest = HttpRequest.newBuilder()//
				.headers("Content-Type", "application/json", //
				         "Authorization", "Bearer "+jwtToken.token())
				.uri(URI.create("http://localhost:8080/users/2")).build();
		
		HttpResponse<String> gresponse = client.send(grequest, BodyHandlers.ofString());
		
		if(response.statusCode() != 200) {
			PrintError(gresponse, om);
			return;
		}
			System.out.println(gresponse.statusCode());
			System.out.println(gresponse.body());		
		
	}

	private static void PrintError(HttpResponse<String> response, ObjectMapper om)
			throws JsonProcessingException, JsonMappingException {
		ApiError error	 =  om.readValue(response.body(), ApiError.class);
		 System.out.println(error);
	}

}
