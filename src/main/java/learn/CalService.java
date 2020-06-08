package learn;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalService {
	static int jround;
	static {
	try {
		Properties p = PropertiesLoaderUtils.loadAllProperties("application.properties");
		jround = Integer.valueOf((String) p.get("jround"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
	
	Random r = new Random();
	@PID
	@ResponseBody
	@RequestMapping("/cal")
	public String func() {
		
		int round = Globals.round.get();
		
		int c1 = r.nextInt(), c2 = r.nextInt();
		for(int i = 0; i < round; i++) {
			for(int j = 0; j < jround; j++) {
				for(int k = 0; k < 1; k++) {
					c2 = c1 + 1;
					c1 = c2 + 1;
					c1 &= c2;
					c2 |= c1;
				}

			}
		}
		return String.valueOf(c1 + c2);
	}
}
