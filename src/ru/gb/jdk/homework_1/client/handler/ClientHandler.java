package ru.gb.jdk.homework_1.client.handler;

import ru.gb.jdk.homework_1.client.view.ClientView;
import ru.gb.jdk.homework_1.server.handler.ServerHandler;

public class ClientHandler {
    private ServerHandler server;
    private ClientView view;
    private boolean ifConnected;

    public ClientHandler(ServerHandler server, ClientView view) {
        this.server = server;
        this.view = view;
        ifConnected = false;
        view.setClientHandler(this);
    }

    public boolean isIfConnected() {
        return ifConnected;
    }

    public void setIfConnected(boolean flag) {
        this.ifConnected = flag;
    }

    public String getLogin() {
        return view.getLogin();
    }

    public void connectToServer(){
        if(server.connectNewClient(this)){
            view.panelTopVisibility(false);
            setIfConnected(true);
        }
    }

    public void disconnectFromServer(){
        setIfConnected(false);
        view.panelTopVisibility(true);
    }

    public void disconnected(){
        server.disconnectClient(this);
    }

    public void getMessage(String msg){
        view.getMessage(msg);
    }

    public void sendMessage(){
        String text = view.getFromMessageLine();
        if(isIfConnected()){
            server.msgFromClient(text, this);
        } else {
            getMessage("Для отправки сообщений подключитесь к серверу\n");
        }
    }
}
