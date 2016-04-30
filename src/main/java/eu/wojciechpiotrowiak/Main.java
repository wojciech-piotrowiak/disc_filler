package eu.wojciechpiotrowiak;

import eu.wojciechpiotrowiak.notifications.Notificator;
import eu.wojciechpiotrowiak.notifications.impl.ConsoleNotificator;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0)
            return;
        try {
            Notificator notificator=new ConsoleNotificator();
            Filler filler = new Filler(notificator);
            final String directory = args[0];
            if (args.length == 1) {
                filler.fillDirectory(directory);
            } else if (args.length == 2) {
                final String length = args[1];
                filler.fillDirectoryWithDefinedLength(directory, Long.valueOf(length));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
