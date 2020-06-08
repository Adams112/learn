package learn;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@org.aspectj.lang.annotation.Aspect
@Component
public class PIDAspect {

	static double kp, ki, kd;
	
	static {
	try {
		Properties p = PropertiesLoaderUtils.loadAllProperties("application.properties");
		kp = Double.valueOf((String) p.get("kp"));
		ki = Double.valueOf((String) p.get("ki"));
		kd = Double.valueOf((String) p.get("kd"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
	
	
	@Pointcut("@annotation(learn.PID)")
	public void pointCut() {
		
	}
	
	
	
	@Around("pointCut()")
	public Object around(JoinPoint joinPoint) {
		Object res = null;
		try {
			double e = 0, de = 0, fe = 0; 
			synchronized (Globals.pidLock) {
				e = Globals.e;
				de = Globals.de;
				fe = Globals.fe;
			}
			
			double u = kp * e + ki * fe + kd * de;
			
			
			
			// 5000, 10000, 20000, 40000, 8000
			// 25, 50, 100, 200, 400ms
			Globals.round.set(20000);
			int cnt = Globals.cnt2++;
//			System.out.println("cnt: " + cnt + ", e: " + e + ", u: " + u + ", round: " + round + ", handlingCount: " + Globals.handlingCount);
			
			long start = System.currentTimeMillis();
			res = ((ProceedingJoinPoint)joinPoint).proceed();
			long endtime = System.currentTimeMillis();
			
			System.out.println("cnt: " + cnt + ", executiontime: " + (endtime - start));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return res;
		
	}
}
