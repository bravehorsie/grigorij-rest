package info.grigoriadi.grigorij;

import info.grigoriadi.grigorij.transform.ChapterTransformer;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

@SpringBootTest
public class ChapterServiceTest {

    private ChapterTransformer chapterTransformer;

    /**
     * Run this only for chapter regeneration.
     */
    @Ignore
    @Test
    public void testGenerateContent() {
        LongStream.range(0, 9).forEach(id->chapterTransformer.saveChapter(id));
    }

    @Autowired
    public void setChapterTransformer(ChapterTransformer chapterTransformer) {
        this.chapterTransformer = chapterTransformer;
    }
}
