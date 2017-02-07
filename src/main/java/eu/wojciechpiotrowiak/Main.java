package eu.wojciechpiotrowiak;

import eu.wojciechpiotrowiak.filler.Filler;
import eu.wojciechpiotrowiak.filler.impl.DefaultFiller;
import eu.wojciechpiotrowiak.notifications.impl.ConsoleNotificator;

import java.io.IOException;

public class Main {

    public static final ConsoleNotificator NOTIFICATOR = new ConsoleNotificator();
    private static Filler filler = new DefaultFiller(NOTIFICATOR);

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("None of parameters defined. 1-directory, 2-Amount of bytes to fill, 3-File size");
            return;
        }
        try {
            final String directory = args[0];
            if (args.length == 1) {
                filler.fillDirectory(directory);
            } else if (args.length == 2) {
                filler.fillDirectoryWithDefinedLength(directory, getLength(args[1]));
            } else if (args.length == 3) {
                final String fileSize = args[2];
                filler.fillDirectoryWithDefinedLengthAndFileSize(directory, getLength(args[1]), Integer.valueOf(fileSize));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Did you entered letters in number parameter?");
            e.printStackTrace();
        } finally {
            NOTIFICATOR.stop();
        }
    }

    private static Long getLength(String length) {
        return Long.valueOf(length);
    }

    public static void setFiller(Filler inputFiller) {
        filler = inputFiller;
    }
}
