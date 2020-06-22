package learn;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

@Component
@EnableScheduling
public class CpuMonitor {
	
	static double ref;
	static {
	try {
		Properties p = PropertiesLoaderUtils.loadAllProperties("application.properties");
		ref = Double.valueOf((String) p.get("ref"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}

	@Scheduled(cron = "* * * * * *")
	public void monitor() {
		SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        
        processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        
//        StringBuffer procCpu = new StringBuffer("CPU load per processor:");
        double[] load = processor.getProcessorCpuLoadBetweenTicks();
        double average = 0.0;
        for (double avg : load) {
//            procCpu.append(String.format(" %.1f%%", avg * 100));
            average += avg;
        }
//        System.out.println(procCpu.toString());
        average /= load.length;
        synchronized (Globals.pidLock) {
			double lastE = Globals.e;
			Globals.e = ref - average;
			Globals.de = Globals.e - lastE;
			Globals.fe = lastE + Globals.e;
		}
	}
}
