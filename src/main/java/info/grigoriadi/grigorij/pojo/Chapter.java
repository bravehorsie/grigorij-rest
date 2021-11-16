package info.grigoriadi.grigorij.pojo;

import java.util.List;

public class Chapter {

    private List<Section> sections;

    private List<Image> images;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
