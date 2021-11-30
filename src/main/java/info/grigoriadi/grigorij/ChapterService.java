package info.grigoriadi.grigorij;

public interface ChapterService {
    /**
     * Gets whole chapter.
     * @param id id
     * @return chapter
     */
    String getChapterById(Long id);

    /**
     * Gets a poem.
     * @param id id of poem
     * @return poem
     */
    String getPoemById(Long id);

    /**
     * Gets an image.
     * @param name image name
     * @return image
     */
    byte[] getImageById(String name);
}
