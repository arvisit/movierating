package com.company.movierating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.company.movierating.dao.FilmDao;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.dao.entity.Film;
import com.company.movierating.dao.entity.Film.AgeRating;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FilmDaoImpl implements FilmDao {
    private static final String GET_BY_ID = "SELECT f.id, f.title, f.description, f.release_year, f.length, a.name AS age_rating "
            + "FROM films f JOIN age_ratings a ON f.age_rating_id = a.id " //
            + "WHERE f.id = ? AND f.deleted = FALSE";
    private static final String GET_ALL = "SELECT f.id, f.title, f.description, f.release_year, f.length, a.name AS age_rating "
            + "FROM films f JOIN age_ratings a ON f.age_rating_id = a.id " //
            + "WHERE f.deleted = FALSE";
    private static final String GET_ALL_PARTIALLY = "SELECT f.id, f.title, f.description, f.release_year, f.length, a.name AS age_rating "
            + "FROM films f JOIN age_ratings a ON f.age_rating_id = a.id " //
            + "WHERE f.deleted = FALSE " //
            + "ORDER BY f.id LIMIT ? OFFSET ?";
    private static final String CREATE = "INSERT INTO films (title, description, release_year, length, age_rating_id) "
            + "VALUES (?, ?, ?, ?, (SELECT id FROM age_ratings WHERE name = ?))";
    private static final String UPDATE = "UPDATE films SET title = ?, description = ?, release_year = ?, length = ?, //"
            + "age_rating_id = (SELECT id FROM age_ratings WHERE name = ?), last_update = NOW() " //
            + "WHERE id = ? AND DELETED = FALSE";
    private static final String DELETE = "UPDATE films SET deleted = TRUE, last_update = NOW() WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(f.id) AS total " //
            + "FROM films f " //
            + "WHERE deleted = FALSE";

    private final DataSource dataSource;

    public FilmDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Film getById(Long id) {
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
    public List<Film> getAll() {
        return null;
    }

    @Override
    public List<Film> getAll(int limit, long offset) {
        return null;
    }

    @Override
    public Film create(Film entity) {
        return null;
    }

    @Override
    public Film update(Film entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Long count() {
        return null;
    }

    private Film process(ResultSet result) throws SQLException {
        Film film = new Film();
        film.setId(result.getLong("id"));
        film.setTitle(result.getString("title"));
        film.setDescription(result.getString("description"));
        film.setReleaseYear(result.getInt("release_year"));
        film.setLength(result.getInt("length"));
        film.setAgeRating(AgeRating.valueOf(result.getString("age_rating").replace('-', '_')));

        return film;
    }

}
