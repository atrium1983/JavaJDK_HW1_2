package ru.gb.jdk.homework_1.server.view;

import ru.gb.jdk.homework_1.server.handler.ServerHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements ServerView {
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    private JPanel panelBottom;
    private JButton buttonStart, buttonStop;
    private JTextArea panelText;
    private ServerHandler handler;

    public ServerWindow (){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setLocationRelativeTo(null);
        setTitle("Chat Server");
        createWindow();
    }

    public void createWindow(){
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createMessageArea());
        setVisible(true);
    }

    public Component createBottomPanel(){
        panelBottom = new JPanel(new GridLayout(1, 2));
        buttonStart = new JButton("Start");
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.startServer();
            }
        });
        buttonStop = new JButton("Stop");
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.stopServer();
            }
        });
        panelBottom.add(buttonStart);
        panelBottom.add(buttonStop);
        return panelBottom;
    }

    public Component createMessageArea(){
        panelText = new JTextArea();
        panelText.setEditable(false);
        return new JScrollPane(panelText);
    }

    @Override
    public void appendInfo(String text) {
        panelText.append(text);
    }

    @Override
    public void setServerHandler(ServerHandler handler) {
        this.handler = handler;
    }
}
