package eu.wojciechpiotrowiak;


import eu.wojciechpiotrowiak.notifications.Notificator;
import me.tongfei.progressbar.ProgressBar;

import java.io.*;

public class Filler {
    private int FILE_SIZE = 2_400_000;
    private Notificator notificator;

    public Filler(Notificator notificator) {
        this.notificator=notificator;
    }

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
        if (newDir.exists() || newDir.mkdir()) {
            directory = dedicatedCatalog;
        } else {
            return;
        }

        int fileNumber = (int)Math.ceil(sizeInBytes/FILE_SIZE);
        notificator.start(fileNumber);
        while (sizeInBytes > FILE_SIZE) {
            sizeInBytes -= FILE_SIZE;
            saveBytesIntoFile(directory, FILE_SIZE);

            notificator.step();
        }
        long freeSpace = new File(directory).getFreeSpace();
        int leftover = (int) (sizeInBytes);

        if (leftover > freeSpace) {
            leftover = (int) freeSpace;
        }
        saveBytesIntoFile(directory, leftover);
        notificator.step();
        notificator.stop();
    }

    private void saveBytesIntoFile(String directory, int totalLength) throws IOException {
        String name = directory + File.separator + System.nanoTime();
        try (
                FileOutputStream outputStream = new FileOutputStream(name);
                BufferedOutputStream out = new BufferedOutputStream(outputStream)) {

            writeBufferIntoStream(totalLength, out);
            out.flush();
        }
    }

    private void writeBufferIntoStream(int totalLength, BufferedOutputStream out) throws IOException {
        byte[] buffer = new byte[totalLength];
        out.write(buffer, 0, totalLength);
    }
}
