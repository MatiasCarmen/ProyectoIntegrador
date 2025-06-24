package vista.drawer.component;

import ren.main.VistaPrincipal;
import vista.drawer.MyDrawerBuilder;
import javax.swing.*;
import java.awt.*;

public class DrawerPanel extends JPanel {
    private final VistaPrincipal mainFrame;
    private final MyDrawerBuilder builder;

    public DrawerPanel(VistaPrincipal mainFrame) {
        this.mainFrame = mainFrame;
        this.builder = new MyDrawerBuilder(mainFrame);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));

        // Usar el MyDrawerBuilder para construir el menú
        add(builder.build(), BorderLayout.CENTER);
    }
}
