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
import com.company.movierating.dao.ReviewDao;
import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.dao.entity.Review;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReviewDaoImpl implements ReviewDao {
    private final static String GET_BY_ID = "SELECT r.id, r.film_id, r.user_id, r.content, r.publication_date " //
            + "FROM reviews r " //
            + "WHERE r.id = ? AND r.deleted = FALSE";
    private final static String GET_ALL = "SELECT r.id, r.film_id, r.user_id, r.content, r.publication_date " //
            + "FROM reviews r " //
            + "WHERE r.deleted = FALSE";
    private final static String GET_ALL_PARTIALLY = "SELECT r.id, r.film_id, r.user_id, r.content, r.publication_date "
            + "FROM reviews r " //
            + "WHERE r.deleted = FALSE " //
            + "ORDER BY r.id LIMIT ? OFFSET ?";
    private final static String GET_ALL_BY_FILM_ID_PARTIALLY = "SELECT r.id, r.film_id, r.user_id, r.content, r.publication_date "
            + "FROM reviews r " //
            + "WHERE r.film_id = ? AND r.deleted = FALSE " //
            + "ORDER BY r.id DESC LIMIT ? OFFSET ?";
    private final static String GET_ALL_BY_USER_ID_PARTIALLY = "SELECT r.id, r.film_id, r.user_id, r.content, r.publication_date "
            + "FROM reviews r " //
            + "WHERE r.user_id = ? AND r.deleted = FALSE " //
            + "ORDER BY r.id DESC LIMIT ? OFFSET ?";
    private final static String CREATE = "INSERT INTO reviews (film_id, user_id, content) " //
            + "VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE reviews SET content = ?, last_update = NOW() " //
            + "WHERE id = ? AND deleted = FALSE";
    private final static String DELETE = "UPDATE reviews SET deleted = TRUE, last_update = NOW() " //
            + "WHERE id = ? AND deleted = FALSE";
    private final static String COUNT = "SELECT COUNT(r.id) AS total " //
            + "FROM reviews r " //
            + "WHERE r.deleted = FALSE";
    private final static String COUNT_BY_FILM_ID = "SELECT COUNT(r.id) AS total " //
            + "FROM reviews r " //
            + "WHERE r.film_id = ? AND r.deleted = FALSE";
    private final static String COUNT_BY_USER_ID = "SELECT COUNT(r.id) AS total " //
            + "FROM reviews r " //
            + "WHERE r.user_id = ? AND r.deleted = FALSE";
    private final static String IS_EXISTED = "SELECT COUNT(r.id) AS existed " //
            + "FROM reviews r " //
            + "WHERE r.film_id = ? AND r.user_id = ? AND r.deleted = FALSE";

    private final DataSource dataSource;
    private final FilmDao filmDao;
    private final UserDao userDao;

    public ReviewDaoImpl(DataSource dataSource, FilmDao filmDao, UserDao userDao) {
        this.dataSource = dataSource;
        this.filmDao = filmDao;
        this.userDao = userDao;
    }

    @Override
    public Review getById(Long id) {
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
    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(GET_ALL);

            while (result.next()) {
                reviews.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return reviews;
    }

    @Override
    public List<Review> getAll(int limit, long offset) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PARTIALLY);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reviews.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return reviews;
    }

    @Override
    public List<Review> getAllByFilm(Long id, int limit, long offset) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_FILM_ID_PARTIALLY);
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setLong(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reviews.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return reviews;
    }

    @Override
    public List<Review> getAllByUser(Long id, int limit, long offset) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_USER_ID_PARTIALLY);
            statement.setLong(1, id);
            statement.setInt(2, limit);
            statement.setLong(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                reviews.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return reviews;
    }

    @Override
    public Review create(Review entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getFilm().getId());
            statement.setLong(2, entity.getUser().getId());
            statement.setString(3, entity.getContent());
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
    public Review update(Review entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, entity.getContent());
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
        throw new RuntimeException("Couldn't count reviews");
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
        throw new RuntimeException("Couldn't count reviews by film");
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
        throw new RuntimeException("Couldn't count reviews by user");
    }

    private Review process(ResultSet result) throws SQLException {
        Review review = new Review();
        review.setId(result.getLong("id"));
        review.setFilm(filmDao.getById(result.getLong("film_id")));
        review.setUser(userDao.getById(result.getLong("user_id")));
        review.setContent(result.getString("content"));
        review.setPublicationDate(
                result.getTimestamp("publication_date").toLocalDateTime().atZone(ZoneId.systemDefault()));

        return review;
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
        throw new RuntimeException("Couldn't check if review exists");
    }

}
