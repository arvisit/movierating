package com.company.movierating.controller.command.factory;

import java.util.HashMap;
import java.util.Map;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.impl.ErrorCommand;
import com.company.movierating.controller.command.impl.UserCommand;
import com.company.movierating.controller.command.impl.UsersCommand;
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
        commands.put("users", new UsersCommand(services.getService(UserService.class)));
        commands.put("user", new UserCommand(services.getService(UserService.class)));
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
