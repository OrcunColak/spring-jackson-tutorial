# Content Negotiation

The original idea is from  
https://blog.dimkus.com/solving-the-xml-response-problem-with-jackson-dataformat-xml-60b2759afb4a

But making the method to return @GetMapping(path = "...", produces = MediaType.APPLICATION_JSON_VALUE)
is easier

# Timezone
Set the time zone of Jackson which is used for JSON serialization and deserialization in application.properties  
```
spring.jackson.time-zone=UTC
```
