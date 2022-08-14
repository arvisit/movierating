package com.company.movierating.controller.command.factory;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.impl.AssignedBansCommand;
import com.company.movierating.controller.command.impl.CreateUserCommand;
import com.company.movierating.controller.command.impl.CreateUserFormCommand;
import com.company.movierating.controller.command.impl.EditUserCommand;
import com.company.movierating.controller.command.impl.EditUserFormCommand;
import com.company.movierating.controller.command.impl.ErrorCommand;
import com.company.movierating.controller.command.impl.SignInCommand;
import com.company.movierating.controller.command.impl.SignInFormCommand;
import com.company.movierating.controller.command.impl.SignOutCommand;
import com.company.movierating.controller.command.impl.UserBansCommand;
import com.company.movierating.controller.command.impl.UserCommand;
import com.company.movierating.controller.command.impl.UsersCommand;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.BanService;
import com.company.movierating.service.UserService;
import com.company.movierating.service.factory.ServiceFactory;

import lombok.Getter;

public enum CommandIdentity {
    ERROR(new ErrorCommand()),

    USERS(new UsersCommand(ServiceFactory.getInstance().getService(UserService.class), Paginator.INSTANCE)),
    USER(new UserCommand(ServiceFactory.getInstance().getService(UserService.class), ParametersPreparer.INSTANCE)),

    USER_BANS(new UserBansCommand(ServiceFactory.getInstance().getService(BanService.class),
            ParametersPreparer.INSTANCE, Paginator.INSTANCE)),
    ASSIGNED_BANS(new AssignedBansCommand(ServiceFactory.getInstance().getService(BanService.class),
            ServiceFactory.getInstance().getService(UserService.class), ParametersPreparer.INSTANCE,
            Paginator.INSTANCE)),

    SIGN_IN_FORM(new SignInFormCommand()),
    SIGN_IN(new SignInCommand(ServiceFactory.getInstance().getService(UserService.class))),
    SIGN_OUT(new SignOutCommand()),

    CREATE_USER_FORM(new CreateUserFormCommand()),
    CREATE_USER(new CreateUserCommand(ServiceFactory.getInstance().getService(UserService.class))),

    EDIT_USER_FORM(new EditUserFormCommand(ServiceFactory.getInstance().getService(UserService.class),
            ParametersPreparer.INSTANCE)),
    EDIT_USER(new EditUserCommand(ServiceFactory.getInstance().getService(UserService.class),
            ParametersPreparer.INSTANCE));

    @Getter
    private final Command command;

    CommandIdentity(Command command) {
        this.command = command;
    }
}
