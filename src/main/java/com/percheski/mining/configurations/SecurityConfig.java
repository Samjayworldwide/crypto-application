package com.percheski.mining.configurations;

import com.percheski.mining.security.JWTAuthenticationFilter;
import com.percheski.mining.security.JwtAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(configure ->
                configure
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(antMatcher("/javainuse-openapi/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user/signup")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/admin/login")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/admin/signup")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/reset_password/**")).permitAll()


                        //User
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/deposit/upload")).hasAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/user/fetchUser")).hasAuthority("USER")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/user/logout")).hasAnyAuthority("USER", "ADMIN")


                        //Admin
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/admin/fetchUser/{email}")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/admin/search_id/{id}")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/admin/approvePayment")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v1/admin/viewProof/{filename}")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/admin/changeWallet")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/admin/change_amount")).hasAuthority("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/wallet/**")).hasAnyAuthority("USER","ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/registration/**")).permitAll()

//                        .anyRequest().permitAll());

                        .anyRequest().authenticated());
        http.httpBasic(HttpBasicConfigurer::disable);
        http.csrf(CsrfConfigurer::disable);
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:2006", "localhost:2006"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

