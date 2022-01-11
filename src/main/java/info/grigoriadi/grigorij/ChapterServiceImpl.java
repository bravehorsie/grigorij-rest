package info.grigoriadi.grigorij;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ChapterServiceImpl implements ChapterService {

    private AmazonS3 s3client;

    private static final String BUCKET_NAME = "grigorij";
    public static final String CHAPTERS_PATH = "chapters/json";
    public static final String POEMS_PATH = "poem";
    public static final String IMAGES_PATH = "images";
    public static final String JSON_SUFFIX = "json";

    private static Logger logger = LoggerFactory.getLogger(ChapterServiceImpl.class);

    @Override
    @Cacheable(value = "chapterCache", key = "#id")
    public String getChapterById(Long id) {
        logger.debug("Getting chapter id [{}]", id);
        final String objectName = CHAPTERS_PATH + "/" + id + "." + JSON_SUFFIX;
        return getS3String(objectName);
    }

    @Override
    @Cacheable(value = "poemCache", key = "#id")
    public String getPoemById(Long id) {
        logger.debug("Getting poem id [{}]", id);
        final String objectName = POEMS_PATH + "/" + id + "." + JSON_SUFFIX;
        return getS3String(objectName);
    }

    private String getS3String(String objectName) {
        if (!s3client.doesObjectExist(BUCKET_NAME, objectName)) {
            throw new IllegalStateException("Object [" + objectName + "] does not exist!");
        }
        S3Object result = s3client.getObject(BUCKET_NAME, objectName);
        try {
            return new String(result.getObjectContent().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Cant read response bytes, e");
        }
    }

    @Override
    @Cacheable(value = "imageCache", key = "#name")
    public byte[] getImageById(String name) {
        logger.debug("Getting image name [{}]", name);
        final String objectName = IMAGES_PATH + "/" + name;
        if (!s3client.doesObjectExist(BUCKET_NAME, objectName)) {
            throw new IllegalStateException("Object [" + objectName + "] does not exist!");
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
