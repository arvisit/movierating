package com.company.movierating.controller.command.factory;

import java.util.HashMap;
import java.util.Map;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.impl.CreateUserCommand;
import com.company.movierating.controller.command.impl.CreateUserFormCommand;
import com.company.movierating.controller.command.impl.EditUserCommand;
import com.company.movierating.controller.command.impl.EditUserFormCommand;
import com.company.movierating.controller.command.impl.ErrorCommand;
import com.company.movierating.controller.command.impl.SignInCommand;
import com.company.movierating.controller.command.impl.SignInFormCommand;
import com.company.movierating.controller.command.impl.SignOutCommand;
import com.company.movierating.controller.command.impl.UserCommand;
import com.company.movierating.controller.command.impl.UsersCommand;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.UserService;
import com.company.movierating.service.factory.ServiceFactory;

public class CommandFactory {
    private final Map<String, Command> commands;

    private static class CommandFactoryHolder {
        public static final CommandFactory HOLDER_INSTANCE = new CommandFactory();
    }

    private CommandFactory() {
        ServiceFactory services = ServiceFactory.getInstance();

        commands = new HashMap<>();
        commands.put("users", new UsersCommand(services.getService(UserService.class), Paginator.INSTANCE));
        commands.put("user", new UserCommand(services.getService(UserService.class), ParametersPreparer.INSTANCE));
        commands.put("create_user_form", new CreateUserFormCommand());
        commands.put("create_user", new CreateUserCommand(services.getService(UserService.class)));
        commands.put("edit_user_form",
                new EditUserFormCommand(services.getService(UserService.class), ParametersPreparer.INSTANCE));
        commands.put("edit_user",
                new EditUserCommand(services.getService(UserService.class), ParametersPreparer.INSTANCE));
        commands.put("sign_in_form", new SignInFormCommand());
        commands.put("sign_in", new SignInCommand(services.getService(UserService.class)));
        commands.put("sign_out", new SignOutCommand());
        commands.put("error", new ErrorCommand());
    }

    public static CommandFactory getInstance() {
        return CommandFactoryHolder.HOLDER_INSTANCE;
    }

    public Command getCommand(String command) {
        Command commandInstance = commands.get(command);
        if (commandInstance == null) {
            commandInstance = commands.get("error");
        }
        return commandInstance;
    }
}
