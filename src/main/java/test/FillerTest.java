package test;

import eu.wojciechpiotrowiak.Filler;
import eu.wojciechpiotrowiak.notifications.Notificator;
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

        Filler filler = new Filler(new TestNotificator());
        filler.fillDirectoryWithDefinedLength(getTargetPath(), 10);

        Assert.assertEquals(++fileNumberBeforeFill, currentFileNumber());
    }

    @Test
    public void testBiggerThanOneMB() throws IOException {
        int fileNumberBeforeFill = currentFileNumber();

        Filler filler = new Filler(new TestNotificator());
        filler.fillDirectoryWithDefinedLength(getTargetPath(), 10_000_000);

        Assert.assertTrue(currentFileNumber() - ++fileNumberBeforeFill > 1);
    }

    @Test
    public void testNotADirectoryDefined() throws IOException {
        int fileNumberBeforeFill = currentFileNumber();

        Filler filler = new Filler(new TestNotificator());
        filler.fillDirectoryWithDefinedLength(getTestPath(), 10_000_000);

        Assert.assertEquals(fileNumberBeforeFill, currentFileNumber());
    }

    @Test
    public void partsNumberTestWithSmallFile() throws IOException
    {
        //less than normal file size
        TestNotificator notificator = new TestNotificator();
        Filler filler = new Filler(notificator);
        filler.fillDirectoryWithDefinedLength(getTargetPath(), 2_399_00);

       Assert.assertEquals(0,notificator.stepsDeclared);
       Assert.assertEquals(1,notificator.stepsMarked);
    }

    @Test
    public void partsNumberTestWithFileEqualToDefault() throws IOException
    {
        //less than normal file size
        TestNotificator notificator = new TestNotificator();
        Filler filler = new Filler(notificator);
        filler.fillDirectoryWithDefinedLength(getTargetPath(), 2_400_000);

        Assert.assertEquals(1,notificator.stepsDeclared);
        Assert.assertEquals(1,notificator.stepsMarked);
    }

    private int currentFileNumber() {
        File file = new File(getTargetPath() + File.separator + "fill");
        if (!file.exists()) {
            //clean instalation might not have 'fill' dir for the first test. This catalog will be created by program
            file.mkdir();
        }
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

    private class TestNotificator implements Notificator {

        int stepsDeclared;
        int stepsMarked = 0;

        @Override
        public void start(int steps) {
            this.stepsDeclared = steps;
        }

        @Override
        public void step() {
            stepsMarked += 1;
        }

        @Override
        public void stop() {

        }
    }
}
