# Spring Batch and Fafka Writer

# List All Topics

kafka-topics.bat --list --zookeeper localhost:2181

# View Messages

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic customers --from-beginning

# Delete Topic

kafka-topics.bat --zookeeper localhost:2181 --delete --topic test

# First N messages
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning  --max-messages 10

# Next N messages
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --max-messages 10