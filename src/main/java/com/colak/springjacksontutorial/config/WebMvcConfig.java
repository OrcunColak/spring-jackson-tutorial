package com.colak.springjacksontutorial.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

// @Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // Removal of XML support in response
    // MappingJackson2XmlHttpMessageConverter converter is added after the jackson-dataformat-xml library is added.
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var xmlConverters = converters
                .stream()
                .filter(MappingJackson2XmlHttpMessageConverter.class::isInstance)
                .toList();
        xmlConverters.forEach(converters::remove);
    }


    // 2. Changing the converter selection logic by changing headers
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // the default media type is application/json
        configurer.defaultContentType(MediaType.APPLICATION_JSON);

        configurer.strategies(List.of((NativeWebRequest webRequest) -> {
            var userAgent = webRequest.getHeader("User-Agent");
            var accept = webRequest.getHeader("Accept");

            // if there is no accept header, the application/json converter will be selected.
            if (accept == null || accept.isBlank()) {
                return List.of(MediaType.APPLICATION_JSON);
            }

            // Look at the user-agent header and check that if the request came from the browser,
            // we add application/json first in the list (i.e. higher priority),
            var accepts = new ArrayList<MediaType>();
            var acceptValues = accept.split(",");

            if (userAgent != null && userAgent.contains("Mozilla")) {
                accepts.add(MediaType.APPLICATION_JSON);
            }

            for (var acceptValue : acceptValues) {
                if (!acceptValue.isBlank()) {
                    accepts.add(MediaType.parseMediaType(acceptValue.strip()));
                }
            }

            if (accepts.isEmpty()) {
                accepts.add(MediaType.APPLICATION_JSON);
            }

            return accepts;
        }));
    }

    // 3. Use a parameter in the URL request
    // use the mediaType parameter to select if you want the response in XML or JSON, the accept header is ignored,
    // we disable its use.
    public void configureContentNegotiation1(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.favorParameter(true);

        configurer.ignoreAcceptHeader(true);

        configurer.parameterName("mediaType");
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
    }
}
