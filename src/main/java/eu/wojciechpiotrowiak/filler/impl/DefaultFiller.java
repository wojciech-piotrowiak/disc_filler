package eu.wojciechpiotrowiak.filler.impl;


import eu.wojciechpiotrowiak.filler.Filler;
import eu.wojciechpiotrowiak.notifications.Notificator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DefaultFiller implements Filler {
    private int DEFAULT_FILE_SIZE = 2_400_000;
    private Integer customFileSize;
    private Notificator notificator;

    public DefaultFiller(Notificator notificator) {
        this.notificator = notificator;
    }

    public void fillDirectory(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            Long freeSpace = file.getFreeSpace();
            writeToDisk(path, freeSpace.intValue());
        }
    }

    public void fillDirectoryWithDefinedLength(String path, Integer length) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            writeToDisk(path, length);
        }
    }

    @Override
    public void fillDirectoryWithDefinedLengthAndFileSize(String path, Integer length, Integer fileSize) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            setFileSize(fileSize);
            writeToDisk(path, length);
        }
    }

    private void writeToDisk(String directory, Integer sizeInBytes) throws IOException {
        //FAT32 has a limit of files (170) on base level, putting files into new catalog fix this problem
        String dedicatedCatalog = directory + File.separator + "fill";
        File newDir = new File(dedicatedCatalog);
        if (newDir.exists() || newDir.mkdir()) {
            directory = dedicatedCatalog;
        } else {
            return;
        }

        int fileNumber = (int) Math.ceil(sizeInBytes / getFileSize());
        notificator.start(fileNumber);
        while (sizeInBytes >= getFileSize()) {
            sizeInBytes -= getFileSize();
            saveBytesIntoFile(directory, getFileSize());

            notificator.step();
        }
        long freeSpace = new File(directory).getFreeSpace();
        int leftover = sizeInBytes;

        if (leftover > freeSpace) {
            leftover = (int) freeSpace;
        }
        if (leftover > 0) {
            saveBytesIntoFile(directory, leftover);
            notificator.step();
        }

        notificator.stop();
    }

    private void saveBytesIntoFile(String directory, int totalLength) throws IOException {
        String name = directory + File.separator + System.nanoTime();
        try (
                FileOutputStream outputStream = new FileOutputStream(name);
                BufferedOutputStream out = new BufferedOutputStream(outputStream)) {

            writeBufferIntoStream(out, totalLength);
            out.flush();
        }
    }

    private void writeBufferIntoStream(BufferedOutputStream out, int totalLength) throws IOException {
        byte[] buffer = new byte[totalLength];
        out.write(buffer, 0, totalLength);
    }

    public Integer getFileSize() {
        if (customFileSize == null) {
            customFileSize = DEFAULT_FILE_SIZE;
        }
        return customFileSize;
    }

    public void setFileSize(Integer customFileSize) {
        this.customFileSize = customFileSize;
    }
}
