package learn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Autowired
	PersonDao personDao;
	
	
	public String insert() throws Exception {
		Person p = new Person();
		String id = String.valueOf( System.currentTimeMillis() );
		p.setEmail("email");
		p.setId(id);
		p.setName("name");
		p.setSex("male");
		personDao.insert(p);
		System.out.println(p);
		return "abc";
//		p = new Person();
//		id = String.valueOf( System.currentTimeMillis() );
//		p.setEmail("email");
//		p.setId(id);
//		p.setName("name");
//		p.setSex("male");
//		personDao.insert(p);
	}
	
	public List<Person> getAll() {
		return personDao.getAll();
	}
}
