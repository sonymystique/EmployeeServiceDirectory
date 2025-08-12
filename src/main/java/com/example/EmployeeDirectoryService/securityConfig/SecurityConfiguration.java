package com.example.EmployeeDirectoryService.securityConfig;
import com.example.EmployeeDirectoryService.exceptions.CustomAccessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



import javax.sql.DataSource;

import static com.example.EmployeeDirectoryService.constants.ADMIN;
import static com.example.EmployeeDirectoryService.constants.url;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private DataSource dataSource;
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.POST, url).hasRole(ADMIN)
                .requestMatchers(HttpMethod.PUT,url).hasRole(ADMIN)
                .requestMatchers(HttpMethod.DELETE, url).hasRole(ADMIN)
                .anyRequest().authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(withDefaults());
        http.exceptionHandling(exceptionHandling->
                exceptionHandling.accessDeniedHandler(customAccessDeniedHandler));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean

    public UserDetailsService userDetailsService() {

//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("password1"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("password1"))
//                .roles("ADMIN")
//                .build();
//        JdbcUserDetailsManager userDetailsManager =
//                new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.createUser(user1);
//        userDetailsManager.createUser(admin);
//
//        return new InMemoryUserDetailsManager(user1,admin);

       return new JdbcUserDetailsManager(dataSource);
    }
}