package ma.devops.gestionecole.security;

import lombok.AllArgsConstructor;
import ma.devops.gestionecole.security.services.A.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsServiceImpl;
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("123")).roles("USER").build(),
                //User.withUsername("user1").password("{noop}123").roles("USER").build()
                User.withUsername("admin").password(passwordEncoder.encode("123")).roles("USER","ADMIN").build()
        );
    }


    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {



        httpSecurity.rememberMe();
        httpSecurity.authorizeHttpRequests().requestMatchers("/img/**","/css/**", "/user/**","/js/**","/vendor/**").permitAll();
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/notAutorized");
        httpSecurity.userDetailsService(userDetailsServiceImpl);
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();

        return httpSecurity.build();
    }
}
