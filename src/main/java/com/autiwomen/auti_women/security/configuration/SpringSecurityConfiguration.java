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
                        .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/topic/{topic}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/unique-topics").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/sorted-unique-topics").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/frequency").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/sorted-by-likes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/sorted-by-date").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{username}/forums").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}/comments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}/comments/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}/likes/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}/views/count").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{username}/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/search/${searchQuery}").permitAll()

                        .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}/password").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}/profile-picture").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}/profile-data").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}/profile-picture").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/users/profiles/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/users/profiles/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/profiles/{username}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/reviews/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/reviews/{username}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/forums/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/forums/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/forums/{id}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/users/{username}/liked-forums").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/users/{username}/viewed-forums").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/users/{username}/commented-forums").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/forums/{forumId}/comments/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/forums/users/{username}/comments").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/forums/{forumId}/comments/{commentId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/forums/{forumId}/comments/{commentId}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}/users/{username}/likes/check").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/forums/{forumId}/users/{username}/likes/add").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/forums/{forumId}/users/{username}/likes/remove").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}/users/{username}/views/check").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/forums/{forumId}/users/{username}/views/add").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/{username}/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/{username}/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/{username}/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/{username}/authorities/{authority}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authorities").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/users/profiles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/forums/comments").hasRole("ADMIN")

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}