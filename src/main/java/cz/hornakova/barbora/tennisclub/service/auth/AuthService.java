package cz.hornakova.barbora.tennisclub.service.auth;

import cz.hornakova.barbora.tennisclub.dao.UserDao;
import cz.hornakova.barbora.tennisclub.model.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDao userDao;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDao = userDao;
    }

    public String login(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userDao.getByUsername(username).orElseThrow();

        return jwtService.generateToken(user);
    }
}
