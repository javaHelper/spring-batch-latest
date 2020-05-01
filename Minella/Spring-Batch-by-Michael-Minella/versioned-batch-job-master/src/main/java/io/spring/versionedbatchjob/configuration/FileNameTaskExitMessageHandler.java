package io.spring.versionedbatchjob.configuration;

import java.io.File;

import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;


@Component
public class FileNameTaskExitMessageHandler {

	@AfterTask
	public void afterTask(TaskExecution taskExecution) {
		String name = new File(BatchConfiguration.class.getProtectionDomain()
				.getCodeSource()
				.getLocation().getPath())
				.getParentFile()
				.getParentFile()
				.getName();

		System.out.println(">> The jar file being executed is = " + name.substring(0, name.length() - 1));

		taskExecution.setExitMessage("JAR executed was: " + name.substring(0, name.length() - 1));
	}
}
