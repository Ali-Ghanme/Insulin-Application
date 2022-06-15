package com.example.diabestes_care_app.Models;

public class MessagesList_Model {
    private String name, username, lastMessage, DoctorImage, ChatKey;
    private int unseenMessages;

    public MessagesList_Model(String name, String username, String lastMessage, String doctorImage, String ChatKey
            , int unseenMessages) {
        this.name = name;
        this.username = username;
        this.lastMessage = lastMessage;
        DoctorImage = doctorImage;
        this.unseenMessages = unseenMessages;
        this.ChatKey = ChatKey;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDoctorImage() {
        return DoctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        DoctorImage = doctorImage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }

    public String getChatKey() {
        return ChatKey;
    }

    public void setChatKey(String chat) {
        this.ChatKey = chat;
    }
}