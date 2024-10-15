package com.autiwomen.auti_women.security.configuration;

import com.autiwomen.auti_women.security.filter.JwtRequestFilter;
import com.autiwomen.auti_women.security.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    public final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll()
//                                .requestMatchers(HttpMethod.POST, "/register").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/users/{username}/image").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/{id}").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/topic/{topic}").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/unique-topics").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/sorted-unique-topics").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/frequency").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/sorted-by-likes").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/sorted-by-date").permitAll()
//                                .requestMatchers(HttpMethod.GET, "users/{username}/forums").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/forums/{forumId}/comments").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/{forumId}/comments/count").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/{forumId}/likes/count").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/forums/{forumId}/views/count").permitAll()
//
//                                .requestMatchers(HttpMethod.GET, "/users/{username}").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/users/{username}/password").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/users/{username}/profile-picture").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/users/{username}/profile-data").authenticated()
//                                .requestMatchers(HttpMethod.DELETE, "/users/{username}/profile-picture").authenticated()
//
//                                .requestMatchers(HttpMethod.POST, "/users/profiles/{username}").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/users/profiles/{username}").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/users/profiles/{username}").authenticated()
//
//                                .requestMatchers(HttpMethod.POST, "/reviews/{username}").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/reviews/{username}").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/reviews/{username}").authenticated()
//
//                                .requestMatchers(HttpMethod.POST, "/forums/{username}").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/forums/{id}").authenticated()
//                                .requestMatchers(HttpMethod.DELETE, "/forums/{id}").authenticated()
//
//                                .requestMatchers(HttpMethod.GET, "/users/{username}/liked-forums").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/users/{username}/viewed-forums").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/users/{username}/commented-forums").authenticated()
//
//                                .requestMatchers(HttpMethod.POST, "/forums/{forumId}/comments/{username}").authenticated()
//                                .requestMatchers(HttpMethod.GET, "/forums/users/{username}/comments").authenticated()
//                                .requestMatchers(HttpMethod.PUT, "/forums/{forumId}/comments/{commentId}").authenticated()
//                                .requestMatchers(HttpMethod.DELETE, "/forums/{forumId}/comments/{commentId}").authenticated()
//
//                                .requestMatchers(HttpMethod.GET, "/forums/{forumId}/users/{username}/likes/check").authenticated()
//                                .requestMatchers(HttpMethod.POST, "/forums/{forumId}/users/{username}/likes/add").authenticated()
//                                .requestMatchers(HttpMethod.DELETE, "/forums/{forumId}/users/{username}/likes/remove").authenticated()
//
//                                .requestMatchers(HttpMethod.GET, "/forums/{forumId}/users/{username}/views/check").authenticated()
//                                .requestMatchers(HttpMethod.POST, "/forums/{forumId}/users/{username}/views/add").authenticated()
//
//                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/{username}").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/{username}/authorities").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/{username}/authorities").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/{username}/authorities").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/{username}/authorities/{authority}").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/authorities").hasRole("ADMIN")
//
//                                .requestMatchers(HttpMethod.GET, "/users/profiles").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/reviews/{id}").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/forums/{id}").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.GET, "/forums/comments").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/forums/{forumId}/comments/{commentId}").hasRole("ADMIN")
//
//                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}