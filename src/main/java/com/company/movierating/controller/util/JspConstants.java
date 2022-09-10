package com.company.movierating.controller.util;

public abstract class JspConstants {

    public static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "errorMessage";
    public static final String SUCCESS_MESSAGE_ATTRIBUTE_NAME = "successMessage";
    public static final String LAST_PAGE_ATTRIBUTE_NAME = "lastPage";

    public static final String VIEW_USER = "jsp/view/user.jsp";
    public static final String VIEW_USERS = "jsp/view/users.jsp";
    public static final String VIEW_FILM = "jsp/view/film.jsp";
    public static final String VIEW_FILMS = "jsp/view/films.jsp";
    public static final String VIEW_BANS = "jsp/view/bans.jsp";
    public static final String VIEW_FILM_SCORES = "jsp/view/film_scores.jsp";
    public static final String VIEW_USER_SCORES = "jsp/view/user_scores.jsp";
    public static final String VIEW_FILM_REVIEWS = "jsp/view/film_reviews.jsp";
    public static final String VIEW_USER_REVIEWS = "jsp/view/user_reviews.jsp";
    public static final String MAIN_PAGE = "index.jsp";
    public static final String SIGN_IN_FORM = "jsp/sign_in/sign_in_form.jsp";
    public static final String CREATE_USER_FORM = "jsp/create/create_user_form.jsp";
    public static final String CREATE_BAN_FORM = "jsp/create/create_ban_form.jsp";
    public static final String CREATE_FILM_FORM = "jsp/create/create_film_form.jsp";
    public static final String CREATE_SCORE_FORM = "jsp/create/create_score_form.jsp";
    public static final String CREATE_REVIEW_FORM = "jsp/create/create_review_form.jsp";
    public static final String EDIT_USER_FORM = "jsp/edit/edit_user_form.jsp";
    public static final String EDIT_BAN_FORM = "jsp/edit/edit_ban_form.jsp";
    public static final String EDIT_FILM_FORM = "jsp/edit/edit_film_form.jsp";
    public static final String DEFAULT_ERROR = "jsp/error/error.jsp";

    public static final String REDIRECT_CREATE_BAN_FORM_COMMAND = "redirect:controller?command=create_ban_form&id=";
    public static final String REDIRECT_CREATE_FILM_FORM_COMMAND = "redirect:controller?command=create_film_form";
    public static final String REDIRECT_CREATE_REVIEW_FORM_COMMAND = "redirect:controller?command=create_review_form&id=";
    public static final String REDIRECT_USER_COMMAND = "redirect:controller?command=user&id=";
    public static final String REDIRECT_FILM_COMMAND = "redirect:controller?command=film&id=";

}
