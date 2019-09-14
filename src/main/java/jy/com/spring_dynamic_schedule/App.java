package jy.com.spring_dynamic_schedule;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jy.com.config.ApplicationContextConfig;

/**
 * Hello world!
 *
 */
public class App 
{
    @SuppressWarnings("resource")
	public static void main( String[] args )
    {
    	new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
    }
}
