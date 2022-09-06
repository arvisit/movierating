package com.company.movierating.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.company.movierating.dao.structure.LimitReward;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConfigurationManager {
    @Getter
    private final String url;
    @Getter
    private final String user;
    @Getter
    private final String password;
    @Getter
    private final String driver;
    @Getter
    private final Integer poolSize;
    @Getter
    private final Integer scoresBeforeUpdate;
    @Getter
    private final List<LimitReward> rewardProperties;
    @Getter
    private final Integer reviewMaxLength;
    @Getter
    private final String imageRootPath;
    @Getter
    private final String imageDefaultAvatar;
    @Getter
    private final String imageDefaultPoster;
    private static final String PROPERTIES_FILE = "/application.properties";

    private static class ConfigurationManagerHolder {
        public static final ConfigurationManager HOLDER_INSTANCE = new ConfigurationManager();
    }

    private ConfigurationManager() {
        Properties properties = new Properties();
        try (InputStream is = ConfigurationManager.class.getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(is);
            if (System.getenv("USE_LOCAL_DB") != null) {
                url = properties.getProperty("db.test.url");
                user = properties.getProperty("db.test.user");
                password = properties.getProperty("db.test.password");
            } else {
                url = properties.getProperty("db.prod.url");
                user = properties.getProperty("db.prod.user");
                password = properties.getProperty("db.prod.password");
            }
            driver = properties.getProperty("db.driver");
            poolSize = Integer.valueOf(properties.getProperty("pool.size"));
            scoresBeforeUpdate = Integer.valueOf(properties.getProperty("reputation.scores_before_update"));

            String[] limitsStr = properties.getProperty("reputation.deviation_limits").split("#");
            List<Double> limits = Arrays.asList(limitsStr).stream().map(Double::parseDouble).toList();
            String[] rewardsStr = properties.getProperty("reputation.rewards").split("#");
            List<Integer> rewards = Arrays.asList(rewardsStr).stream().map(Integer::parseInt).toList();

            rewardProperties = new ArrayList<>();
            for (int i = 0; i < limits.size(); i++) {
                rewardProperties.add(new LimitReward(limits.get(i), rewards.get(i)));
            }

            reviewMaxLength = Integer.valueOf(properties.getProperty("review.max_length"));

            imageRootPath = properties.getProperty("image.root");
            imageDefaultAvatar = properties.getProperty("image.avatar.default");
            imageDefaultPoster = properties.getProperty("image.poster.default");

            log.info("Configuration properties were loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConfigurationManager getInstance() {
        return ConfigurationManagerHolder.HOLDER_INSTANCE;
    }
}
