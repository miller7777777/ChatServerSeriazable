package com.company;

import java.io.*;
import java.net.Socket;

/**
 * Created by vladimir on 19.05.15.
 */
public class Client extends Thread {


    private String login;

    public String getLogin() {
        return login;
    }

    private Socket sock;
    private ChatServer server;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Packet welcomeMessage;

    public Client(Socket sock, ChatServer server) throws IOException {
        this.server = server;
        this.sock = sock;

        is = new ObjectInputStream(sock.getInputStream());
        os = new ObjectOutputStream(sock.getOutputStream());
        os.flush();


        start();
    }

    @Override
    public void run() {
        try {
            Packet p = (Packet) is.readObject();
            login = p.getSender();
            String welcomeString = "Welcome, " + login + "!";
            welcomeMessage = new Packet("Server", login, welcomeString);
            send(welcomeMessage);

            while (true) {

                String tempStr = "";
                p = (Packet) is.readObject();
                if(p.getReceiver() != null){
                    tempStr = "@" + p.getReceiver() + ": ";
                }else tempStr = "";

                System.out.println(p.getSender() + ": " + tempStr + p.getMessage());



                if (p != null) {
                    if (p.getMessage().equals("exit")) {
                        server.removeClient(this);
                        os.close();
                        is.close();
                        sock.close();
                        break;
                    }
                    if(p.getReceiver() == null) {
                        server.notifyAllClients(p);
                    }else server.notifyConcretClient(p);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send(Packet p) {



        try {
            os.writeObject(p);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
