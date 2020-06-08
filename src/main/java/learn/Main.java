package learn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Random;
import java.util.zip.Deflater;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.coobird.thumbnailator.Thumbnails;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

public class Main {
	
	public static void main(String[] args) throws Exception {
//        File f = new File("C:\\Users\\taylor\\Desktop\\����ͼƬ\\ceshi.png");
//        int length = (int) f.length();
//        byte[] data = new byte[length];
//        new FileInputStream(f).read(data);
//
//        	FileOutputStream fos = new FileOutputStream("C:\\Users\\taylor\\Desktop\\����ͼƬ\\ceshi4.png");
//        	compress(data, 1).writeTo(fos);
        	
//		
//		Runnable r = () -> {while(true);};
//		for(int i = 0; i < 9; i++) {
//			new Thread(r).start();
//		}
//		
//		while(true) {
//			printProcessors();
//			Thread.sleep(5000);
//		}
		
		System.out.println(-1 % 10000);
		System.out.println(-1 / 10000);
	}
	
	public static void printProcessors() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        
        processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        
        StringBuilder procCpu = new StringBuilder("CPU load per processor:");
        double[] load = processor.getProcessorCpuLoadBetweenTicks();
        for (double avg : load) {
            procCpu.append(String.format(" %.1f%%", avg * 100));
        }
        System.out.println(procCpu.toString());
	}

	 public static ByteArrayOutputStream compress(byte[] input, double rate) throws IOException {
		 ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
         Thumbnails.of(inputStream)
                 .scale(1f)
                 .outputQuality(rate)
                 .toOutputStream(outputStream);
         return outputStream;

	}
	 
	 public static double getProcessCpuLoad() throws Exception {

		    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
		    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
		    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

		    if (list.isEmpty())     return Double.NaN;

		    Attribute att = (Attribute)list.get(0);
		    Double value  = (Double)att.getValue();

		    // usually takes a couple of seconds before we get real values
		    if (value == -1.0)      return Double.NaN;
		    // returns a percentage value with 1 decimal point precision
		    double ratio = (int)(value * 1000) / 10.0;
		    return ratio;
		}

}
