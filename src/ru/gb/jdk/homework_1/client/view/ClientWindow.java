package ru.gb.jdk.homework_1.client.view;

import ru.gb.jdk.homework_1.client.handler.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientWindow extends JFrame implements ClientView {
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;

    private JPanel panelTop;
    private JTextField fieldIp, fieldPort, fieldLogin, fieldMessage;
    private JPasswordField fieldPassword;
    private JButton buttonLogin, buttonSend;
    private JTextArea panelText;
    private JPanel panelBottom;
    private ClientHandler handler;

    public ClientWindow(){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setLocationRelativeTo(null);
        setTitle("Chat Client");
        disconnectionSetting();

        createWindow();
    }

    public void disconnectionSetting(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handler.disconnected();
            }
        });
    }

    public void createWindow(){
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createMessageArea());
        setVisible(true);
    }

    public Component createTopPanel(){
        panelTop = new JPanel(new GridLayout(2, 3));
        fieldIp = new JTextField("127.0.0.1");
        fieldPort = new JTextField("8080");
        fieldLogin = new JTextField("my_login");
        fieldPassword = new JPasswordField("1234");
        buttonLogin = new JButton("Login");
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.connectToServer();
            }
        });
        panelTop.add(fieldIp);
        panelTop.add(fieldPort);
        panelTop.add(fieldLogin);
        panelTop.add(fieldPassword);
        panelTop.add(buttonLogin);

        return panelTop;
    }

    public Component createBottomPanel(){
        panelBottom = new JPanel(new BorderLayout());
        fieldMessage = new JTextField();
        fieldMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    handler.sendMessage();
                }
            }
        });
        buttonSend = new JButton("Send");
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.sendMessage();
            }
        });
        panelBottom.add(fieldMessage, BorderLayout.CENTER);
        panelBottom.add(buttonSend, BorderLayout.EAST);

        return panelBottom;
    }

    public Component createMessageArea(){
        panelText = new JTextArea();
        panelText.setEditable(false);
        return new JScrollPane(panelText);
    }

    @Override
    public String getLogin() {
        return fieldLogin.getText();
    };

    @Override
    public void panelTopVisibility(Boolean value) {
        panelTop.setVisible(value);
    }

    @Override
    public void getMessage(String msg) {
        panelText.append(msg);
    }

    @Override
    public String getFromMessageLine() {
        String msg = fieldMessage.getText();
        if(!msg.isEmpty() && !msg.isBlank()){
            fieldMessage.setText("");
            return msg;
        } else {
            getMessage("Вы не ввели сообщение\n");
        }
        return null;
    }

    @Override
    public void setClientHandler(ClientHandler handler) {
        this.handler = handler;
    }
}

