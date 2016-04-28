package eu.wojciechpiotrowiak;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0)
            return;

        try {
            Filler filler = new Filler();
            final String directory = args[1];
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
