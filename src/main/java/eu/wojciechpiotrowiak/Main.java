package eu.wojciechpiotrowiak;

import org.apache.commons.lang3.time.StopWatch;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0)
            return;

        try {
            StopWatch stopWatch=new StopWatch();
            stopWatch.start();
            Filler filler = new Filler();
            final String directory = args[0];
            if (args.length == 1) {
                filler.fillDirectory(directory);
            } else if (args.length == 2) {
                final String length = args[1];
                filler.fillDirectoryWithDefinedLength(directory, Long.valueOf(length));
            }
            stopWatch.stop();
            System.out.println(String.format("Filling took %d ms",stopWatch.getTime()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
