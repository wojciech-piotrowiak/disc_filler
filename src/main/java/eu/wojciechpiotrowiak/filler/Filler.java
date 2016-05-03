package eu.wojciechpiotrowiak.filler;


import java.io.IOException;

public interface Filler {
    void fillDirectory(String path) throws IOException;

    void fillDirectoryWithDefinedLength(String path, Integer length) throws IOException;

    void fillDirectoryWithDefinedLengthAndFileSize(String directory, Integer length, Integer fileSize) throws IOException;
}

