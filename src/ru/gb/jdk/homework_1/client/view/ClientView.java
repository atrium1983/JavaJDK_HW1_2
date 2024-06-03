package ru.gb.jdk.homework_1.client.view;

import ru.gb.jdk.homework_1.client.handler.ClientHandler;

public interface ClientView {
    String getLogin();
    void panelTopVisibility(Boolean value);
    void getMessage(String msg);
    String getFromMessageLine();
    void setClientHandler(ClientHandler handler);

}
