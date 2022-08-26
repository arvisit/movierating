package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.ScoreDao;
import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.connection.ConfigurationManager;
import com.company.movierating.dao.entity.Film;
import com.company.movierating.dao.entity.Score;
import com.company.movierating.dao.entity.User;
import com.company.movierating.dao.structure.LimitReward;
import com.company.movierating.service.ReputationService;

public class ReputationServiceImpl implements ReputationService {
    private final int scoresBeforeUpdate;
    private final List<LimitReward> rewardProperties;

    private final ScoreDao scoreDao;
    private final UserDao userDao;

    public ReputationServiceImpl(ScoreDao scoreDao, UserDao userDao, ConfigurationManager properties) {
        this.scoreDao = scoreDao;
        this.userDao = userDao;
        this.scoresBeforeUpdate = properties.getScoresBeforeUpdate();
        this.rewardProperties = properties.getRewardProperties();
    }

    @Override
    public void updateReputation(Film film) {
        List<Score> lastScores = scoreDao.getAllByFilm(film.getId(), scoresBeforeUpdate + 1, 0);
        if (lastScores.size() < scoresBeforeUpdate + 1) {
            return;
        }
        Double filmAverageScore = scoreDao.countFilmAverageScore(film.getId());
        Double deviation = Math.abs(filmAverageScore - lastScores.get(lastScores.size() - 1).getValue());
        User user = lastScores.get(lastScores.size() - 1).getUser();

        rewardProperties.stream() //
                .filter(p -> deviation <= p.getLimit()) //
                .limit(1) //
                .forEach(p -> user.setReputation(user.getReputation() + p.getReward()));

        userDao.update(user);
    }

}
