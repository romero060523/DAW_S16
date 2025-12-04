package com.tecsup.evaluacion03.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                    .csrf(csrf -> csrf.disable()) // Para desarrollo, habilita en produccion
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                        // Rutas para ADMIN
                        .requestMatchers("/usuario/**", "/rol/**", "/bitacora/**").hasRole("ADMIN")

                        // Rutas específicas de COCINA (antes de /pedido/**)
                        .requestMatchers("/pedido/cocina", "/pedido/*/marcar-servido").hasAnyRole("COCINERO", "ADMIN")

                        // Rutas para MOZO y ADMIN
                        .requestMatchers("/pedido/**").hasAnyRole("MOZO", "ADMIN")

                        // Rutas de información compartida (todos los roles operativos pueden ver)
                        .requestMatchers("/mesa/**", "/cliente/**").hasAnyRole("MOZO", "COCINERO", "ADMIN")

                        // Rutas para ver platos (MOZO, COCINERO y ADMIN - todos necesitan ver el menú)
                        .requestMatchers("/plato/**").hasAnyRole("MOZO", "COCINERO", "ADMIN")

                        // Todas las demás rutas requieren autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}