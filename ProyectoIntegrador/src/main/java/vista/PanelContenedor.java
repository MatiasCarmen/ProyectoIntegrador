/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author mathi
 */

/** Contenedor central que gestiona las pantallas con CardLayout. */
public class PanelContenedor extends JPanel {
    private final CardLayout card = new CardLayout();

    public PanelContenedor() { setLayout(card); }

    public void addView(String name, JPanel panel) { add(panel, name); }
    public void showView(String name)            { card.show(this, name); }
}