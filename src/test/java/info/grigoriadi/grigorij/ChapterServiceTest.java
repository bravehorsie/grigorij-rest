package info.grigoriadi.grigorij;

import info.grigoriadi.grigorij.transform.ChapterTransformer;
import info.grigoriadi.grigorij.transform.PoemTransformer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

@SpringBootTest
public class ChapterServiceTest {

    private ChapterTransformer chapterTransformer;

    private PoemTransformer poemTransformer;

    /**
     * Run this only for chapter regeneration.
     */
    @Test
    @Disabled("Disabled by default")
    public void testGenerateContent() {
        LongStream.range(0, 9).forEach(id->chapterTransformer.saveChapter(id));
    }

    @Test
    @Disabled("Disabled by default")
    public void testGeneratePoems() {
        poemTransformer.transformPoem("1-moej-bessonicy-druzja");
        poemTransformer.transformPoem("2-vojna");
        poemTransformer.transformPoem("3-vremeni-v-obrez");
        poemTransformer.transformPoem("4-chernoviki");
    }

    @Autowired
    public void setChapterTransformer(ChapterTransformer chapterTransformer) {
        this.chapterTransformer = chapterTransformer;
    }

    @Autowired
    public void setPoemTransformer(PoemTransformer poemTransformer) {
        this.poemTransformer = poemTransformer;
    }
}
