package info.grigoriadi.grigorij.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLogger implements CacheEventListener<String, Object> {

    private static Logger logger = LoggerFactory.getLogger(CacheLogger.class);

    @Override
    public void onEvent(CacheEvent<? extends String, ?> event) {
        logger.debug("EhCache event: {}, {}", event.getKey(), event.getType());
    }
}
