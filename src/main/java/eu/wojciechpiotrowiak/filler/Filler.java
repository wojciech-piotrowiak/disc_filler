package eu.wojciechpiotrowiak.filler;


import java.io.IOException;

public interface Filler {
    void fillDirectory(String path) throws IOException;

    void fillDirectoryWithDefinedLength(String path, long length) throws IOException;
}

