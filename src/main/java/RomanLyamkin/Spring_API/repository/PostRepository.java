package RomanLyamkin.Spring_API.repository;

import RomanLyamkin.Spring_API.model.Post;
import org.springframework.data.repository.CrudRepository;

/*Интерфейс репозитория постов PostRepository наследует
интерфейс CrudRepository.*/
public interface PostRepository extends CrudRepository<Post,Long> {

}
