package ru.job4j.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.UserDbStore;

import java.util.Collection;

@Service @ThreadSafe
public class UserService {
    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public User add(User user) {
        return store.add(user);
    }

    public Collection<User> findAll() {
        return store.findAll();
    }

    public User findUserByEmail(String email) {
        return store.findUserByEmail(email);
    }
}
