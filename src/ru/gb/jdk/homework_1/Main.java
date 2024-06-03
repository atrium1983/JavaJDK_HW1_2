package ru.gb.jdk.homework_1;

import ru.gb.jdk.homework_1.client.handler.ClientHandler;
import ru.gb.jdk.homework_1.client.view.ClientWindow;
import ru.gb.jdk.homework_1.server.handler.ServerHandler;
import ru.gb.jdk.homework_1.server.view.ServerWindow;
import ru.gb.jdk.homework_1.server.writer.ChatHandler;

public class Main {
    public static void main(String[] args) {
        ServerHandler server = new ServerHandler(new ServerWindow(), new ChatHandler());
        new ClientHandler(server, new ClientWindow());
        new ClientHandler(server, new ClientWindow());
    }
}
