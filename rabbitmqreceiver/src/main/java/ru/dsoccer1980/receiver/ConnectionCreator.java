package ru.dsoccer1980.receiver;

import com.rabbitmq.client.Channel;

public interface ConnectionCreator {

    Channel getChannel();

    String getQueueName();

}
