package jy.com.task;

import org.springframework.stereotype.Component;

@Component
public class DynamicTask {

	public void executeTask() {
		System.out.println("Task executed");
	}
}