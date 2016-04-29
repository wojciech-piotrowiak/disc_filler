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
        File file=new File(getAbsolutePath());
        int fileNumberBeforeFill = file.listFiles().length;

        Filler filler =new Filler();
        filler.fillDirectoryWithDefinedLength(getAbsolutePath(),10);

        int fileNumberAfterFill = file.listFiles().length;
        Assert.assertEquals(++fileNumberBeforeFill,fileNumberAfterFill);
    }

    @Test
    public void testBiggerThanOneMB() throws IOException {
        File file=new File(getAbsolutePath());
        int fileNumberBeforeFill = file.listFiles().length;

        Filler filler =new Filler();
        filler.fillDirectoryWithDefinedLength(getAbsolutePath(),10_000_000);

        int fileNumberAfterFill = file.listFiles().length;
        Assert.assertTrue(fileNumberAfterFill-++fileNumberBeforeFill>1);
    }

    private String getAbsolutePath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }
}
