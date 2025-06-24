package vista.drawer.component.menu;

public class SimpleMenuOption {
    private String[][] menus;
    private String[] icons;
    private String baseIconPath;
    private float iconScale = 1.0f;
    private MenuEvent menuEvent;
    private String style;

    public SimpleMenuOption setMenus(String[][] menus) {
        this.menus = menus;
        return this;
    }

    public SimpleMenuOption setIcons(String[] icons) {
        this.icons = icons;
        return this;
    }

    public SimpleMenuOption setBaseIconPath(String path) {
        this.baseIconPath = path;
        return this;
    }

    public SimpleMenuOption setIconScale(float scale) {
        this.iconScale = scale;
        return this;
    }

    public SimpleMenuOption addMenuEvent(MenuEvent event) {
        this.menuEvent = event;
        return this;
    }

    public SimpleMenuOption setStyle(String style) {
        this.style = style;
        return this;
    }

    public String[][] getMenus() {
        return menus;
    }

    public String[] getIcons() {
        return icons;
    }

    public String getBaseIconPath() {
        return baseIconPath;
    }

    public float getIconScale() {
        return iconScale;
    }

    public MenuEvent getMenuEvent() {
        return menuEvent;
    }

    public String getStyle() {
        return style;
    }
}
