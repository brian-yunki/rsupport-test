package rsupport.test.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import rsupport.test.config.security.enums.RoleType;
import rsupport.test.config.security.filter.TokenAuthenticationFilter;
import rsupport.test.config.security.handler.HandleAccessDenied;
import rsupport.test.config.security.handler.HandleAuthenticationEntryPoint;
import rsupport.test.config.security.handler.HandleLogout;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final HandleAuthenticationEntryPoint handleAuthenticationEntryPoint;
    private final HandleAccessDenied handleAccessDenied;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final HandleLogout handleLogout;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests( authorize -> {
                    authorize
                            .requestMatchers(HttpMethod.GET).permitAll()
                            .requestMatchers("/login").permitAll();

                    authorize
                            .requestMatchers(HttpMethod.POST).hasRole(RoleType.ADMIN.name())
                            .requestMatchers(HttpMethod.PUT).hasRole(RoleType.ADMIN.name())
                            .requestMatchers(HttpMethod.DELETE).hasRole(RoleType.ADMIN.name())
                            .anyRequest().authenticated();
                })
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(handleAuthenticationEntryPoint)
                                .accessDeniedHandler(handleAccessDenied)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(handleLogout)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .getOrBuild();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(Duration.ofSeconds(10));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider)
                .getOrBuild();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(SecurityUserDetailService securityUserDetailService, PasswordEncoder passwordEncoder) {
        SecurityAuthenticationProvider provider = new SecurityAuthenticationProvider();
        provider.setUserDetailsService(securityUserDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
