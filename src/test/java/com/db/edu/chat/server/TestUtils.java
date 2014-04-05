package com.db.edu.chat.server;

public class TestUtils {
	public static void sleep(long millis) {
		try { 
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
