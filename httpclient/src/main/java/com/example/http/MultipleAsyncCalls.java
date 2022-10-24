package com.example.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MultipleAsyncCalls {
	
	private static final String URL = "https://jsonplaceholder.typicode.com/posts/";

	public static void main(String[] args) {
		
		int n =100;
	    ExecutorService executor = Executors.newFixedThreadPool(n);

	    try {
	    	
	    	HttpClient client = HttpClient.newBuilder().executor(executor).build();
			
		     List<HttpRequest> requestList =
		    	      IntStream.rangeClosed(1, n)
		    	      .mapToObj(i -> HttpRequest.newBuilder().uri(URI.create(URL + i)).build())
		    	      .toList();
		     
		      for (int i = 0; i < n; i++) {
		          client.sendAsync(requestList.get(i), BodyHandlers.ofString())//
		          .thenApply(response -> response.body())//
		          .thenAccept(System.out::println);
		          }
		      
		      executor.awaitTermination(4, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			executor.shutdownNow();
		}
	

	}

}
