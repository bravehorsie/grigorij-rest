package info.grigoriadi.grigorij;

import info.grigoriadi.grigorij.transform.ChapterTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.LongStream;

@SpringBootTest
public class ChapterServiceTest {

    private ChapterTransformer chapterTransformer;

    @Test
    public void testGenerateContent() {
        LongStream.range(1, 9).forEach(id->chapterTransformer.saveChapter(id));
    }

    @Autowired
    public void setChapterTransformer(ChapterTransformer chapterTransformer) {
        this.chapterTransformer = chapterTransformer;
    }
}
