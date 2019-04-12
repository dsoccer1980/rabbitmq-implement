package ru.dsoccer1980.receiver;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ConnectionCreatorImpl implements ConnectionCreator {

    protected final String exchangeName;
    private final String PROPERTY_LOCATION = "classpath:/application.properties";
    protected BuiltinExchangeType exchangeType;
    protected List<String> routingKeys;
    private String queueName;

    public ConnectionCreatorImpl(String exchangeName) {
        this(exchangeName, BuiltinExchangeType.DIRECT);
    }

    public ConnectionCreatorImpl(String exchangeName, BuiltinExchangeType exchangeType) {
        this(exchangeName, exchangeType, "");
    }

    public ConnectionCreatorImpl(String exchangeName, BuiltinExchangeType exchangeType, List<String> routingKeys) {
        this.exchangeName = exchangeName;
        this.routingKeys = routingKeys;
        this.exchangeType = exchangeType;
    }

    public ConnectionCreatorImpl(String exchangeName, BuiltinExchangeType exchangeType, String... routingKeys) {
        this(exchangeName, exchangeType, Arrays.asList(routingKeys));
    }

    public Channel getChannel() {
        try {
            ConnectionFactory factory = getConnectionFactory();
            Connection connection = factory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, exchangeType);
            setQueueName(channel.queueDeclare().getQueue());
            for (String keys : routingKeys) {
                channel.queueBind(getQueueName(), exchangeName, keys);
            }
            return channel;
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setExchangeType(BuiltinExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getQueueName() {
        return queueName;
    }

    private void setQueueName(String queueName) {
        this.queueName = queueName;
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
}




