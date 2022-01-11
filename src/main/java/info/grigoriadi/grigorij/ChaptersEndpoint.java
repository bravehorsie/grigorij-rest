package info.grigoriadi.grigorij;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.grigoriadi.grigorij.pojo.Chapter;
import info.grigoriadi.grigorij.pojo.PoemChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChaptersEndpoint {

    private ChapterService chapterService;

    private ObjectMapper mapper;

    public ChaptersEndpoint() {
        mapper = new ObjectMapper();
    }

    @GetMapping(value = "/chapters/{id}", produces = "application/json")
    public ResponseEntity<Chapter> getChapter(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    mapper.readValue(chapterService.getChapterById(id), Chapter.class)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing json", e);
        }
    }

    @GetMapping(value = "/poems/{id}", produces = "application/json")
    public ResponseEntity<PoemChapter> getPoem(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    mapper.readValue(chapterService.getPoemById(id), PoemChapter.class)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing json", e);
        }
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        return ResponseEntity.ok(chapterService.getImageById(id));
    }


    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

}
