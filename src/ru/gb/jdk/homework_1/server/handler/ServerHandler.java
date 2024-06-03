package ru.gb.jdk.homework_1.server.handler;

import ru.gb.jdk.homework_1.client.handler.ClientHandler;
import ru.gb.jdk.homework_1.server.view.ServerView;
import ru.gb.jdk.homework_1.server.writer.Writable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServerHandler {
    private boolean ifConnected;
    private ArrayList<ClientHandler> clientsConnected;
    private ServerView view;
    private Writable writable;

    public ServerHandler(ServerView view, Writable writable) {
        clientsConnected = new ArrayList<>();
        this.view = view;
        this.writable = writable;
        view.setServerHandler(this);
    }

    public boolean isConnected() {
        return ifConnected;
    }

    public void setIfConnected(boolean ifConnected) {
        this.ifConnected = ifConnected;
    }

    public String getDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean connectNewClient(ClientHandler client){
        if(isConnected()){
            if(!clientsConnected.contains(client)){
                clientsConnected.add(client);
                historyToClient(client);
                msgServerToAll("Пользователь " + client.getLogin() +" поключился к серверу");
                return true;
            } else {
                msgToClient("Вы уже подключены к серверу", client);
            }
        } else {
            msgToClient("Сервер отключен. Подключение не выполнено", client);
        }
        return false;
    }

    public boolean disconnectClient(ClientHandler client){
        if(isConnected()){
            if(clientsConnected.contains(client)){
                clientsConnected.remove(client);
                msgServerToAll(client.getLogin() + " отключился от сервера");
                return true;
            }
        }
        return false;
    }

    public void stopServer(){
        if(isConnected()){
            setIfConnected(false);
            msgServerToAll("Сервер был остановлен");
            for (ClientHandler client : clientsConnected) {
                client.disconnectFromServer();
            }
            clientsConnected.clear();
        } else {
            msgToServer("Сервер уже остановлен");
        }
    }

    public void startServer(){
        if(!isConnected()){
            setIfConnected(true);
            historyOnServer();
            msgServerToAll("Сервер запущен");
        } else {
            msgToServer("Сервер уже запущен");
        }
    }

    public void msgServerToAll(String msg){
        for (ClientHandler client : clientsConnected) {
            client.getMessage(getDateTime() + " Server: " + msg + "\n");
        }
        msgToServer(msg);
    }

    public void msgFromClient(String msg, ClientHandler sender){
        String text = getDateTime() +" "+ sender.getLogin() + ": " + msg + "\n";
        for (ClientHandler client : clientsConnected) {
            client.getMessage(text);
        }
        view.appendInfo(text);
        writable.save(text);
    }

    public void msgToClient(String msg, ClientHandler client){
        client.getMessage(getDateTime() + " Server: " + msg + "\n");
    }

    public void msgToServer(String msg){
        view.appendInfo(getDateTime() + " Server: " + msg + "\n");
    }

    public void historyOnServer(){
        if(!writable.read().isEmpty()){
            view.appendInfo(writable.read());
        }
    }

    public void historyToClient(ClientHandler client){
        if(!writable.read().isEmpty()){
            client.getMessage(writable.read());
        }
    }
}
