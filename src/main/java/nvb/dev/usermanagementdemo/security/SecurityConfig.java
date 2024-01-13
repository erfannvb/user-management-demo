package nvb.dev.usermanagementdemo.security;

import lombok.AllArgsConstructor;
import nvb.dev.usermanagementdemo.security.filter.AuthenticationFilter;
import nvb.dev.usermanagementdemo.security.filter.ExceptionHandlerFilter;
import nvb.dev.usermanagementdemo.security.manager.AuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static nvb.dev.usermanagementdemo.constant.SecurityConstant.REGISTER_PATH;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AuthManager authManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        httpSecurity
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers(HttpMethod.POST, REGISTER_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}
