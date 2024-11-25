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
                        .requestMatchers(HttpMethod.GET, "/forums/{forumId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/topics/{topic}/forums").permitAll()
                        .requestMatchers(HttpMethod.GET, "/topics/unique/forums").permitAll()
                        .requestMatchers(HttpMethod.GET, "/topics/sorted/forums").permitAll()
                        .requestMatchers(HttpMethod.GET, "/topics/frequency").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/sorted-by-likes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/sorted-by-date").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/users/{username}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments/forums/{forumId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments/count/forums/{forumId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/likes/count/forums/{forumId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/views/count/forums/{forumId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{username}/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/forums/search").permitAll()

                        .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}/password").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}/profile-picture").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/users/{username}/profile-data").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}/profile-picture").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/profiles/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/profiles/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/profiles/users/{username}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/reviews/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/reviews/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/reviews/users/{username}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/forums/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/forums/{forumId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/forums/{forumId}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/forums/users/{username}/liked-forums").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/forums/users/{username}/viewed-forums").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/forums/users/{username}/commented-forums").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/comments/forums/{forumId}/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/comments/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/comments//{commentId}/forums/{forumId}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/comments/{commentId}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/likes/check/forums/{forumId}/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/likes/add/forums/{forumId}/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/likes//delete/forums/{forumId}/users/{username}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/views/check/forums/{forumId}/users/{username}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/views/add/forums/{forumId}/users/{username}").hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authorities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/authorities/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/authorities/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/authorities/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authorities/{authority}/users/{username}").hasRole("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/profiles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/users/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/comments").hasRole("ADMIN")

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}