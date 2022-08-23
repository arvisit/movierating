package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.ScoreDao;
import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.entity.Film;
import com.company.movierating.dao.entity.Score;
import com.company.movierating.dao.entity.User;
import com.company.movierating.service.ReputationService;

public class ReputationServiceImpl implements ReputationService {
    private static final int SCORES_BEFORE_UPDATE = 1;

    private final ScoreDao scoreDao;
    private final UserDao userDao;

    public ReputationServiceImpl(ScoreDao scoreDao, UserDao userDao) {
        this.scoreDao = scoreDao;
        this.userDao = userDao;
    }

    @Override
    public void updateReputation(Film film) {
        List<Score> lastScores = scoreDao.getAllByFilm(film.getId(), SCORES_BEFORE_UPDATE + 1, 0);
        if (lastScores.size() < SCORES_BEFORE_UPDATE + 1) {
            return;
        }
        Double filmAverageScore = scoreDao.countFilmAverageScore(film.getId());
        Double deviation = Math.abs(filmAverageScore - lastScores.get(lastScores.size() - 1).getValue());
        User user = lastScores.get(lastScores.size() - 1).getUser();
        if (deviation <= 0.1) {
            user.setReputation(user.getReputation() + 10);
        } else if (deviation <= 0.5) {
            user.setReputation(user.getReputation() + 8);
        } else if (deviation <= 1.0) {
            user.setReputation(user.getReputation() + 4);
        } else if (deviation <= 2.0) {
            user.setReputation(user.getReputation() + 0);
        } else if (deviation <= 3.0) {
            user.setReputation(user.getReputation() + (-4));
        } else if (deviation <= 4.0) {
            user.setReputation(user.getReputation() + (-8));
        } else {
            user.setReputation(user.getReputation() + (-10));
        }
        userDao.update(user);
    }

}
