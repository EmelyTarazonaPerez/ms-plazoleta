package plazoleta.config.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import plazoleta.adapters.driving.http.utils.JwtService.JwtTokenValidationFilter;
import plazoleta.adapters.driving.http.utils.JwtService.JwtTokenValidator;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenValidator tokenValidator;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers(HttpMethod.POST, "/plate/auth/**").hasAuthority("propietario")
                        .requestMatchers(HttpMethod.POST, "/restaurant/create").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/restaurant/getAll").hasAuthority("cliente")
                        .requestMatchers(HttpMethod.GET,"/plate/getAll/{id}").hasAuthority("cliente")
                        .requestMatchers(HttpMethod.POST,"/order/create").hasAuthority("cliente")
                        .requestMatchers(HttpMethod.GET, "/order/get-all").hasAuthority("trabajador")
                        .requestMatchers(HttpMethod.PUT, "/order/update/{id}").hasAuthority("trabajador")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenValidationFilter(tokenValidator), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

