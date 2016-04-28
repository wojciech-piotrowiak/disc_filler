import eu.wojciechpiotrowiak.Filler;
import org.junit.Test;

import java.io.IOException;


public class FillerTest {

    @Test
    public void test() throws IOException {
        Filler filler =new Filler();
        filler.fillDirectoryWithDefinedLength("C:\\test",31_000_000);
    }
}
