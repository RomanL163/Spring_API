package RomanLyamkin.Spring_API.UserDetailsService;

import RomanLyamkin.Spring_API.model.SecurityUserWrapper;
import RomanLyamkin.Spring_API.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*Класс JpaUserDetailsService наследует интерфейс UserDetailsService
* и содержит метод loadUserByUsername, используемый для поиска
* аккаунта по имени. В случае отсутсвия аккаунта с заданным именем
* бросается ошибка UsernameNotFoundException*/
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(SecurityUserWrapper::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}
