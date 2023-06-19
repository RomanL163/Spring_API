package RomanLyamkin.Spring_API.controller;

import RomanLyamkin.Spring_API.model.Post;
import RomanLyamkin.Spring_API.repository.PostRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*Контроллер PostController обрабатывает вызов метода создания
* нового поста пользователя и его запись в репозиторий постов.
* Доступ к созданию поста имеют только пользователи
* с действительным jwt токеном.*/
@RestController
public class PostController {

    private final PostRepository posts;
    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    /*Метод create конвертирует тело запроса в обьект класса Post
    * и записывает его в репозиторий. */
    @PostMapping("/newpost")
    public void create(@RequestBody Post newPost) {
        posts.save(newPost);
    }

}
