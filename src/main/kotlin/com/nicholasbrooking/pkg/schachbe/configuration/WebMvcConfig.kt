package com.nicholasbrooking.pkg.schachbe.configuration

import com.nicholasbrooking.pkg.schachbe.service.mapping.ApiStringToEnumConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class WebMvcConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedHeaders("*")
                .allowedMethods("*")
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(ApiStringToEnumConverter())
    }

}
