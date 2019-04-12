package ru.dsoccer1980;


import com.rabbitmq.client.BuiltinExchangeType;
import ru.dsoccer1980.receiver.ConnectionCreator;
import ru.dsoccer1980.receiver.ConnectionCreatorImpl;
import ru.dsoccer1980.receiver.MessageReceiverImpl;
import ru.dsoccer1980.receiver.messagehandlerimpl.MessageConsolePrinter;

public class ReceiverClient {

    public static void main(String[] args) {
        ConnectionCreator connectionCreator = new ConnectionCreatorImpl("example", BuiltinExchangeType.DIRECT, "info");

        MessageReceiverImpl receiver = new MessageReceiverImpl(connectionCreator, new MessageConsolePrinter());
        receiver.receive();
    }
}


