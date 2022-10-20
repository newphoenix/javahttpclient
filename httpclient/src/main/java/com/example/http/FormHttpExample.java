package com.example.http;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FormHttpExample {
	
	private static Charset UTF8 = StandardCharsets.UTF_8;
    
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

		HttpResponse<String> tokenResponse = client.send(tokenRequest, BodyHandlers.ofString());

		System.out.println(tokenResponse.statusCode());
		System.out.println(tokenResponse.body());		
		
		ObjectMapper om = new ObjectMapper();
		
		JwtToken jwtToken =  om.readValue(tokenResponse.body(), JwtToken.class);
    	
        Map<String, Object> data = Map.of(
        		"username","laura", //
        		"password","lpassword", //
        		"email","laura@live.com", //
        		"enabled",true);
        
        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create("http://localhost:8080/users"))
        		.headers("Authorization", "Bearer "+jwtToken.token(), //
						 "Content-Type", "application/x-www-form-urlencoded")
                .POST(BodyPublishers.ofString(formData(data)))
                .build();
        
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
		
	}
    
    
	public static String formData(Map<String, Object> data) {
	
		StringBuilder builder = new StringBuilder();
				
		data.forEach((key,value)-> {
			
			if (builder.length() > 0) {
				builder.append("&");
			}
					
			builder
			.append(URLEncoder.encode(key, UTF8))
			.append("=")
			.append(URLEncoder.encode(value.toString(), UTF8));
			
		});

		return builder.toString();
	}

}
