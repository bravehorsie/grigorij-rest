package info.grigoriadi.grigorij.transform;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import info.grigoriadi.grigorij.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ChapterServiceImpl implements ChapterService {

    private AmazonS3 s3client;

    private static final String BUCKET_NAME = "grigorij";
    public static final String CHAPTERS_PATH = "chapters";
    public static final String FILE_SUFFIX = "html";

    @Override
    public String getChapterById(Long id) {
        final String objectName = CHAPTERS_PATH + "/" + id + "." + FILE_SUFFIX;
        if (!s3client.doesObjectExist(BUCKET_NAME, objectName)) {
            throw new IllegalStateException("Object [" + id + "] does not exist!");
        }
        S3Object result = s3client.getObject(BUCKET_NAME, objectName);
        try {
            return new String(result.getObjectContent().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Cant read response bytes, e");
        }
    }

    @Autowired
    public void setS3client(AmazonS3 s3client) {
        this.s3client = s3client;
    }
}
