package info.grigoriadi.grigorij.pojo;

public class Paragraph {

    private String content;

    private Poem poem;

    private boolean indent;

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
}
