package eu.wojciechpiotrowiak.notifications.impl;

import eu.wojciechpiotrowiak.notifications.Notificator;
import org.junit.Test;

public class ConsoleNotificatorTest {

    @Test
    public void step() throws Exception {
        Notificator notificator = new ConsoleNotificator();

        notificator.start(250);
        for (int i = 0; i < 250; i++) {
            notificator.step();
            waitSomeTime();
        }
        notificator.stop();
    }

    private void waitSomeTime() throws InterruptedException {
        Thread.currentThread().sleep(100);
    }
}