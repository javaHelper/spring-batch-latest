package com.example.configuration;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobLaunchingController {
	@Autowired
	private JobOperator jobOperator;

	@PostMapping("/")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public long launch(@RequestParam("name") String name) throws Exception {
		return this.jobOperator.start("job", String.format("name=%s", name));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void stop(@PathVariable("id") long id) throws Exception {
		this.jobOperator.stop(id);
	}
}
