package learn;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;


@Configuration

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		String[] beanNames = beanFactory.getBeanDefinitionNames();
//		Arrays.asList(beanNames).forEach(System.out::println);
//		BeanDefinition bd = beanFactory.getBeanDefinition("app");
//		
//		if(bd instanceof AnnotatedBeanDefinition) {
//			AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) bd;
//			
//		}
	}

}
