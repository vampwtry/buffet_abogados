package com.project.abogados.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/CasoInformal","/SaveCasoInformal","/registro", "/login", "/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/send-email")
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .successHandler((request, response, authentication) -> {
                            // Redirigir según el rol del usuario autenticado
                            String redirectUrl = "/home"; // Valor por defecto si no coincide con ningún rol

                            boolean isAdmin = authentication.getAuthorities().stream()
                                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
                            boolean isUser = authentication.getAuthorities().stream()
                                    .anyMatch(role -> role.getAuthority().equals("ROLE_USER"));
                            boolean isAbogado = authentication.getAuthorities().stream()
                                    .anyMatch(role -> role.getAuthority().equals("ROLE_ABOGADO"));

                            if (isAdmin) {
                                redirectUrl = "/admin";
                            } else if (isUser) {
                                redirectUrl = "/user";
                            } else if (isAbogado) {
                                redirectUrl = "/abogado";
                            }

                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Ajusta la ruta según tus necesidades
                .allowedOrigins("http://localhost:8080") // Ajusta el dominio permitido
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
