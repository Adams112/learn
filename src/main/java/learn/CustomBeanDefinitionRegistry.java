package learn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println(123);
		BeanDefinition beanDefinition = new RootBeanDefinition(A2.class);
//		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) registry.getBeanDefinition("app");
//		try {
//			Class<?> c = Class.forName(abd.getBeanClassName());
//			System.out.println(c.getName());
//			Field[] fields = c.getDeclaredFields();
//			for (Field field : fields) {
//				System.out.println(field);
//				Arrays.asList(field.getAnnotations()).forEach(System.out::println);
//			}
//			Method[] methods = c.getMethods();
//			for (Method method : methods) {
//				System.out.println(method);
//				Arrays.asList(method.getAnnotations()).forEach(System.out::println);
//			}
//			Arrays.asList(c.getAnnotations()).forEach(System.out::println);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		
		
		registry.registerBeanDefinition("a", beanDefinition);
		AnnotatedBeanDefinition bd = (AnnotatedBeanDefinition) registry.getBeanDefinition("adaptive");
		System.out.println(bd.getBeanClassName());
		System.out.println();
		registry.registerBeanDefinition("adaptive", new RootBeanDefinition(Adaptive.class));
	}

}
