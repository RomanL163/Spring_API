package RomanLyamkin.Spring_API.controller;


import RomanLyamkin.Spring_API.SecurityTokenService.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*Контроллер AuthController обрабатывает вызов сервиса генерации токена.*/
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /*Метод token использует метод Authentication для аутентификации
    * пользователя по его имени и паролю.*/
    @PostMapping("/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }

}
