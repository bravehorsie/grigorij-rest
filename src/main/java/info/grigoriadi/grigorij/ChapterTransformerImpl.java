package info.grigoriadi.grigorij;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.grigoriadi.grigorij.pojo.*;
import info.grigoriadi.grigorij.transform.ChapterTransformer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterTransformerImpl implements ChapterTransformer {

    private ChapterService chapterService;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void saveChapter(long id)  {
        String content = chapterService.getChapterById(id);

        Chapter chapter = new Chapter();
        chapter.setSections(new ArrayList<>());
        chapter.setImages(new ArrayList<>());
        List<Paragraph> paragraphs = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements body = doc.select("body");

        Elements contentChildren = body.get(0).children();
        Element firstChild = contentChildren.get(0);
        if ("div".equals(firstChild.nodeName()) && "content".equals(firstChild.id())) {
            contentChildren = firstChild.children();
        }
        contentChildren.forEach(n->{
            if ("h3".equals(n.nodeName()) || "h2".equals(n.nodeName()) || "h4".equals(n.nodeName())) {
                if (paragraphs.size() != 0) {
                    chapter.getSections().get(chapter.getSections().size() - 1).getParagraphs().addAll(paragraphs);
                    paragraphs.clear();
                }
                Section section = new Section(n.text());
                section.setParagraphs(new ArrayList<>());
                chapter.getSections().add(section);
            } else if ("p".equals(n.nodeName())) {
                Paragraph paragraph = new Paragraph(n.text());
                if ("newparagraph".equals(n.className())) {
                    paragraph.setIndent(true);
                }
                //TODO image link
                paragraphs.add(paragraph);
            } else if ("div".equals(n.nodeName()) && "stix".equals(n.attr("class"))) {
                Paragraph poemParagraph = new Paragraph();
                poemParagraph.setPoem(parsePoem(n));
                paragraphs.add(poemParagraph);
            } else if ("div".equals(n.nodeName()) && "photos".equals(n.attr("class"))) {
                System.out.println("Ignoring photos div");
            } else {
                throw new IllegalStateException("Unexpected element: " + n.nodeName());
            }
        });

        chapter.getSections().get(chapter.getSections().size() - 1).getParagraphs().addAll(paragraphs);

        //get images
        body.select("a.fancybox").forEach(n->{
            Image image = new Image();
            String src = n.attr("href");
            image.setName(src.substring(src.lastIndexOf("/") + 1));
            image.setDescription(n.attr("title"));
            chapter.getImages().add(image);
        });

        File file = new File("./chapter-" + id + ".json");
        try {
            mapper.writeValue(file, chapter);
        } catch (IOException e) {
            throw new RuntimeException("Can't write file", e);
        }

    }

    private Poem parsePoem(Element node) {
        Poem poem = new Poem();
        poem.setVerses(new ArrayList<>());
        Container<Verse> verse = new Container<>();
        verse.setObject(new Verse());
        verse.getObject().setLines(new ArrayList<>());

        node.children().forEach(n->{
            if (!"p".equals(n.nodeName())) {
                throw new IllegalStateException("Unexpected element: " + n.nodeName());
            }
            if (n.text().startsWith("*")) {
                poem.getVerses().add(verse.getObject());
                verse.setObject(new Verse());
                verse.getObject().setLines(new ArrayList<>());
            }
            verse.getObject().getLines().add(n.text());
        });

        return poem;
    }

    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    private static class Container<T> {
        private T object;

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }
    }
}
