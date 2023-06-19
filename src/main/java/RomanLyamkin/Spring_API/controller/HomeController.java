package RomanLyamkin.Spring_API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*Контроллер HomeController обрабатывает вызовы страниц системы.
* Начальная страница доступна всем, страница user доступна
* только пользователям с действительным jwt токеном.*/
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello, User!";
    }

}
