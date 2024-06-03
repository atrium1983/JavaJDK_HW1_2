package ru.gb.jdk.homework_1.server.view;

import ru.gb.jdk.homework_1.server.handler.ServerHandler;

public interface ServerView {
    void appendInfo(String text);
    void setServerHandler(ServerHandler handler);
}
