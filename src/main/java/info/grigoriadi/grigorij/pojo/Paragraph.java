package info.grigoriadi.grigorij.pojo;

public class Paragraph {

    private String content;

    private Poem poem;

    private boolean indent;

    private boolean quote;

    private Image image;

    public Paragraph() {
    }

    /**
     * A poem consisting of verses and verse lines.
     * Either a content or poem should not be null.
     *
     * @return poem
     */
    public Poem getPoem() {
        return poem;
    }

    public void setPoem(Poem poem) {
        this.poem = poem;
    }

    public Paragraph(String content) {
        this.content = content;
    }

    /**
     * Basic string content.
     * Either a content or poem should not be null.
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Should the paragraph be indented?
     * @return true for indent
     */
    public boolean isIndent() {
        return indent;
    }

    public void setIndent(boolean indent) {
        this.indent = indent;
    }

    /**
     * Associated image if any.
     * @return image
     */
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Is paragraph a quote.
     * @return is quote
     */
    public boolean isQuote() {
        return quote;
    }

    public void setQuote(boolean quote) {
        this.quote = quote;
    }
}
