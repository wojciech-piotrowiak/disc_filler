package eu.wojciechpiotrowiak;


import me.tongfei.progressbar.ProgressBar;

import java.io.*;

public class Filler {
    public void fillDirectory(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            long freeSpace = file.getFreeSpace();
            writeToDisk(path, freeSpace);
        }
    }

    public void fillDirectoryWithDefinedLength(String path, long length) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            writeToDisk(path, length);
        }
    }

    private void writeToDisk(String directory, long sizeInBytes) throws IOException {
        //FAT32 has a limit of files (170) on base level, putting files into new catalog fix this problem
        String dedicatedCatalog = directory + File.separator + "fill";
        File newDir = new File(dedicatedCatalog);
        if (newDir.mkdir()) {
            directory = dedicatedCatalog;
        } else {
            return;
        }

        ProgressBar pb = new ProgressBar("Test", 100);
        pb.start();
        int oneMB = 1_000_000;
        while (sizeInBytes > oneMB) {
            sizeInBytes -= oneMB;
            saveBytesIntoFile(directory, oneMB);
            pb.step();
        }
        int leftover = (int) (oneMB - sizeInBytes);
        if (leftover > 0) {
            saveBytesIntoFile(directory, leftover);
        }
        pb.stop();
    }

    private void saveBytesIntoFile(String directory, int oneMB) throws IOException {
        String name = directory + File.separator + System.nanoTime();
        try (
                FileOutputStream outputStream = new FileOutputStream(name);
                BufferedOutputStream out = new BufferedOutputStream(outputStream)) {

            writeBufferIntoStream(oneMB, out);
            out.flush();
        }
    }

    private void writeBufferIntoStream(int length, BufferedOutputStream out) throws IOException {
        byte[] buffer = new byte[length];
        for (int i = 0; i < length; i++) {
            buffer[i] = 1;
        }
        out.write(buffer, 0, length);
    }
}
