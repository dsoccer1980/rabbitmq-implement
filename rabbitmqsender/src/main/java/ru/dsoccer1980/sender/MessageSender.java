package ru.dsoccer1980.sender;

import com.rabbitmq.client.BuiltinExchangeType;

public interface MessageSender {

    void sendMessage(String message);

    void sendMessage(String message, String route);

    void setExchangeType(BuiltinExchangeType type);
}
