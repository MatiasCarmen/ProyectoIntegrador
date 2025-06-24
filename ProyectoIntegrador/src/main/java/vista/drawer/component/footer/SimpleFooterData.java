package vista.drawer.component.footer;

public class SimpleFooterData {
    private String title;
    private String description;
    private String style;

    public SimpleFooterData setTitle(String title) {
        this.title = title;
        return this;
    }

    public SimpleFooterData setDescription(String description) {
        this.description = description;
        return this;
    }

    public SimpleFooterData setStyle(String style) {
        this.style = style;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStyle() {
        return style;
    }
}
