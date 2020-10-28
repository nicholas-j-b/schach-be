package com.nicholasbrooking.pkg.schachbe.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class SecurityConfig(
        val dataSource: DataSource
) : WebSecurityConfigurerAdapter() {
    private val getUserQueryString =
            "SELECT username, password, enabled FROM user WHERE username = ?"
    private val getAuthoritiesByUserQueryString =
            "SELECT u.username, ua.user_role " +
                    "FROM user u " +
                    "JOIN user_authority ua " +
                    "ON u.id=ua.user_id " +
                    "WHERE u.username = ?"

    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/health/**").permitAll()
                .antMatchers("/user/new/**").permitAll()
                .antMatchers(HttpMethod.GET, "/game/state/**").permitAll()
                .antMatchers("/**/admin/**").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(getUserQueryString)
                .authoritiesByUsernameQuery(getAuthoritiesByUserQueryString)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
