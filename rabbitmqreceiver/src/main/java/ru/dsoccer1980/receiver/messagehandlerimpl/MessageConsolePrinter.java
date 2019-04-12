package ru.dsoccer1980.receiver.messagehandlerimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dsoccer1980.receiver.MessageHandler;


public class MessageConsolePrinter implements MessageHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doAction(String message) {
        System.out.println(message);
    }
}
