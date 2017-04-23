package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Пользователь on 16.04.2017.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    protected String getServerAddress() {
        ConsoleHelper.writeMessage("Enter a server address");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        ConsoleHelper.writeMessage("Enter a server port");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("Enter a user name");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    protected SocketThread getSocketThread() {
        SocketThread socketThread = new SocketThread();
        return socketThread;
    }

    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            e.getMessage();
            clientConnected = false;
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();

        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.getMessage();
            System.exit(1);
        }


        if (clientConnected) {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду ‘exit’.");
        } else {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        }
        while (clientConnected) {
            String text = ConsoleHelper.readString();
            if (text.equals("exit")) {
                break;
            }
            if (shouldSendTextFromConsole()) {
                sendTextMessage(text);
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    public class SocketThread extends Thread {

        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage("Участник с именем " + userName + " присоединился к чату");
        }

        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage("Участник с именем " + userName + " покинул чат");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = Client.this.connection.receive();

                try {
                    if (message.getType().equals(MessageType.NAME_REQUEST)) {
                        String name = getUserName();
                        Client.this.connection.send(new Message(MessageType.USER_NAME, name));
                    } else if (message.getType().equals(MessageType.NAME_ACCEPTED)) {
                        notifyConnectionStatusChanged(true);
                        return;
                    } else {
                        throw new IOException("Unexpected MessageType");
                    }
                } catch (Exception e) {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = Client.this.connection.receive();
                try {
                    if (message.getType().equals(MessageType.TEXT)) {
                        processIncomingMessage(message.getData());
                    } else if (message.getType().equals(MessageType.USER_ADDED)) {
                        informAboutAddingNewUser(message.getData());
                    } else if (message.getType().equals(MessageType.USER_REMOVED)) {
                        informAboutDeletingNewUser(message.getData());
                    } else {
                        throw new IOException("Unexpected MessageType");
                    }
                } catch (Exception e) {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        public void run() {
            String host = getServerAddress();
            int port = getServerPort();
            try {
                Socket socket = new Socket(host, port);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();

            } catch (ClassNotFoundException | IOException ex) {
                notifyConnectionStatusChanged(false);
            }
        }

    }
}
