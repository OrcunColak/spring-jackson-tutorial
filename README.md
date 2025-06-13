#  Centralized  Configuration Example
See https://towardsdev.com/escaping-the-objectmapper-hell-clean-modern-json-handling-in-java-without-losing-your-mind-d1b35f5809a6

```java
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
```
# Jackson Afterburner
This speeds up reflection-heavy conversions and reduces temporary object churn.
```xml
<dependency>
  <groupId>com.fasterxml.jackson.module</groupId>
  <artifactId>jackson-module-afterburner</artifactId>
</dependency>
```
then
```
@Bean
public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new AfterburnerModule())
        // other custom config...
        ;
}
```
or
```
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new AfterburnerModule());
```

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
