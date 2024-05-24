package com.example.shabbyshackinn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login/**" ).permitAll()
                        .anyRequest().authenticated()
                )
//                .oauth2Login(oauth2->{
//                    oauth2.userInfoEndpoint(ep->{
//                        ep.userAuthoritiesMapper( this.userAuthoritiesMapper() );
//                    });
//                })
                .formLogin((form) -> form
//                        .loginPage("/login")
                                .permitAll()
                )
                .logout((logout) -> {
                    logout.permitAll();
                    logout.logoutSuccessUrl("/login");
                })
                .csrf(AbstractHttpConfigurer::disable); //Kankse ta bort den här??

        return http.build();
    }

//    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//
//        return (authorities) -> {
//            List<SimpleGrantedAuthority> mappedAuthorities = new ArrayList<>();
//
//
//            authorities.forEach(authority -> {
//
//                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {
//
//                    Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//
//
//                    //String email = userAttributes.get("email").toString();
//                    // email is not returned from Github!!! If not public email setting is turned on in your account
//
//                    // så - vi kan gå på login
//                    String login = userAttributes.get("login").toString();
//
//                    // Map the attributes found in userAttributes
//                    // to one or more GrantedAuthority's and add it to mappedAuthorities
//                    if(login.equals("aspcodenet")){
//                        mappedAuthorities.add(new SimpleGrantedAuthority("Admin"));
//                    }
//                }
//
//            });
//            
//            return mappedAuthorities;
//        };
//    }
    
}
