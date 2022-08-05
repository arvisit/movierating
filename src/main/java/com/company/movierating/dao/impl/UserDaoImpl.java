package com.company.movierating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.dao.entity.User;
import com.company.movierating.dao.entity.User.Role;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserDaoImpl implements UserDao {
    private static final String GET_BY_ID = "SELECT u.id, u.email, u.login, u.password, u.registration, u.info, u.reputation, r.name AS role " //
            + "FROM users u JOIN roles r ON u.role_id = r.id " //
            + "WHERE u.id = ? AND u.deleted = FALSE";
    private static final String GET_BY_EMAIL = "SELECT u.id, u.email, u.login, u.password, u.registration, u.info, u.reputation, r.name AS role " //
            + "FROM users u JOIN roles r ON u.role_id = r.id " //
            + "WHERE u.email = ? AND u.deleted = FALSE";
    private static final String GET_BY_LOGIN = "SELECT u.id, u.email, u.login, u.password, u.registration, u.info, u.reputation, r.name AS role " //
            + "FROM users u JOIN roles r ON u.role_id = r.id " //
            + "WHERE u.login = ? AND u.deleted = FALSE";
    private static final String GET_ALL = "SELECT u.id, u.email, u.login, u.password, u.registration, u.info, u.reputation, r.name AS role " //
            + "FROM users u JOIN roles r ON u.role_id = r.id " //
            + "WHERE u.deleted = FALSE";
    private static final String CREATE = "INSERT INTO users (email, login, password, role_id) " //
            + "VALUES (?, ?, ?, (SELECT id FROM roles WHERE name = ?))";
    private static final String UPDATE = "UPDATE users SET email = ?, login = ?, password = ?, " //
            + "role_id = (SELECT id FROM roles WHERE name = ?), info = ?, reputation = ?, last_update = NOW()" //
            + "WHERE id = ? AND deleted = FALSE";
    private static final String DELETE = "UPDATE users SET deleted = TRUE WHERE id = ?, last_update = NOW()";

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return process(result);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return process(result);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User getByLogin(String login) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_LOGIN);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return process(result);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(GET_ALL);

            while (result.next()) {
                users.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public User create(User entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getLogin());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getRole().toString());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong("id");
                return getById(id);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User update(User entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getLogin());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getRole().toString());
            statement.setString(5, entity.getInfo());
            statement.setInt(6, entity.getReputation());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();

            return getById(entity.getId());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted == 1;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    private User process(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setLogin(result.getString("login"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setRole(Role.valueOf(result.getString("role")));
        user.setInfo(result.getString("info"));
        user.setReputation(result.getInt("reputation"));
        user.setRegistration(ZonedDateTime.parse(result.getString("registration"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSx")));

        return user;
    }

}
