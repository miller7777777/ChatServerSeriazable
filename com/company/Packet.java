package com.company;

import java.io.Serializable;

/**
 * Created by vladimir on 22.05.15.
 */
public class Packet implements Serializable {

    private static final long serialVersionUID = 1234567890;
    private String sender;
    private String receiver;
    private String message;


    public Packet(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }
}
