package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by vladimir on 19.05.15.
 */
public class ChatServer {
    private static final int PORT = 7000;
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;

    public void acceptClients() {
        clients = new ArrayList<Client>();
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket, this);
                clients.add(client);
                System.out.println("New client has been connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyAllClients(Packet p) {
        System.out.println("Notify all clients with message: " + p.getMessage());
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).send(p);
        }
        System.out.println("Server: send packet to all: " + p.getMessage());

    }



    public void removeClient(Client c) {
        if (clients.remove(c)) {
            System.out.println("Client has been disconnected");
        }
    }

    public void notifyConcretClient(Packet p) {



        System.out.println("Notify client " + p.getReceiver() + " with message: " + p.getMessage());
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getLogin().equals(p.getReceiver())) {

                System.out.println("Server: send to client: " + p. getReceiver() + " packet: " + p.getMessage());

                clients.get(i).send(p);
            }
        }
    }
}
