package com.example.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class HttpExample {

	public static void main(String[] args) throws IOException, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest grequest = HttpRequest.newBuilder()//
				.uri(URI.create("https://jsonplaceholder.typicode.com/posts/1")).build();
		
		HttpResponse<String> gresponse = client.send(grequest, BodyHandlers.ofString());

		System.out.println(gresponse.statusCode());
		System.out.println(gresponse.body());

		// INSERT
		HttpRequest request = HttpRequest.newBuilder()//
				.uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString("""
						{
						 "title":"foo",
						 "body":"bar",
						 "userId":1
						 }
						""")).build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());

		// UPDATE
		HttpRequest request2 = HttpRequest.newBuilder()//
				.uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
				.header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString("""
						{
						 "id":1,
						 "title":"foo",
						 "body":"bar",
						 "userId":1
						 }
						""")).build();

		HttpResponse<String> response2 = client.send(request2, BodyHandlers.ofString());

		System.out.println(response2.statusCode());
		System.out.println(response2.body());

		// DELETE
		HttpRequest request3 = HttpRequest.newBuilder()//
				.uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
				.header("Content-Type", "application/json")				
				.DELETE()
				.build();

		HttpResponse<String> response3 = client.send(request3, BodyHandlers.ofString());

		System.out.println(response3.statusCode());
		System.out.println(response3.body());

	}

}
