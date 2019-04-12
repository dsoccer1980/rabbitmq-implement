package ru.dsoccer1980.sender;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MessageSenderImpl extends AbstractMessageSender {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public MessageSenderImpl(String exchangeName) {
        this(exchangeName, BuiltinExchangeType.DIRECT);
    }

    public MessageSenderImpl(String exchangeName, BuiltinExchangeType exchangeType) {
        super(exchangeName, exchangeType);
    }

    void publishMessage(Channel channel, String message, String routingKey) throws IOException {
        channel.basicPublish(exchangeName, routingKey, true, null, message.getBytes(StandardCharsets.UTF_8));
        log.info("Sent message: " + message);
    }


}
