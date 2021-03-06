package info.grigoriadi.grigorij.pojo;

import java.util.ArrayList;
import java.util.List;

public class Poem {

    private String name;

    private List<Verse> verses = new ArrayList<>();

    public List<Verse> getVerses() {
        return verses;
    }

    public void setVerses(List<Verse> verses) {
        this.verses = verses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
