package info.grigoriadi.grigorij;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ChapterServiceImpl implements ChapterService {

    private AmazonS3 s3client;

    private static final String BUCKET_NAME = "grigorij";
    public static final String CHAPTERS_PATH = "chapters/json";
    public static final String IMAGES_PATH = "images";
    public static final String CHAPTER_SUFFIX = "json";
    public static final String IMAGE_SUFFIX = "json";

    @Override
    public String getChapterById(Long id) {
        final String objectName = CHAPTERS_PATH + "/" + id + "." + CHAPTER_SUFFIX;
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

    @Override
    public byte[] getImageById(Long id) {
        final String objectName = IMAGES_PATH + "/" + id + "." + IMAGE_SUFFIX;
        if (!s3client.doesObjectExist(BUCKET_NAME, objectName)) {
            throw new IllegalStateException("Object [" + id + "] does not exist!");
        }
        S3Object result = s3client.getObject(BUCKET_NAME, objectName);
        try {
            return result.getObjectContent().readAllBytes();
        } catch (IOException e) {
            throw new IllegalStateException("Cant read response bytes, e");
        }
    }

    @Autowired
    public void setS3client(AmazonS3 s3client) {
        this.s3client = s3client;
    }
}
