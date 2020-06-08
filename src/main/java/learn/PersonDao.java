package learn;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonDao {
	
//	@Insert("insert into person(person_id, person_name, person_email, person_sex) values (#{id}, #{name}, #{email}, #{sex})")
	void insert(Person p);
	
	List<Person> getAll();
}
