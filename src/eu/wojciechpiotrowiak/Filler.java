package eu.wojciechpiotrowiak;


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
        writeToDisk(path, length);
    }

    private void writeToDisk(String directory, long sizeInBytes) throws IOException {
        while(sizeInBytes>1_000_000) {
            String name = directory + File.separator + System.nanoTime();
            try (
                    FileOutputStream outputStream = new FileOutputStream(name);
                    BufferedOutputStream out = new BufferedOutputStream(outputStream)) {
                int oneMB = 1_000_000;
                sizeInBytes-=oneMB;
                byte[] buffer = new byte[oneMB];
                for (int i = 0; i < oneMB; i++) {
                    buffer[i] = 1;
                }
                out.write(buffer, 0, oneMB);
                out.flush();
            }
        }

    }
}
