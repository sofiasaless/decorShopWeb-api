package com.decor_shop_web.shopWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    // o metodo abaixo define as permições para os end-points da api
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry->{
           registry.requestMatchers("/home", "/auth/register/**").permitAll();

           registry.requestMatchers("/admin/**").hasRole("ADMIN");
           registry.requestMatchers("/user/**").hasRole("USER");
           registry.anyRequest().authenticated();
        })
                // para a formulario de login aparecer é necessário permitir o acesso a todos
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
        .build();
    }

    // definindo usuarios USER e ADMIN em memoria para teste na segurança
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

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
