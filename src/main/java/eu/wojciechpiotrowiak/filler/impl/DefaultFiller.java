package eu.wojciechpiotrowiak.filler.impl;


import eu.wojciechpiotrowiak.filler.Filler;
import eu.wojciechpiotrowiak.notifications.Notificator;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
            writeToDisk(path, file.getFreeSpace());
        }
    }

    public void fillDirectoryWithDefinedLength(String path, long length) throws IOException {
        if (length <= 0) {
            throw new IOException("Content length should be positive number!");
        }
        File file = new File(path);
        if (file.isDirectory()) {
            writeToDisk(path, length);
        }
    }

    @Override
    public void fillDirectoryWithDefinedLengthAndFileSize(String path, long length, Integer fileSize) throws IOException {
        if (length <= 0 || fileSize <= 0) {
            throw new IOException("Content length and file size should be positive number!");
        }
        File file = new File(path);
        if (file.isDirectory()) {
            setFileSize(fileSize);
            writeToDisk(path, length);
        }
    }

    private void writeToDisk(final String directory, final long requestedSpaceToFill) throws IOException {
        final String dedicatedCatalog = getFinalPath(directory);

        int fileNumber = (int) Math.ceil(requestedSpaceToFill / getFileSize());
        if (fileNumber == 0) fileNumber = 1;
        notificator.start(fileNumber);

        for (int c = 0; c < fileNumber; c++) {
            saveBytesIntoFile(dedicatedCatalog, getFileSize());
            notificator.step();
        }

        long freeSpace = new File(directory).getFreeSpace();
        long leftover = requestedSpaceToFill - (getFileSize() * fileNumber);

        if (leftover > freeSpace) {
            leftover = freeSpace;
        }
        //to avoid negative number when casting value below
        if (leftover > 0 && leftover < Integer.MAX_VALUE) {
            saveBytesIntoFile(dedicatedCatalog, (int) leftover);
            notificator.step();
        }

        notificator.stop();
    }

    private void saveBytesIntoFile(String directory, int totalLength) {
        Path name = Paths.get(directory, System.nanoTime() + "");
        try (
                FileOutputStream outputStream = new FileOutputStream(name.toString());
                BufferedOutputStream out = new BufferedOutputStream(outputStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(totalLength)) {
            byte[] b = new byte[totalLength];
            Arrays.fill(b, (byte) 0b1);
            byteArrayOutputStream.write(b);
            byteArrayOutputStream.writeTo(out);
            //out.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            System.out.print("Could not create content files");
        }
    }

    private String getFinalPath(String initialPath) throws IOException {
        //FAT32 has a limit of files (170) on base level, putting files into new catalog fix this problem
        Path dedicatedCatalog = Paths.get(initialPath, "fill");
        File newDir = new File(dedicatedCatalog.toString());
        if (newDir.exists() || newDir.mkdir()) {
            return dedicatedCatalog.toString();
        }
        throw new IOException("Could not resolve or create result catalog");
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
