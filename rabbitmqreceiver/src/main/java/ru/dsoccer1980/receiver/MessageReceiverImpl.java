package ru.dsoccer1980.receiver;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageReceiverImpl implements MessageReceiver {
    private final ConnectionCreator connectionCreator;
    private MessageHandler messageHandler;

    public MessageReceiverImpl(ConnectionCreator connectionCreator) {
        this(connectionCreator, null);
    }

    public MessageReceiverImpl(ConnectionCreator connectionCreator, MessageHandler messageHandler) {
        this.connectionCreator = connectionCreator;
        this.messageHandler = messageHandler;
    }

    public void receive() {
        try {
            DeliverCallback deliverCallback =
                    (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                        if (messageHandler != null) {
                            messageHandler.doAction(message);
                        }
                    };
            Channel channel = connectionCreator.getChannel();
            channel.basicConsume(connectionCreator.getQueueName(), true, deliverCallback, consumerTag -> {
            });

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}
