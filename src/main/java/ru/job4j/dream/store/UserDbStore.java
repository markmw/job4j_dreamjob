package ru.job4j.dream.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository @ThreadSafe
public class UserDbStore {
    private final BasicDataSource pool;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDbStore.class.getName());

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<User> findAll() {
        Collection<User> collection = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement(
                     "select * from users")) {
            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    collection.add(new User(
                            resultSet.getInt("id"),
                            resultSet.getString("email"),
                            resultSet.getString("password")));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
        }
        return collection;
    }

    public Optional<User> add(User user) {
        Optional<User> rsl = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement pr = cn.prepareStatement(
                     "insert into users(email, password) VALUES (?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, user.getEmail());
            pr.setString(2, user.getPassword());
            pr.execute();
            try (ResultSet resultSet = pr.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
            rsl = Optional.of(user);
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
        }
        return rsl;
    }

    public Optional<User> findUserByEmail(String email) {
        try (Connection cn = pool.getConnection()) {
            try (PreparedStatement pr = cn.prepareStatement(
                    "select * from users where email = ?")) {
                pr.setString(1, email);
                try (ResultSet result = pr.executeQuery()) {
                    if (result.next()) {
                        return Optional.of(new User(
                                result.getInt("id"),
                                result.getString("email"),
                                result.getString("password")));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error: ", e);
        }
        return Optional.empty();
    }
}
