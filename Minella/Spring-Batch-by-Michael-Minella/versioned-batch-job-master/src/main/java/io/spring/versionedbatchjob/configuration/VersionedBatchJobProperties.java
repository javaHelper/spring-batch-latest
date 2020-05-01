package io.spring.versionedbatchjob.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("versioned.batch")
public class VersionedBatchJobProperties {

	private long wait = 1000L;

	public long getWait() {
		return wait;
	}

	public void setWait(long wait) {
		this.wait = wait;
	}
}
