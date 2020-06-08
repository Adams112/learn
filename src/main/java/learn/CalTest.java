package learn;

public class CalTest {
	public static void main(String[] args) {
		
	}
	
	
	public void expCpu() {
		double kp = 0.05, ki = 0.01, kd = 0.01;
		int timeSpan = 2000;
		int loadPerSec = 100;
		int loadTimeUsageRef = 200;
		int[] round = {1000, 2000, 4000, 8000, 16000};
		int[] timeUse = {25, 50, 100, 200, 400};
		
		boolean[] loadQueue = new boolean[timeSpan];
		for (int i = 0; i < timeSpan; i++) {
			loadQueue[i] = false;
		}
		for (int i = loadPerSec; i < timeSpan * loadPerSec / 1000; i++) {
			loadQueue[1000 * i / loadPerSec] = true;
		}
		
		
	}
}
