package eu.wojciechpiotrowiak.filler;


import java.io.IOException;

public interface Filler {
    void fillDirectory(String path) throws IOException;

    void fillDirectoryWithDefinedLength(String path, long length) throws IOException;

    void fillDirectoryWithDefinedLengthAndFileSize(String directory, long length, Integer fileSize) throws IOException;
}

