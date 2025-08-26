package com.apishoppage.api.config;

import com.apishoppage.api.config.userconfig.UserManagerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserManagerConfig userInfoManagerConfig;

    public SecurityConfig(UserManagerConfig userInfoManagerConfig){
        this.userInfoManagerConfig = userInfoManagerConfig;
    }


    @Order(1)
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .userDetailsService(userInfoManagerConfig)
                //.formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }


    //This is just an example about how you can config the console to show H2 commands
    @Order(2)
    @Bean
    public SecurityFilterChain dataBaseConsoleSecurityFilterChainConfig(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .securityMatcher(("/h2-console/**"))
                .authorizeHttpRequests(auth->auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                // to display the h2Console in Iframe
                .headers(headers -> headers.frameOptions(withDefaults()).disable())
                .build();
    }


    /*Encript the password
    * is needed in order to make "private final PasswordEncoder passwordEncoder;"
    * line work in InitialUserInfo class
    * */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
