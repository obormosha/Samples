package com.javarush.task.task31.task3110_backup.task3110.command;

import com.javarush.task.task31.task3110.ConsoleHelper;

/**
 * Created by Operator on 29.04.2017.
 */
public class ExitCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
