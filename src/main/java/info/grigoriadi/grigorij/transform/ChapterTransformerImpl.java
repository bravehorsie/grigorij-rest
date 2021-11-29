package info.grigoriadi.grigorij.transform;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.grigoriadi.grigorij.ChapterService;
import info.grigoriadi.grigorij.pojo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Historically here was an old plain html version of the pages, this transformer translates the structured
 * chapter text to json.
 */
@Service
public class ChapterTransformerImpl implements ChapterTransformer {

    private ObjectMapper mapper = new ObjectMapper();

    private String jsonOutPath;

    private String htmlInPath;

    public ChapterTransformerImpl() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void saveChapter(long id)  {
        String content = null;
        try (FileInputStream fis = new FileInputStream(htmlInPath + File.separator + id + ".html")) {
            content = new String(fis.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + id + ".html", e);
        }

        Chapter chapter = new Chapter();
        chapter.setSections(new ArrayList<>());
        Container<Section> lastSection = new Container<>(new Section());
        chapter.setImages(new ArrayList<>());
        Document doc = Jsoup.parse(content);
        Element body = doc.select("body").get(0);

        Elements contentChildren = body.children();
        Element firstChild = contentChildren.get(0);
        if ("div".equals(firstChild.nodeName()) && "content".equals(firstChild.id())) {
            contentChildren = firstChild.children();
        }
        contentChildren.forEach(n->{
            String text = n.text();
            if (text.length() == 0) {
                throw new IllegalStateException("Empty text");
            }
            if ("h1".equals(n.nodeName())) {
                if (chapter.getName() != null) {
                    throw new IllegalStateException("Chapter name already set, only one h1 per chapter expected");
                }
                chapter.setName(n.text());
            } else if ("h3".equals(n.nodeName()) || "h2".equals(n.nodeName()) || "h4".equals(n.nodeName())) {
                if (lastSection.getObject().getParagraphs().size() > 0) {
                    chapter.getSections().add(lastSection.getObject());
                }
                lastSection.setObject(new Section(text));
            } else if ("p".equals(n.nodeName())) {
                Paragraph paragraph = new Paragraph(text);
                n.classNames().forEach(cn->{
                    switch (cn) {
                        case "newparagraph":
                            paragraph.setIndent(true);
                            break;
                        case "quote":
                            paragraph.setQuote(true);
                            break;
                    }
                });
                lastSection.getObject().getParagraphs().add(paragraph);
                List<Image> images = extractImages(n);
                images.stream().findAny().ifPresent(paragraph::setImage);
            } else if ("div".equals(n.nodeName()) && "stix".equals(n.attr("class"))) {
                Paragraph poemParagraph = new Paragraph();
                poemParagraph.setPoem(parsePoem(n));
                lastSection.getObject().getParagraphs().add(poemParagraph);
            } else if ("div".equals(n.nodeName()) && "photos".equals(n.attr("class"))) {
                System.out.println("Ignoring photos div");
            } else {
                throw new IllegalStateException("Unexpected element: " + n.nodeName());
            }
        });

        chapter.getSections().add(lastSection.getObject());

        //get images
        chapter.getImages().addAll(extractImages(body));

        File file = new File(jsonOutPath + File.separator + "json" + File.separator + id + ".json");
        try {
            mapper.writeValue(file, chapter);
        } catch (IOException e) {
            throw new RuntimeException("Can't write file", e);
        }

    }

    private List<Image> extractImages(Element node) {
        return node.select("a.fancybox").stream().map(n->{
            Image image = new Image();
            String src = n.attr("href");
            image.setName(src);
            image.setDescription(n.attr("title"));
            return image;
        }).collect(Collectors.toList());
    }

    private Poem parsePoem(Element node) {
        Poem poem = new Poem();
        poem.setVerses(new ArrayList<>());
        Container<Verse> verse = new Container<>(new Verse());
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

        poem.getVerses().add(verse.getObject());
        return poem;
    }

    private static class Container<T> {
        private T object;

        public Container(T object) {
            this.object = object;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }
    }

    @Value("${jsonOutPath}")
    public void setJsonOutPath(String jsonOutPath) {
        this.jsonOutPath = jsonOutPath;
    }

    @Value("${htmlInPath}")
    public void setHtmlInPath(String htmlInPath) {
        this.htmlInPath = htmlInPath;
    }
}

