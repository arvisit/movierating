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
    private final static String GET_ALL_BY_FILM_ID_PARTIALLY = "SELECT s.id, s.film_id, s.user_id, s.value, s.publication_date "
            + "FROM scores s " //
            + "WHERE s.film_id = ? AND s.deleted = FALSE " //
            + "ORDER BY s.id DESC LIMIT ? OFFSET ?";
    private final static String GET_ALL_BY_USER_ID_PARTIALLY = "SELECT s.id, s.film_id, s.user_id, s.value, s.publication_date "
            + "FROM scores s " //
            + "WHERE s.user_id = ? AND s.deleted = FALSE " //
            + "ORDER BY s.id DESC LIMIT ? OFFSET ?";
    private final static String CREATE = "INSERT INTO scores (film_id, user_id, value) " //
            + "VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE scores SET value = ?, last_update = NOW() " //
            + "WHERE id = ? AND deleted = FALSE";
    private final static String DELETE = "UPDATE scores SET deleted = TRUE, last_update = NOW() " //
            + "WHERE id = ? AND deleted = FALSE";
    private final static String COUNT = "SELECT COUNT(s.id) AS total " //
            + "FROM scores s " //
            + "WHERE s.deleted = FALSE";
    private final static String COUNT_BY_FILM_ID = "SELECT COUNT(s.id) AS total " //
            + "FROM scores s " //
            + "WHERE s.film_id = ? AND s.deleted = FALSE";
    private final static String COUNT_BY_USER_ID = "SELECT COUNT(s.id) AS total " //
            + "FROM scores s " //
            + "WHERE s.user_id = ? AND s.deleted = FALSE";
    private final static String COUNT_FILM_AVERAGE_SCORE = "SELECT AVG(s.value) AS average " //
            + "FROM scores s " //
            + "WHERE s.film_id = ? AND s.deleted = FALSE";
    private final static String IS_EXISTED = "SELECT COUNT(s.id) AS existed " //
            + "FROM scores s " //
            + "WHERE s.film_id = ? AND s.user_id = ? AND s.deleted = FALSE";

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
            return getById(id, connection);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    
    @Override
    public Score getById(Long id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
        statement.setLong(1, id);
        ResultSet result = statement.executeQuery();
        
        if (result.next()) {
            return process(result, connection);
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
                scores.add(process(result, connection));
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
                scores.add(process(result, connection));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return scores;
    }

    @Override
    public List<Score> getAllByFilm(Long id, int limit, long offset) {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_FILM_ID_PARTIALLY);
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setLong(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                scores.add(process(result, connection));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return scores;
    }

    @Override
    public List<Score> getAllByUser(Long id, int limit, long offset) {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_USER_ID_PARTIALLY);
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setLong(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                scores.add(process(result, connection));
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
            statement.setLong(1, entity.getFilm().getId());
            statement.setLong(2, entity.getUser().getId());
            statement.setInt(3, entity.getValue());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong("id");
                return getById(id, connection);
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

            return getById(entity.getId(), connection);
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

    @Override
    public Long countByFilm(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(COUNT_BY_FILM_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Couldn't count scores by film");
    }

    @Override
    public Long countByUser(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(COUNT_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Couldn't count scores by user");
    }

    @Override
    public Double countFilmAverageScore(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(COUNT_FILM_AVERAGE_SCORE);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getDouble("average");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Couldn't count film average score");
    }

    private Score process(ResultSet result, Connection connection) throws SQLException {
        Score score = new Score();
        score.setId(result.getLong("id"));
        score.setFilm(filmDao.getById(result.getLong("film_id"), connection));
        score.setUser(userDao.getById(result.getLong("user_id"), connection));
        score.setValue(result.getInt("value"));
        score.setPublicationDate(
                result.getTimestamp("publication_date").toLocalDateTime().atZone(ZoneId.systemDefault()));

        return score;
    }

    @Override
    public boolean isExisted(Long filmId, Long userId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(IS_EXISTED);
            statement.setLong(1, filmId);
            statement.setLong(2, userId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getLong("existed") == 1;
            }
            return false;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Couldn't check if score exists");
    }

}
