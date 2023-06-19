package RomanLyamkin.Spring_API.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import RomanLyamkin.Spring_API.UserDetailsService.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.spec.SecretKeySpec;

import static org.springframework.security.config.Customizer.withDefaults;

/*Класс SecurityConfig содержит в себе настройки системы безопасности.*/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /*Ключ jwtKey это симметричный секретный ключ, который используется для шифрования
    * и дешифрования jwt токена. Ключ jwtKey хранится в файле application.properties*/
    @Value("${jwt.key}")
    private String jwtKey;

    /*Класс myUserDetailsService это экземпляр класса JpaUserDetailsService,
     который имплементирует интерфейс UserDetailsService
     Экземпляр содержит в себе метод loadUserByUsername, который ищет
     пользователя в репозитории по имени пользователя.*/
    private final JpaUserDetailsService myUserDetailsService;


    public SecurityConfig(JpaUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    /*Бин securityFilterChain устанавливает уровень допуска для страниц.
    * Доступ к базе данных h2database открыт для использования базы данных
    * и её отладки. В дальнейшем будет осуществлен переход на иную базу данных
    * и доступ будет закрыт.
    * Доступ к начальной странице и контроллеру регистрации открыт для всех.
    * Доступ к контроллеру выдачи jwt токена открыт только для авторизованных
    * пользователей.
    * Доступ к остальным страницам (в том числе к контроллеру создания новых
    * постов) открыт только пользователям с действительным jwt токеном.*/
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("registration").permitAll()
                        .requestMatchers("/api/auth/token").hasRole("USER")
                        .anyRequest().hasAuthority("SCOPE_READ")
                )
                .userDetailsService(myUserDetailsService)
                .headers(headers -> headers.frameOptions().disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(withDefaults())
                .build();
    }

    /*Бин passwordEncoder устанавливает BCryptPasswordEncoder в качестве
    * кодировщика паролей.*/
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*Бин jwtEncoder устанавливает NimbusJwtEncoder в качестве кодировщика
    * jwt токена и в jwtKey качестве секретного ключа. */
    @Bean
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
    }

    /*Бин jwtDecoder устанавливает NimbusJwtDecoder в качестве декодировщика
     * jwt токена и в jwtKey качестве секретного ключа.
     * Алгоритм HS512 используется для расшифровки токена.*/
    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] bytes = jwtKey.getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length,"RSA");
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS512).build();
    }
}

