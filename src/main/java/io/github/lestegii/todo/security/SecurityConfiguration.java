package io.github.lestegii.todo.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import io.github.lestegii.todo.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        // Allow images to be viewed by anyone
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")
                ).permitAll()
        );
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$FxqQj4d32dXDru1KBikilOBFbmaZCx4OCfANiErEspj.ieefOI3XK") // password
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$12$uBSrdsnqXuwZ7O6RSOnZvO3fC9qdWu7cmigcx79KKX2qyUi87xORy") // adminpassword
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
