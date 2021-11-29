package info.grigoriadi.grigorij.transform;

import info.grigoriadi.grigorij.pojo.Poem;
import info.grigoriadi.grigorij.pojo.PoemChapter;
import info.grigoriadi.grigorij.pojo.Verse;

public class PoemContext {

    public static final String POEM_NAME = "poem-name:";
    public static final String NAME = "name:";

    private final PoemChapter chapter;
    private Poem poem;
    private Verse currentVerse = new Verse();

    public PoemContext() {
        this.chapter = new PoemChapter();
    }

    public void readLine(String line) {
        if (line.startsWith(POEM_NAME)) {
            chapter.setName(line.substring(line.indexOf(POEM_NAME) + POEM_NAME.length()).trim());
        } else if (line.startsWith(NAME)) {
            String poemName = line.substring(line.indexOf(NAME) + NAME.length()).trim();
            if (poem != null) {
                chapter.getPoems().add(poem);
            }
            poem = new Poem();
            poem.setName(poemName);
        } else {
            if (line.isBlank() && currentVerse.getLines().size() > 0) {
                poem.getVerses().add(currentVerse);
                currentVerse = new Verse();
            } else if (!line.isBlank()) {
                currentVerse.getLines().add(line);
            }
        }
    }

    public PoemChapter getChapter() {
        return chapter;
    }
}
