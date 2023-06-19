package RomanLyamkin.Spring_API.repository;

import RomanLyamkin.Spring_API.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/*Интерфейс репозитория аккаунтов UserRepository наследует
интерфейс CrudRepository и обозначивает метод используемый
для поиска аккаунта по имени.*/
public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
