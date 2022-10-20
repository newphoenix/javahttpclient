package com.example.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUploadHttpExample {
	
	private static Charset UTF8 = StandardCharsets.UTF_8;

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {


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

		HttpResponse<String> tokenresponse = client.send(tokenRequest, BodyHandlers.ofString());

		System.out.println(tokenresponse.statusCode());
		System.out.println(tokenresponse.body());		
		
		ObjectMapper om = new ObjectMapper();		
		JwtToken jwtToken =  om.readValue(tokenresponse.body(), JwtToken.class);

		Path path = Path.of(FileUploadHttpExample.class.getResource("/a.txt").toURI());
		byte[] fileData = Files.readAllBytes(path);
		Map<String, byte[]> data = Map.of("cv",fileData);
		
		String boundary = "-------------" + UUID.randomUUID().toString();
        
		
		HttpRequest request = HttpRequest.newBuilder()//
				.uri(URI.create("http://localhost:8080/users/upload"))
				.headers("Authorization", "Bearer "+jwtToken.token(), //
				         "Content-Type", "multipart/form-data; boundary=" + boundary)
				.POST(BodyPublishers.ofByteArrays(createMultipart(data, boundary,"a.txt")))
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		System.out.println(response.statusCode());
		System.out.println(response.body());
		

	}
	
	public URI getUri(String fileName) throws URISyntaxException {
		
		return getClass().getClassLoader().getResource(fileName).toURI();
	}
	
	public static List<byte[]> createMultipart(Map<String, byte[]> data, String boundary, String filename) {
		
		List<byte[]> byteArrays = new ArrayList<>();
		byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(UTF8);

		data.forEach((key, value) -> {

			byteArrays.add(separator);
			byteArrays.add(("\"" + key + "\"; filename=\"" + filename + "\"\r\n\r\n").getBytes(UTF8));
			byteArrays.add(value);
			byteArrays.add("\r\n".getBytes(UTF8));

		});

		byteArrays.add(("--" + boundary + "--").getBytes(UTF8));
		return byteArrays;
	}

}
