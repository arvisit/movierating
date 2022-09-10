package com.company.movierating.controller.command.factory;

public class CommandFactory {

    private static class CommandFactoryHolder {
        public static final CommandFactory HOLDER_INSTANCE = new CommandFactory();
    }

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return CommandFactoryHolder.HOLDER_INSTANCE;
    }

    public CommandIdentity getCommandIdentity(String command) {
        CommandIdentity commandIdentity;
        try {
            commandIdentity = CommandIdentity.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandIdentity = CommandIdentity.valueOf("NO_SUCH");
        }
        if (commandIdentity == null) {
            commandIdentity = CommandIdentity.valueOf("NO_SUCH");
        }
        return commandIdentity;
    }
}
