package learn;

import org.springframework.stereotype.Component;

@Component("a")
public class A1 implements A {

	@Override
	public void f() {
		System.out.println("From A1");
	}

}
