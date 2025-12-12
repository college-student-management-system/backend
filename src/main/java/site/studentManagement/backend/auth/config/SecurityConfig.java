package site.studentManagement.backend.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()

                        // Super Admin Only
                        .requestMatchers("/superadmin/**").hasRole("SUPER_ADMIN")

                        // HOD + Super Admin
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")

                        // Faculty + HOD + Super Admin
                        .requestMatchers("/faculty/**").hasAnyRole("FACULTY", "ADMIN", "SUPER_ADMIN")

                        // Student + faculty + admin + superadmin
                        .requestMatchers("/student/**").hasAnyRole("STUDENT", "FACULTY", "ADMIN", "SUPER_ADMIN")

                        // everything else needs authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
