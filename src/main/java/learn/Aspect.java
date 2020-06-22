package learn;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {
	static int lowTime, highTime;
	static BufferedWriter bw;
	static int tv = 1000000;
	static {
	try {
		Properties p = PropertiesLoaderUtils.loadAllProperties("application.properties");
		lowTime = Integer.valueOf((String) p.get("lowTime"));
		highTime = Integer.valueOf((String) p.get("highTime"));
		
		bw = new BufferedWriter(new FileWriter(new File("/home/paper/ps.txt")));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}


	@Pointcut("@annotation(learn.Anno)")
	public void pointCut() {
		
	}
	
	
	
	@Around("pointCut()")
	public Object around(JoinPoint joinPoint) {
		Object res = null;
		try {
			int count = 0;
			synchronized (Globals.handleCountLock) {
				count = Globals.handlingCount++;
			}
			ConcurrentHashMap<Integer, Integer> handleTime = Globals.handleTime;
			int rate = 1;
			int t = 0, lt = 0, c = 0;
			
			synchronized (handleTime) {
				while(rate <= 4) {
					t = handleTime.get(rate);
					c = Globals.handleCount.get(rate);
					
					if(t == 0) {
						handleTime.put(rate, -1);
						break;
					}
					if(t == -1) {
						rate--;
						if(rate == 0) rate = 1;
						break;
					}
					
					if(t <= lowTime) {
						rate++;
						lt = t;
						if(rate == 5) {
							rate = 4;
							break;
						}
					} else if (t <= highTime) {
						if ((c + 1) * t < highTime) {
							break;
						} else {
							rate--;
							if(rate == 0) rate = 1;
							break;
						}
					}
					else {
						if (lt < (lowTime / 2) && count < 1) break;
						
						rate--;
						if(rate == 0) rate = 1;
						break;
					}
					
					if(count >= 10) {
						rate--;
						if(rate == 0) rate = 1;
					}
				}
				
				
				t = handleTime.get(rate);
				Globals.handleCount.put(rate, c + 1);
			}
			
			
			
			int cnt = Globals.cnt++;
			
			if(cnt % 10 == 0) bw.flush();
			Globals.rate.set(rate);
			
			System.out.println("accept cnt: " + cnt + ", rate: " + rate + ", handletime: " + t + 
					", handleCount: " + count);
			String s = "" + cnt + ", " + rate;
			bw.write(s);
			bw.newLine();
			long start = System.currentTimeMillis();
			res = ((ProceedingJoinPoint)joinPoint).proceed();
			long endtime = System.currentTimeMillis();
			
			int ht = (int) (endtime - start);
			synchronized (handleTime) {
				t = handleTime.get(rate);
				if (ht == 0) ht = 1;
				if (t == -1) {
					t = ht;
				} else {
					t = (t + ht) / 2;
				}
				
				Globals.handleTime.put(rate, t);
			}
			
			synchronized (Globals.handleCount) {
				c = Globals.handleCount.get(rate);
				Globals.handleCount.put(rate, c - 1);
			}

			
			
			System.out.println("handle cnt: " + cnt + 
					", rate: " + rate + ", execution time: " + ht);
			s = "" + cnt + ", " + ht;
			bw.write(s);
			bw.newLine();
			
			synchronized (Globals.handleCountLock) {
				Globals.handlingCount--;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return res;
	}
}
