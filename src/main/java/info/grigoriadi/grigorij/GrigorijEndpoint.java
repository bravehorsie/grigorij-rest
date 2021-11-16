package info.grigoriadi.grigorij;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrigorijEndpoint {

    private ChapterService chapterService;

    @GetMapping(value = "/chapters/{id}", produces = "application/json")
    public ResponseEntity<Result> getChapter(@PathVariable Long id) {
        Result response = new Result();
        response.setContent(chapterService.getChapterById(id));
        return ResponseEntity.ok(response);
    }

    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

}
