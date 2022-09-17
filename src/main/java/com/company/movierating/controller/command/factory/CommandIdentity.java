package com.company.movierating.controller.command.factory;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.impl.ErrorCommand;
import com.company.movierating.controller.command.impl.NoSuchCommand;
import com.company.movierating.controller.command.impl.SignInCommand;
import com.company.movierating.controller.command.impl.SignInFormCommand;
import com.company.movierating.controller.command.impl.SignOutCommand;
import com.company.movierating.controller.command.impl.ban.AssignedBansCommand;
import com.company.movierating.controller.command.impl.ban.CreateBanCommand;
import com.company.movierating.controller.command.impl.ban.CreateBanFormCommand;
import com.company.movierating.controller.command.impl.ban.EditBanCommand;
import com.company.movierating.controller.command.impl.ban.EditBanFormCommand;
import com.company.movierating.controller.command.impl.ban.UserBansCommand;
import com.company.movierating.controller.command.impl.film.CreateFilmCommand;
import com.company.movierating.controller.command.impl.film.CreateFilmFormCommand;
import com.company.movierating.controller.command.impl.film.EditFilmCommand;
import com.company.movierating.controller.command.impl.film.EditFilmFormCommand;
import com.company.movierating.controller.command.impl.film.FilmCommand;
import com.company.movierating.controller.command.impl.film.FilmsCommand;
import com.company.movierating.controller.command.impl.film.SearchFilmCommand;
import com.company.movierating.controller.command.impl.review.CreateReviewCommand;
import com.company.movierating.controller.command.impl.review.CreateReviewFormCommand;
import com.company.movierating.controller.command.impl.review.FilmReviewsCommand;
import com.company.movierating.controller.command.impl.review.UserReviewsCommand;
import com.company.movierating.controller.command.impl.score.CreateScoreCommand;
import com.company.movierating.controller.command.impl.score.CreateScoreFormCommand;
import com.company.movierating.controller.command.impl.score.FilmScoresCommand;
import com.company.movierating.controller.command.impl.score.UserScoresCommand;
import com.company.movierating.controller.command.impl.user.CreateUserCommand;
import com.company.movierating.controller.command.impl.user.CreateUserFormCommand;
import com.company.movierating.controller.command.impl.user.EditUserCommand;
import com.company.movierating.controller.command.impl.user.EditUserFormCommand;
import com.company.movierating.controller.command.impl.user.UserCommand;
import com.company.movierating.controller.command.impl.user.UsersCommand;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.service.BanService;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.ReviewService;
import com.company.movierating.service.ScoreService;
import com.company.movierating.service.UserService;
import com.company.movierating.service.factory.ServiceFactory;

import lombok.Getter;

public enum CommandIdentity {
    NO_SUCH(new NoSuchCommand(), SecurityLevel.GUEST), ERROR(new ErrorCommand(), SecurityLevel.GUEST),

    USERS(new UsersCommand(ServiceFactory.getInstance().getService(UserService.class), Paginator.INSTANCE),
            SecurityLevel.GUEST),
    USER(new UserCommand(ServiceFactory.getInstance().getService(UserService.class), ParametersPreparer.INSTANCE),
            SecurityLevel.GUEST),

    USER_BANS(new UserBansCommand(ServiceFactory.getInstance().getService(BanService.class),
            ParametersPreparer.INSTANCE, Paginator.INSTANCE), SecurityLevel.USER_SELF),
    ASSIGNED_BANS(new AssignedBansCommand(ServiceFactory.getInstance().getService(BanService.class),
            ParametersPreparer.INSTANCE, Paginator.INSTANCE), SecurityLevel.ADMIN_SELF),

    SIGN_IN_FORM(new SignInFormCommand(), SecurityLevel.GUEST),
    SIGN_IN(new SignInCommand(ServiceFactory.getInstance().getService(UserService.class)), SecurityLevel.GUEST),
    SIGN_OUT(new SignOutCommand(), SecurityLevel.USER),

    CREATE_USER_FORM(new CreateUserFormCommand(), SecurityLevel.GUEST),
    CREATE_USER(new CreateUserCommand(ServiceFactory.getInstance().getService(UserService.class)), SecurityLevel.GUEST),

