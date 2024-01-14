package com.softwaremind.recruitment.gkisiel.configuration;

import com.softwaremind.recruitment.gkisiel.enums.SortBy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }

    private static class StringToEnumConverter implements Converter<String, SortBy> {
        @Override
        public SortBy convert(String source) {
            return SortBy.valueOf(source.toUpperCase());
        }
    }
}
