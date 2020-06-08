package learn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PictureService {
	
	 static String rootPath;
	 
	 static {
			try {
				Properties p = PropertiesLoaderUtils.loadAllProperties("application.properties");
				rootPath = p.getProperty("picture.rootpath");
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	
	String[] fileNames = new String[] {"", "ceshi4.png",
			"ceshi3.png",
			"ceshi2.png",
			"ceshi1.png"};
	byte[] buffer1, buffer2, buffer3, buffer4;
	int len1, len2, len3, len4;
	

	
	public PictureService() {
		try {
			buffer1 = new byte[1024 * 1024 * 20];
			buffer2 = new byte[1024 * 1024 * 20];
			buffer3 = new byte[1024 * 1024 * 20];
			buffer4 = new byte[1024 * 1024 * 20];
			
			FileInputStream in = new FileInputStream(rootPath + fileNames[1]);
			len1 = in.read(buffer1);
			
			in = new FileInputStream(rootPath + fileNames[2]);
			len2 = in.read(buffer2);
			
			in = new FileInputStream(rootPath + fileNames[3]);
			len3 = in.read(buffer3);
			
			in = new FileInputStream(rootPath + fileNames[4]);
			len4 = in.read(buffer4);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Anno
	@RequestMapping("/download")
	public void download(HttpServletResponse response,  HttpServletRequest request) throws IOException {
		int rate = Globals.rate.get();
		String filename = fileNames[rate];
		String fullName = rootPath + filename;
	    response.setCharacterEncoding("utf-8");
	    response.setContentType("multipart/form-data");
	    response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
	    
	    int len = 0;
	    byte[] buffer = null;
	    
	    if(rate == 1) {
	    	len = len1;
	    	buffer = buffer1;
	    } else if(rate == 2) {
	    	len = len2;
	    	buffer = buffer2;
	    } else if(rate == 3) {
	    	len = len3;
	    	buffer = buffer3;
	    } else {
	    	len = len4;
	    	buffer = buffer4;
	    }
	    
        OutputStream out = response.getOutputStream();
        out.write(buffer,0,len);
		
		

	}
	
}
