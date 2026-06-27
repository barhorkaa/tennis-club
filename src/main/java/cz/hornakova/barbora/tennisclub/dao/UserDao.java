package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.model.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getByUsername(String username);
    User save(User user);

}
