package com.decor_shop_web.shopWeb.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

//    @Autowired
//    private UserDetailsService userDetailsService;

    private final SecurityFilter securityFilter;

    // new securityFilterChain method, now configurated for using jwt
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // requests and them permissions
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers("/decor/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/decor/save").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")  // defines logout url
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().flush();
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")  // deletes session cookies
                )
        .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(registry->{
//           registry.requestMatchers("/home", "/auth/register/**").permitAll();
//           registry.requestMatchers("/decor/save").hasRole("ADMIN");
//           registry.requestMatchers("/decor/list").hasRole("ADMIN");
//           registry.requestMatchers("/admin/**").hasRole("ADMIN");
//           registry.requestMatchers("/user/**").hasRole("USER");
//           registry.anyRequest().authenticated();
//        })
//                // para a formulario de login aparecer é necessário permitir o acesso a todos
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//        .build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails normalUser = User.builder()
//                .username("ned")
//                .password("$2a$12$tfP.l3Z/PfynwOYeVCcY7O36ObEDRpTRXjMQCW9yYFPc9ZaQkEvRW")
//                .roles("USER")
//        .build();
//
//        UserDetails adminUser = User.builder()
//                .username("cloroquina")
//                .password("$2a$12$tfP.l3Z/PfynwOYeVCcY7O36ObEDRpTRXjMQCW9yYFPc9ZaQkEvRW")
//                .roles("ADMIN","USER")
//        .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return userDetailsService;
//    }
//
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setUserDetailsService(userDetailsService);
////        provider.setPasswordEncoder(passwordEncoder());
////        return provider;
////    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
