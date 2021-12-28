package com.mitnickgame.bubblesmash.config;

public class Runner {
	private static boolean isGamePlaying;
	private static boolean isGamePaused;
	private static int ms;
	static Runner runner = null;
	
	private Runner(){
		ms = 0;
	}
	
	public static Runner check(){
		if (runner != null){
			runner = new Runner();
		}
		return runner;
	}
	
	public static boolean isGamePlaying() {
		return isGamePlaying;
	}

	public static boolean isGamePaused() {
		return isGamePaused;
	}

	public static void setGamePlaying(boolean isGamePlaying) {
		Runner.isGamePlaying = isGamePlaying;
	}

	public static void setGamePaused(boolean isGamePaused) {
		Runner.isGamePaused = isGamePaused;
	}

	public static int getMs() {
		return ms;
	}

	public static void setMs(int ms) {
		Runner.ms = ms;
	}
}
