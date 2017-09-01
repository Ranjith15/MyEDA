package com.edassist.util;

public class MemoryMonitor {
	public static void ouputMemoryUsage(String className) {
		; // (className+ ": Total Memory ["+Runtime.getRuntime().totalMemory()+"], Free Memory ["+Runtime.getRuntime().freeMemory()+"], Memory Usage ["+(Runtime.getRuntime().totalMemory() -
			// Runtime.getRuntime().freeMemory())+"]");
	}

}
