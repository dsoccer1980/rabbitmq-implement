package ru.dsoccer1980.sender;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractMessageSender implements MessageSender {

    protected final String exchangeName;
    private final String PROPERTY_LOCATION = "classpath:/application.properties";
    protected BuiltinExchangeType exchangeType;

    public AbstractMessageSender(String exchangeName) {
        this(exchangeName, BuiltinExchangeType.DIRECT);
    }

    public AbstractMessageSender(String exchangeName, BuiltinExchangeType exchangeType) {
        this.exchangeName = exchangeName;
        this.exchangeType = exchangeType;
    }

    public void sendMessage(String message) {
        sendMessage(message, "");
    }

    public void sendMessage(String message, String routingKey) {
        createChannelAndSendMessage(message, routingKey);
    }

    public void setExchangeType(BuiltinExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    private void createChannelAndSendMessage(String message, String routingKey) {
        ConnectionFactory factory = getConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, exchangeType);
            publishMessage(channel, message, routingKey);

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.load(PROPERTY_LOCATION);
        } catch (IOException e) {
            //default properties
        }
        return factory;
    }

    abstract void publishMessage(Channel channel, String message, String routingKey) throws IOException;


}
