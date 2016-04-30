package test;

import eu.wojciechpiotrowiak.Filler;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FillerTest {

    @Test
    public void testLessThanOneMB() throws IOException {
        int fileNumberBeforeFill = currentFileNumber();

        Filler filler = new Filler();
        filler.fillDirectoryWithDefinedLength(getTargetPath(), 10);

        Assert.assertEquals(++fileNumberBeforeFill, currentFileNumber());
    }

    @Test
    public void testBiggerThanOneMB() throws IOException {
        int fileNumberBeforeFill = currentFileNumber();

        Filler filler = new Filler();
        filler.fillDirectoryWithDefinedLength(getTargetPath(), 10_000_000);

        Assert.assertTrue(currentFileNumber() - ++fileNumberBeforeFill > 1);
    }

    @Test
    public void testNotADirectoryDefined() throws IOException {
        int fileNumberBeforeFill = currentFileNumber();

        Filler filler = new Filler();
        filler.fillDirectoryWithDefinedLength(getTestPath(), 10_000_000);

        Assert.assertEquals(fileNumberBeforeFill,currentFileNumber());
    }

    private int currentFileNumber() {
        File file = new File(getTargetPath());
        return file.listFiles().length;
    }

    private String getTargetPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString() + File.separator + "target";
    }

    private String getTestPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString() + File.separator + "src" + File.separator +
                "test" + File.separator + "test" + File.separator + "example.txt";
    }
}
