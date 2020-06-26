package learn;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Globals {
	static ConcurrentHashMap<Integer, Integer> handleTime = new ConcurrentHashMap<Integer, Integer>();
	static ConcurrentHashMap<Integer, Integer> handleCount = new ConcurrentHashMap<Integer, Integer>();
	static int handlingCount = 0;
	static int cnt = 0;
	
	static ThreadLocal<Integer> rate = new ThreadLocal<Integer>();
	
	static Object handleTimeLock = new Object();
	static Object handleCountLock = new Object();
	static Object rateLock = new Object();
	
	static {
		handleTime.put(1, 0);
		handleTime.put(2, 0);
		handleTime.put(3, 0);
		handleTime.put(4, 0);
		
		handleCount.put(1, 0);
		handleCount.put(2, 0);
		handleCount.put(3, 0);
		handleCount.put(4, 0);
	}
	
	static Object pidLock = new Object();
	
	static double e = 0.0;
	static double fe = 0.0;
	static double de = 0.0;
	
	static ThreadLocal<Integer> round = new ThreadLocal<Integer>();
	static int cnt2 = 0;
	static int handlingCount2 = 0;
}
