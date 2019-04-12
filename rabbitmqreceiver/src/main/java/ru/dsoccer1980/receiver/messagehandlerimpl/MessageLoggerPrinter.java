package ru.dsoccer1980.receiver.messagehandlerimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dsoccer1980.receiver.MessageHandler;


public class MessageLoggerPrinter implements MessageHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doAction(String message) {
        log.info("LOGG:" + message);
    }
}
