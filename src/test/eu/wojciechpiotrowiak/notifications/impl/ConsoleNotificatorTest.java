package eu.wojciechpiotrowiak.notifications.impl;

import eu.wojciechpiotrowiak.notifications.Notificator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.listeners.NotifiedMethodInvocationReport;

import static org.junit.Assert.*;

public class ConsoleNotificatorTest {

    private Notificator notificator = new ConsoleNotificator();
    @Before
    public void before() throws Exception {
        notificator.start(250);
    }

    @Test
    public void step() throws Exception {
        for (int i = 0; i < 250; i++) {
            notificator.step();
            Thread.sleep(100);
        }
        Thread.sleep(1000);
    }

    @Test
    public void stop() throws Exception {
        notificator.stop();
    }

}