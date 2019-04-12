package ru.dsoccer1980.receiver;

public interface MessageReceiver {

    void receive();

    void setMessageHandler(MessageHandler messageHandler);

}
