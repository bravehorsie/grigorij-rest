package info.grigoriadi.grigorij.pojo;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private String heading;

    private List<Paragraph> paragraphs;

    public Section() {
        paragraphs = new ArrayList<>();
    }

    public Section(String heading) {
        this.heading = heading;
        this.paragraphs = new ArrayList<>();
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
