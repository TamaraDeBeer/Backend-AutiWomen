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

//                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{username}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/{username}").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/{username}/authorities").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/{username}/authorities").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/{username}/authorities/{authority}").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/forums/{id}").hasAuthority("ADMIN")


                        .requestMatchers(HttpMethod.GET, "/users/{username}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "{username}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/{username}").authenticated()

                        .requestMatchers(HttpMethod.POST,"/forums").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/forums/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/forums/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/forums/{id}/like").authenticated()
                        .requestMatchers(HttpMethod.GET,"/forums/{id}").authenticated()

                        .requestMatchers(HttpMethod.GET,"/forums").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}