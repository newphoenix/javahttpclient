package com.example.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SecureHttpExample {
	
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
		
		JwtToken jwtToken =  om.readValue(response.body(), JwtToken.class);
				
		//----- user request
		HttpRequest grequest = HttpRequest.newBuilder()//
				.headers("Content-Type", "application/json",//
						 "Authorization", "Bearer "+jwtToken.token())
				.uri(URI.create("http://localhost:8080/users/2")).build();
		
		HttpResponse<String> gresponse = client.send(grequest, BodyHandlers.ofString());

		System.out.println(gresponse.statusCode());
		System.out.println(gresponse.body());
	}

}
