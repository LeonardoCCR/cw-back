package com.example.concesswebapi.config;

import com.example.concesswebapi.security.JwtAuthFilter;
import com.example.concesswebapi.security.JwtService;
import com.example.concesswebapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/acessorios/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/admsempresa/**")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/admssuporte/**")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/testdrives/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/clientes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/concessionarias/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/empresas/**")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/fabricantes/**" )
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/gestores/**")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/modelos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/veiculos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/vendas/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/vendedores/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/usuarios")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}