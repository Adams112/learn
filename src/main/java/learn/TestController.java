package learn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {
	@Autowired
	private PersonService personService;
	@Autowired
	private Adaptive adaptive;
	
	@RequestMapping("/test")
	@ResponseBody
	public String func() throws Exception {
//		return personService.insert();
		return adaptive.getName();
	}
}
