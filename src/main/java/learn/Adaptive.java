package learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Adaptive {

	
	private String name = "123";

	@Anno
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
