package it.uniroma3.siw.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.siw.model.Credentials.DEFAULT_ROLE;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    DataSource datasource;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> {
                            try {
                                requests
                                        .requestMatchers(HttpMethod.GET, "/", "/index", "/login", "/register","/artist", "/artist/{id}","/movie/{id}","/movie","/tutteLeRecensioni","/aggiungiRecensione", "/css/**","/images/**", "/videos/**", "favicon.ico").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/login", "/register","/searchMovie","/searchArtist","/display/image/{id}").permitAll()
                                        // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
                                        .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
                                        .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
                                        // solo gli utenti autenticati con ruolo DEFAULT possono accedere a risorse con path /user/**
                                        .requestMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority(ADMIN_ROLE,DEFAULT_ROLE)
                                        .requestMatchers(HttpMethod.POST,"/user/**").hasAnyAuthority(ADMIN_ROLE,DEFAULT_ROLE)

                                        .anyRequest().authenticated()
                                        .and().exceptionHandling().accessDeniedPage("/error");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(datasource)
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?");
    }
}