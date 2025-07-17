package vista.tema;
/**
 *
 * @author mathi
 */
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class TemaAdministrador {
    public static final Color COLOR_PRIMARIO = new Color(63, 81, 181);
    public static final Color COLOR_SECUNDARIO = new Color(255, 64, 129);
    public static final Color FONDO = new Color(245, 245, 245);
    public static final Color TEXTO = new Color(33, 33, 33);

    public static void inicializarTema() {
        try {
            FlatDarkLaf.setup();
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("ProgressBar.arc", 8);
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Button.foreground", COLOR_PRIMARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}