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


kafka-topics.bat --zookeeper localhost:2181 --alter --topic customers --delete-config retention.ms

WARNING: Altering topic configuration from this script has been deprecated and may be removed in future releases.
         Going forward, please use kafka-configs.sh for this functionality
Updated config for topic customers.


```
C:\Users\pc>kafka-topics.bat --list --zookeeper localhost:2181

C:\Users\pc>kafka-topics.bat --list --zookeeper localhost:2181
customers

C:\Users\pc>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic customers --from-beginning
{"id":1,"firstName":" John","lastName":" Doe","birthdate":{"month":"OCTOBER","year":1952,"dayOfYear":284,"dayOfMonth":10,"dayOfWeek":"FRIDAY","hour":10,"minute":10,"monthValue":10,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":2,"firstName":" Amy","lastName":" Eugene","birthdate":{"month":"JULY","year":1985,"dayOfYear":186,"dayOfMonth":5,"dayOfWeek":"FRIDAY","hour":17,"minute":10,"monthValue":7,"nano":0,"second":0,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":3,"firstName":" Laverne","lastName":" Mann","birthdate":{"month":"DECEMBER","year":1988,"dayOfYear":346,"dayOfMonth":11,"dayOfWeek":"SUNDAY","hour":10,"minute":10,"monthValue":12,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":4,"firstName":" Janice","lastName":" Preston","birthdate":{"month":"FEBRUARY","year":1960,"dayOfYear":50,"dayOfMonth":19,"dayOfWeek":"FRIDAY","hour":10,"minute":10,"monthValue":2,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":5,"firstName":" Pauline","lastName":" Rios","birthdate":{"month":"AUGUST","year":1977,"dayOfYear":241,"dayOfMonth":29,"dayOfWeek":"MONDAY","hour":10,"minute":10,"monthValue":8,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":6,"firstName":" Perry","lastName":" Burnside","birthdate":{"month":"MARCH","year":1981,"dayOfYear":69,"dayOfMonth":10,"dayOfWeek":"TUESDAY","hour":10,"minute":10,"monthValue":3,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":7,"firstName":" Todd","lastName":" Kinsey","birthdate":{"month":"DECEMBER","year":1998,"dayOfYear":348,"dayOfMonth":14,"dayOfWeek":"MONDAY","hour":10,"minute":10,"monthValue":12,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":8,"firstName":" Jacqueline","lastName":" Hyde","birthdate":{"month":"MARCH","year":1983,"dayOfYear":79,"dayOfMonth":20,"dayOfWeek":"SUNDAY","hour":10,"minute":10,"monthValue":3,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":9,"firstName":" Rico","lastName":" Hale","birthdate":{"month":"OCTOBER","year":2000,"dayOfYear":284,"dayOfMonth":10,"dayOfWeek":"TUESDAY","hour":10,"minute":10,"monthValue":10,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":10,"firstName":" Samuel","lastName":" Lamm","birthdate":{"month":"NOVEMBER","year":1999,"dayOfYear":315,"dayOfMonth":11,"dayOfWeek":"THURSDAY","hour":10,"minute":10,"monthValue":11,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":11,"firstName":" Robert","lastName":" Coster","birthdate":{"month":"OCTOBER","year":1972,"dayOfYear":284,"dayOfMonth":10,"dayOfWeek":"TUESDAY","hour":10,"minute":10,"monthValue":10,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":12,"firstName":" Tamara","lastName":" Soler","birthdate":{"month":"JANUARY","year":1978,"dayOfYear":2,"dayOfMonth":2,"dayOfWeek":"MONDAY","hour":10,"minute":10,"monthValue":1,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":13,"firstName":" Justin","lastName":" Kramer","birthdate":{"month":"NOVEMBER","year":1951,"dayOfYear":323,"dayOfMonth":19,"dayOfWeek":"MONDAY","hour":10,"minute":10,"monthValue":11,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":14,"firstName":" Andrea","lastName":" Law","birthdate":{"month":"OCTOBER","year":1959,"dayOfYear":287,"dayOfMonth":14,"dayOfWeek":"WEDNESDAY","hour":10,"minute":10,"monthValue":10,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":15,"firstName":" Laura","lastName":" Porter","birthdate":{"month":"DECEMBER","year":2010,"dayOfYear":346,"dayOfMonth":12,"dayOfWeek":"SUNDAY","hour":10,"minute":10,"monthValue":12,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":16,"firstName":" Michael","lastName":" Cantu","birthdate":{"month":"APRIL","year":1999,"dayOfYear":101,"dayOfMonth":11,"dayOfWeek":"SUNDAY","hour":10,"minute":10,"monthValue":4,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":17,"firstName":" Andrew","lastName":" Thomas","birthdate":{"month":"MAY","year":1967,"dayOfYear":124,"dayOfMonth":4,"dayOfWeek":"THURSDAY","hour":10,"minute":10,"monthValue":5,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":18,"firstName":" Jose","lastName":" Hannah","birthdate":{"month":"SEPTEMBER","year":1950,"dayOfYear":259,"dayOfMonth":16,"dayOfWeek":"SATURDAY","hour":10,"minute":10,"monthValue":9,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":19,"firstName":" Valerie","lastName":" Hilbert","birthdate":{"month":"JUNE","year":1966,"dayOfYear":164,"dayOfMonth":13,"dayOfWeek":"MONDAY","hour":10,"minute":10,"monthValue":6,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
{"id":20,"firstName":" Patrick","lastName":" Durham","birthdate":{"month":"OCTOBER","year":1978,"dayOfYear":285,"dayOfMonth":12,"dayOfWeek":"THURSDAY","hour":10,"minute":10,"monthValue":10,"nano":0,"second":10,"chronology":{"id":"ISO","calendarType":"iso8601"}}}
```