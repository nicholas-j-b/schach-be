package com.nicholasbrooking.pkg.schachbe.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
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
            "SELECT ua.username, ua.user_role " +
                    "FROM user_authority ua " +
                    "WHERE ua.username = ?"

    override fun configure(http: HttpSecurity) {
        http
                .formLogin().disable()
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/health/**").permitAll()
                .antMatchers("/user/new/**").permitAll()
                .antMatchers("/**/admin/**").hasRole("ADMIN")
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




//class SecurityConfig : WebSecurityConfigurerAdapter() {
////    @Autowired
////    private val authenticationEntryPoint: MyBasicAuthenticationEntryPoint? = null
//    @Autowired
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//    auth.jdbcAuthentication()
////        auth.inMemoryAuthentication()
////                .withUser("schach").password(passwordEncoder().encode("schach"))
////                .authorities("ROLE_USER")
//    }
//
//    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests()
////                .antMatchers("/securityNone").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
////                .authenticationEntryPoint(authenticationEntryPoint)
////        http.addFilterAfter(CustomFilter(),
////                BasicAuthenticationFilter::class.java)
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//}