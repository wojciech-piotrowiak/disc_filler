package eu.wojciechpiotrowiak;

import eu.wojciechpiotrowiak.filler.Filler;
import eu.wojciechpiotrowiak.filler.impl.DefaultFiller;
import eu.wojciechpiotrowiak.notifications.impl.ConsoleNotificator;

import java.io.IOException;

public class Main {

    private static Filler filler = new DefaultFiller(new ConsoleNotificator());

    public static void main(String[] args) {
        if (args.length == 0)
            return;
        try {
            final String directory = args[0];
            if (args.length == 1) {
                filler.fillDirectory(directory);
            } else if (args.length == 2) {
                final String length = args[1];
                filler.fillDirectoryWithDefinedLength(directory, Integer.valueOf(length));
            } else if (args.length == 3) {
                final String length = args[1];
                final String fileSize = args[2];
                filler.fillDirectoryWithDefinedLengthAndFileSize(directory, Integer.valueOf(length), Integer.valueOf(fileSize));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setFiller(Filler inputFiller) {
        filler = inputFiller;
    }
}
