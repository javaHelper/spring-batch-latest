[![Release](https://jitpack.io/v/mminella/versioned-batch-job.svg)](https://jitpack.io/#mminella/versioned-batch-job)

# Versioned Batch Job
This job is used in the testing of the CI/CD functionality provided in Spring Cloud Data Flow.  
This job is designed to provide three main features:

* Output the jar that is being executed (to indicate if the jar was changed from run to run)
* Output the contents of the Spring `Environment` to illustrate all properties provided.
* Allow a configurable sleep so it can be used to emulate a long running process.

To configure the sleep, use the property `versioned.batch.wait` with a value in milliseconds.  
Each run through the tasklet will wait for three seconds before seeing if it has waited enough (so a resolution of three seconds is the most accurate this job will do).
 