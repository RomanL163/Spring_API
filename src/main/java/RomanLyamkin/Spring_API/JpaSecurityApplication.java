package RomanLyamkin.Spring_API;

import RomanLyamkin.Spring_API.repository.PostRepository;
import RomanLyamkin.Spring_API.repository.UserRepository;
import RomanLyamkin.Spring_API.model.Post;
import RomanLyamkin.Spring_API.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/*Класс JpaSecurityApplication используется для запуска приложения
* Spring Boot. Также, класс используется для инициализации базы
* данных h2database, содержащей в себе репозитории аккаунтов
* пользователей и их постов. С дальнейшим переходом на иную базу
* данных инициализация производиться не будет.*/
@SpringBootApplication
public class JpaSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PostRepository posts, UserRepository users, PasswordEncoder encoder) {
		return args -> {
			users.save(new User("user",encoder.encode("password"),"READ,ROLE_USER"));
			users.save(new User("user2",encoder.encode("password"),"READ,ROLE_USER"));
			posts.save(new Post("Hello, World!","Welcome to my first post!","user2"));
		};
	}
}
