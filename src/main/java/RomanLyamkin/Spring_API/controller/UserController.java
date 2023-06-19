package RomanLyamkin.Spring_API.controller;

import RomanLyamkin.Spring_API.model.User;
import RomanLyamkin.Spring_API.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*Контроллер PostController обрабатывает вызов метода регистрации
 * нового аккаунта пользователя и его запись в репозиторий аккаунтов.
 * Доступ к регистрации открыт всем*/
@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    /*Метод create конвертирует тело запроса в обьект класса User
     * и записывает его в репозиторий */
    @PostMapping("/registration")
    public void create(@RequestBody User newUser) {
        repository.save(newUser);
    }
}