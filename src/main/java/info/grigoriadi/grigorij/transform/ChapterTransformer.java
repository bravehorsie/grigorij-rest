package info.grigoriadi.grigorij.transform;

/**
 * Transforms old HTML chapters into json.
 */
public interface ChapterTransformer {
    /**
     * Loads a chapter and saves as JSON.
     * @param id id
     */
    void saveChapter(long id);
}
