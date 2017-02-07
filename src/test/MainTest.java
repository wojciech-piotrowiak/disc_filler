

import eu.wojciechpiotrowiak.Main;
import eu.wojciechpiotrowiak.filler.Filler;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;


public class MainTest {
    private Filler filler = Mockito.mock(Filler.class);

    @Test
    public void noParametersTest() throws IOException {
        Main.setFiller(filler);
        Main.main(new String[]{});
        verifyNoMoreInteractions(filler);
    }

    @Test
    public void fillDirectoryTest() throws IOException {
        Main.setFiller(filler);
        Main.main(new String[]{"X:\\"});
        verify(filler).fillDirectory(anyString());
    }

    @Test
    public void fillWithDefinedLengthTest() throws IOException {
        Main.setFiller(filler);
        Main.main(new String[]{"X:\\", "22"});
        verify(filler).fillDirectoryWithDefinedLength(anyString(), anyLong());
    }

    @Test
    public void fillWithDefinedLengthAndFileSizeTest() throws IOException {
        Main.setFiller(filler);
        Main.main(new String[]{"X:\\", "22", "11"});
        verify(filler).fillDirectoryWithDefinedLengthAndFileSize(anyString(), anyLong(), anyInt());
    }

    @Test
    public void badParameterTest() throws IOException {
        Main.setFiller(filler);
        Main.main(new String[]{"X:\\", "A"});
        verifyZeroInteractions(filler);
        //verify(filler).fillDirectoryWithDefinedLength(anyString(), anyLong());
    }

    @Test
    public void badParametersTest() throws IOException {
        Main.setFiller(filler);
        Main.main(new String[]{"X:\\", "22", "CC"});
        verifyZeroInteractions(filler);//.fillDirectoryWithDefinedLengthAndFileSize(anyString(),anyLong(),anyInt());
    }
}
