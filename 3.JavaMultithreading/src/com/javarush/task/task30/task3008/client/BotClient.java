package com.javarush.task.task30.task3008.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by User on 16.04.2017.
 */
public class BotClient extends Client {
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            super.processIncomingMessage(message);

            if (message != null && !message.isEmpty() && message.contains(":")) {
                int indexOfSeparator = message.indexOf(":");
                String name = message.substring(0, indexOfSeparator);
                String request = message.substring(indexOfSeparator + 1);

                SimpleDateFormat dateFormat = null;
                if (request.contains("дата")) {
                    dateFormat = new SimpleDateFormat("d.MM.YYYY");
                } else if (request.contains("день")) {
                    dateFormat = new SimpleDateFormat("d");
                } else if (request.contains("месяц")) {
                    dateFormat = new SimpleDateFormat("MMMM");
                } else if (request.contains("год")) {
                    dateFormat = new SimpleDateFormat("YYYY");
                } else if (request.contains("время")) {
                    dateFormat = new SimpleDateFormat("H:mm:ss");
                } else if (request.contains("час")) {
                    dateFormat = new SimpleDateFormat("H");
                } else if (request.contains("минуты")) {
                    dateFormat = new SimpleDateFormat("m");
                } else if (request.contains("секунды")) {
                    dateFormat = new SimpleDateFormat("s");
                }
                String answer = null;
                if (dateFormat != null) {
                    answer = dateFormat.format(Calendar.getInstance().getTime());
                    sendTextMessage("Информация для " + name + ": " + answer);
                }

            }
        }
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (100 * Math.random());
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
