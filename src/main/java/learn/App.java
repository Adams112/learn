package learn;

import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@Configuration
//@ComponentScan(basePackages= {"learn"})
//@EnableAutoConfiguration
//@MapperScan(basePackageClasses = {App.class})
public class App {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
	}

}
