package eu.wojciechpiotrowiak.notifications.impl;

import eu.wojciechpiotrowiak.notifications.Notificator;
import me.tongfei.progressbar.ProgressBar;

public class ConsoleNotificator implements Notificator {
    private ProgressBar progressBar;

    @Override
    public void start(int steps) {
        progressBar = new ProgressBar("Filling data", steps);
        progressBar.start();
    }

    @Override
    public void step() {
        progressBar.step();
    }

    @Override
    public void stop() {
        progressBar.stop();
    }
}