    EDIT_USER_FORM(new EditUserFormCommand(ServiceFactory.getInstance().getService(UserService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.USER_SELF),
    EDIT_USER(new EditUserCommand(ServiceFactory.getInstance().getService(UserService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.USER_SELF),

    CREATE_BAN_FORM(new CreateBanFormCommand(ServiceFactory.getInstance().getService(UserService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.ADMIN_SELF),
    CREATE_BAN(new CreateBanCommand(ServiceFactory.getInstance().getService(BanService.class), //
            ServiceFactory.getInstance().getService(UserService.class), ParametersPreparer.INSTANCE),
            SecurityLevel.ADMIN_SELF),

    EDIT_BAN_FORM(new EditBanFormCommand(ServiceFactory.getInstance().getService(BanService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.ADMIN),
    EDIT_BAN(new EditBanCommand(ServiceFactory.getInstance().getService(BanService.class), ParametersPreparer.INSTANCE),
            SecurityLevel.ADMIN),

    FILMS(new FilmsCommand(ServiceFactory.getInstance().getService(FilmService.class), Paginator.INSTANCE),
            SecurityLevel.GUEST),
    SEARCH_FILM(new SearchFilmCommand(ServiceFactory.getInstance().getService(FilmService.class), Paginator.INSTANCE),
            SecurityLevel.GUEST),
    FILM(new FilmCommand(ServiceFactory.getInstance().getService(FilmService.class), ParametersPreparer.INSTANCE),
            SecurityLevel.GUEST),

    CREATE_FILM_FORM(new CreateFilmFormCommand(), SecurityLevel.ADMIN),
    CREATE_FILM(new CreateFilmCommand(ServiceFactory.getInstance().getService(FilmService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.ADMIN),

    EDIT_FILM_FORM(new EditFilmFormCommand(ServiceFactory.getInstance().getService(FilmService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.ADMIN),
    EDIT_FILM(new EditFilmCommand(ServiceFactory.getInstance().getService(FilmService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.ADMIN),

    FILM_SCORES(new FilmScoresCommand(ServiceFactory.getInstance().getService(ScoreService.class),
            ParametersPreparer.INSTANCE, Paginator.INSTANCE), SecurityLevel.GUEST),
    USER_SCORES(new UserScoresCommand(ServiceFactory.getInstance().getService(ScoreService.class),
            ParametersPreparer.INSTANCE, Paginator.INSTANCE), SecurityLevel.GUEST),

    CREATE_SCORE_FORM(new CreateScoreFormCommand(ServiceFactory.getInstance().getService(FilmService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.USER_SELF_NOT_BANNED),
    CREATE_SCORE(new CreateScoreCommand(ServiceFactory.getInstance().getService(ScoreService.class), //
            ServiceFactory.getInstance().getService(FilmService.class), ParametersPreparer.INSTANCE),
            SecurityLevel.USER_SELF_NOT_BANNED),

    FILM_REVIEWS(new FilmReviewsCommand(ServiceFactory.getInstance().getService(ReviewService.class),
            ServiceFactory.getInstance().getService(FilmService.class), ParametersPreparer.INSTANCE,
            Paginator.INSTANCE), SecurityLevel.GUEST),
    USER_REVIEWS(new UserReviewsCommand(ServiceFactory.getInstance().getService(ReviewService.class),
            ParametersPreparer.INSTANCE, Paginator.INSTANCE), SecurityLevel.GUEST),

    CREATE_REVIEW_FORM(new CreateReviewFormCommand(ServiceFactory.getInstance().getService(FilmService.class),
            ParametersPreparer.INSTANCE), SecurityLevel.USER_SELF_NOT_BANNED),
    CREATE_REVIEW(new CreateReviewCommand(ServiceFactory.getInstance().getService(ReviewService.class), //
            ServiceFactory.getInstance().getService(FilmService.class), ParametersPreparer.INSTANCE),
            SecurityLevel.USER_SELF_NOT_BANNED);

    @Getter
    private final Command command;
    @Getter
    private final SecurityLevel securityLevel;

    CommandIdentity(Command command, SecurityLevel securityLevel) {
        this.command = command;
        this.securityLevel = securityLevel;
    }
}
