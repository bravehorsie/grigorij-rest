package info.grigoriadi.grigorij;

public interface ChapterService {
    /**
     * Gets whole chapter.
     * @param id id
     * @return chapter
     */
    String getChapterById(Long id);

    /**
     * Gets an image.
     * @param imageId image id
     * @return image
     */
    byte[] getImageById(Long imageId);
}
