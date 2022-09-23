package com.company.movierating;

import java.util.List;

import com.company.movierating.dao.connection.ConfigurationManager;

public class AppConstants {

    public static final String DEFAULT_AVATAR_NAME = ConfigurationManager.getInstance().getImageDefaultAvatar();
    public static final String DEFAULT_POSTER_NAME = ConfigurationManager.getInstance().getImageDefaultPoster();

    public static final String IMAGE_STORAGE_ROOT = ConfigurationManager.getInstance().getStorageRootPath() + "/img";
    public static final String IMAGE_STORAGE_AVATAR = IMAGE_STORAGE_ROOT + "/avatar";
    public static final String IMAGE_STORAGE_POSTER = IMAGE_STORAGE_ROOT + "/poster";
    public static final String DEFAULT_STORAGE_AVATAR = IMAGE_STORAGE_AVATAR + "/" + DEFAULT_AVATAR_NAME;
    public static final String DEFAULT_STORAGE_POSTER = IMAGE_STORAGE_POSTER + "/" + DEFAULT_POSTER_NAME;

    public static final String IMAGE_APP_ROOT = "img";
    public static final String IMAGE_APP_AVATAR = IMAGE_APP_ROOT + "/avatar";
    public static final String IMAGE_APP_POSTER = IMAGE_APP_ROOT + "/poster";
    public static final String DEFAULT_APP_AVATAR = IMAGE_APP_AVATAR + "/" + DEFAULT_AVATAR_NAME;
    public static final String DEFAULT_APP_POSTER = IMAGE_APP_POSTER + "/" + DEFAULT_POSTER_NAME;
    
    public static final List<String> AVAILABLE_LANGUAGES = ConfigurationManager.getInstance().getLanguages();
}
