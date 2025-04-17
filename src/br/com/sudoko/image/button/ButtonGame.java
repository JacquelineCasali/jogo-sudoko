package br.com.sudoko.image.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonGame extends JButton {
    public ButtonGame(final ActionListener actionListener) {
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }
}
