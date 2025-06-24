package vista.drawer.component.header;

import javax.swing.Icon;

public class SimpleHeaderData {
    private Icon icon;
    private String title;
    private String description;
    private String style;

    public SimpleHeaderData setIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    public SimpleHeaderData setTitle(String title) {
        this.title = title;
        return this;
    }

    public SimpleHeaderData setDescription(String description) {
        this.description = description;
        return this;
    }

    public SimpleHeaderData setStyle(String style) {
        this.style = style;
        return this;
    }

    public Icon getIcon() {
        return icon;
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
