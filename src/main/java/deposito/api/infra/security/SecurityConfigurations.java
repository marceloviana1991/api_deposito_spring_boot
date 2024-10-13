package deposito.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
public class SecurityConfigurations {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/{id}").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/usuarios").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "/produtos", "/produtos/{id}").hasRole("USER");
                    req.requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/produtos").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/produtos/{id}").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "/pedidos").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "/pedidos/{id}").hasRole("USER");
                    req.requestMatchers(HttpMethod.POST, "/pedidos").hasRole("USER");
                    req.requestMatchers(HttpMethod.PUT, "/pedidos").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/pedidos/{id}").hasRole("ADMIN");

                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
