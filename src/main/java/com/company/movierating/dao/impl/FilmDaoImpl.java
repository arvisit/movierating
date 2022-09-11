package com.company.movierating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.movierating.dao.FilmDao;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.dao.entity.Film;
import com.company.movierating.dao.entity.Film.AgeRating;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FilmDaoImpl implements FilmDao {
    private static final String GET_BY_ID = """
            SELECT f.id, f.title, f.description, f.release_year, f.length, f.poster, a.name AS age_rating
            FROM films f JOIN age_ratings a ON f.age_rating_id = a.id
            WHERE f.id = ? AND f.deleted = FALSE""";
    private static final String GET_ALL = """
            SELECT f.id, f.title, f.description, f.release_year, f.length, f.poster, a.name AS age_rating
            FROM films f JOIN age_ratings a ON f.age_rating_id = a.id
            WHERE f.deleted = FALSE""";
    private static final String GET_ALL_PARTIALLY = """
            SELECT f.id, f.title, f.description, f.release_year, f.length, f.poster, a.name AS age_rating
            FROM films f JOIN age_ratings a ON f.age_rating_id = a.id
            WHERE f.deleted = FALSE
            ORDER BY f.id LIMIT ? OFFSET ?""";
    private static final String SEARCH_BY_TITLE_PARTIALLY = """
            SELECT f.id, f.title, f.description, f.release_year, f.length, f.poster, a.name AS age_rating
            FROM films f JOIN age_ratings a ON f.age_rating_id = a.id
            WHERE f.deleted = FALSE AND UPPER(f.title) LIKE UPPER(?)
            ORDER BY f.id LIMIT ? OFFSET ?""";
    private static final String CREATE = """
            INSERT INTO films (title, description, release_year, length, age_rating_id, poster)
            VALUES (?, ?, ?, ?, (SELECT id FROM age_ratings WHERE name = ?), ?)""";
    private static final String UPDATE = """
            UPDATE films SET title = ?, description = ?, release_year = ?, length = ?,
            age_rating_id = (SELECT id FROM age_ratings WHERE name = ?), poster = ?, last_update = NOW()
            WHERE id = ? AND DELETED = FALSE""";
    private static final String DELETE = """
            UPDATE films SET deleted = TRUE, last_update = NOW()
            WHERE id = ? AND deleted = FALSE""";
    private static final String COUNT = """
            SELECT COUNT(f.id) AS total
            FROM films f
            WHERE deleted = FALSE""";
    private static final String COUNT_BY_TITLE = """
            SELECT COUNT(f.id) AS total
            FROM films f
            WHERE deleted = FALSE AND UPPER(f.title) LIKE UPPER(?)""";

    private final DataSource dataSource;

    public FilmDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Film getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            return getById(id, connection);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Film getById(Long id, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
        statement.setLong(1, id);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return process(result);
        }
        return null;
    }

    @Override
    public List<Film> getAll() {
        List<Film> films = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(GET_ALL);

            while (result.next()) {
                films.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return films;
    }

    @Override
    public List<Film> getAll(int limit, long offset) {
        List<Film> films = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PARTIALLY);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                films.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return films;
    }

    @Override
    public List<Film> searchByTitle(String title, int limit, long offset) {
        List<Film> films = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SEARCH_BY_TITLE_PARTIALLY);
            statement.setString(1, formatForSearch(title));
            statement.setInt(2, limit);
            statement.setLong(3, offset);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                films.add(process(result));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return films;
    }

    @Override
    public Film create(Film entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getReleaseYear());
            statement.setInt(4, entity.getLength());
            statement.setString(5, entity.getAgeRating().toString().replace('_', '-'));
            statement.setString(6, entity.getPoster());
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
    public Film update(Film entity) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getReleaseYear());
            statement.setInt(4, entity.getLength());
            statement.setString(5, entity.getAgeRating().toString().replace('_', '-'));
            statement.setString(6, entity.getPoster());
            statement.setLong(7, entity.getId());
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
        throw new RuntimeException("Couldn't count films");
    }

    @Override
    public Long count(String title) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(COUNT_BY_TITLE);
            statement.setString(1, formatForSearch(title));
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new RuntimeException("Couldn't count films");
    }

    private Film process(ResultSet result) throws SQLException {
        Film film = new Film();
        film.setId(result.getLong("id"));
        film.setTitle(result.getString("title"));
        film.setDescription(result.getString("description"));
        film.setReleaseYear(result.getInt("release_year"));
        film.setLength(result.getInt("length"));
        film.setAgeRating(AgeRating.valueOf(result.getString("age_rating").replace('-', '_')));
        film.setPoster(result.getString("poster"));

        return film;
    }

    private String formatForSearch(String str) {
        return "%" + str + "%";
    }

}
