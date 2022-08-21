package com.company.movierating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.company.movierating.dao.FilmDao;
import com.company.movierating.dao.ScoreDao;
import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.dao.entity.Score;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScoreDaoImpl implements ScoreDao {
    private final static String GET_BY_ID = "SELECT s.id, s.film_id, s.user_id, s.value, s.publication_date " //
            + "FROM scores s " //
            + "WHERE s.id = ? AND s.deleted = FALSE";
    private final static String GET_ALL = "SELECT s.id, s.film_is, s.user_id, s.value, s.publication_date " //
            + "FROM scores s " //
            + "WHERE s.deleted = FALSE";
    private final static String GET_ALL_PARTIALLY = "SELECT s.id, s.film_id, s.user_id, s.value, s.publication_date " //
            + "FROM scores s " //
            + "WHERE s.deleted = FALSE " //
            + "ORDER BY s.id LIMIT ? OFFSET ?";
    private final static String CREATE = "INSERT INTO scores (id, film_id, user_id, value) " //
            + "VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE scores SET value = ?, last_update = NOW() " //
            + "WHERE id = ? AND deleted = FALSE";
    private final static String DELETE = "UPDATE scores SET deleted = TRUE, last_update = NOW() " //
            + "WHERE id = ? AND deleted = FALSE";
    private final static String COUNT = "SELECT COUNT(s.id) AS total " //
            + "FROM scores s" //
            + "WHERE s.deleted = FALSE";

    private final DataSource dataSource;
    private final FilmDao filmDao;
    private final UserDao userDao;

    public ScoreDaoImpl(DataSource dataSource, FilmDao filmDao, UserDao userDao) {
        this.dataSource = dataSource;
        this.filmDao = filmDao;
        this.userDao = userDao;
    }

    @Override
    public Score getById(Long id) {
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
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(GET_ALL);

            while (result.next()) {
                scores.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return scores;
    }

    @Override
    public List<Score> getAll(int limit, long offset) {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PARTIALLY);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                scores.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return scores;
    }

    @Override
    public Score create(Score entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getFilm().getId());
            statement.setLong(3, entity.getUser().getId());
            statement.setInt(4, entity.getValue());
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
    public Score update(Score entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, entity.getValue());
            statement.setLong(2, entity.getId());
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

    @Override
    public Long count() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(COUNT);

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Couldn't count scores");
    }

    private Score process(ResultSet result) throws SQLException {
        Score score = new Score();
        score.setId(result.getLong("id"));
        score.setFilm(filmDao.getById(result.getLong("film_id")));
        score.setUser(userDao.getById(result.getLong("user_id")));
        score.setValue(result.getInt("value"));
        score.setPublicationDate(
                result.getTimestamp("publication_date").toLocalDateTime().atZone(ZoneId.systemDefault()));

        return score;
    }
}
