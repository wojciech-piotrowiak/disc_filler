package eu.wojciechpiotrowiak.notifications;


public interface Notificator {
     void start(int steps);
     void step();
     void stop();
}
