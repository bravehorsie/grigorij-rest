package info.grigoriadi.grigorij.pojo;

import java.util.ArrayList;
import java.util.List;

public class PoemChapter {

    private String name;
    private List<Poem> poems = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Poem> getPoems() {
        return poems;
    }

    public void setPoems(List<Poem> poems) {
        this.poems = poems;
    }
}
