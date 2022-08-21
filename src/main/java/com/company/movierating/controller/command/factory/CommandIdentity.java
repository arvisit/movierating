package com.company.movierating.controller.command.factory;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.impl.AssignedBansCommand;
import com.company.movierating.controller.command.impl.CreateBanCommand;
import com.company.movierating.controller.command.impl.CreateBanFormCommand;
import com.company.movierating.controller.command.impl.CreateUserCommand;
import com.company.movierating.controller.command.impl.CreateUserFormCommand;
import com.company.movierating.controller.command.impl.EditBanCommand;
import com.company.movierating.controller.command.impl.EditBanFormCommand;
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
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.service.BanService;
import com.company.movierating.service.UserService;
import com.company.movierating.service.factory.ServiceFactory;

import lombok.Getter;

public enum CommandIdentity {
    ERROR(new ErrorCommand(), SecurityLevel.GUEST),

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
            SecurityLevel.ADMIN);

    @Getter
    private final Command command;
    @Getter
    private final SecurityLevel securityLevel;

    CommandIdentity(Command command, SecurityLevel securityLevel) {
        this.command = command;
        this.securityLevel = securityLevel;
    }
}
