package vista.drawer.component.menu;
/**
 *
 * @author mathi
 */
public class MenuAction {
    private final int index;
    private final int subIndex;

    public MenuAction(int index, int subIndex) {
        this.index = index;
        this.subIndex = subIndex;
    }

    public int getIndex() {
        return index;
    }

    public int getSubIndex() {
        return subIndex;
    }
}
