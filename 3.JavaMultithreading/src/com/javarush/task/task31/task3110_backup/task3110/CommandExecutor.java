package com.javarush.task.task31.task3110_backup.task3110;

import com.javarush.task.task31.task3110.Operation;
import com.javarush.task.task31.task3110.command.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Operator on 29.04.2017.
 */
public class CommandExecutor {
    private CommandExecutor() {
    }

    private static final Map<com.javarush.task.task31.task3110.Operation, Command> allKnownCommandsMap = new HashMap<>();

    static {
        allKnownCommandsMap.put(com.javarush.task.task31.task3110.Operation.ADD, new ZipAddCommand());
        allKnownCommandsMap.put(com.javarush.task.task31.task3110.Operation.CONTENT, new ZipContentCommand());
        allKnownCommandsMap.put(com.javarush.task.task31.task3110.Operation.CREATE, new ZipCreateCommand());
        allKnownCommandsMap.put(com.javarush.task.task31.task3110.Operation.EXTRACT, new ZipExtractCommand());
        allKnownCommandsMap.put(com.javarush.task.task31.task3110.Operation.REMOVE, new ZipRemoveCommand());
        allKnownCommandsMap.put(com.javarush.task.task31.task3110.Operation.EXIT, new ExitCommand());
    }

    public static void execute(Operation operation) throws Exception {
        allKnownCommandsMap.get(operation).execute();
    }
}
