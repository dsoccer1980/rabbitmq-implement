package ru.dsoccer1980;

import com.rabbitmq.client.BuiltinExchangeType;
import ru.dsoccer1980.sender.MessageSender;
import ru.dsoccer1980.sender.MessageSenderImpl;

import java.time.LocalDateTime;

public class SenderClient {

    public static void main(String[] args) {
        MessageSender messageSender = new MessageSenderImpl("example", BuiltinExchangeType.DIRECT);
        messageSender.sendMessage(LocalDateTime.now().toString(), "info");
    }
}


