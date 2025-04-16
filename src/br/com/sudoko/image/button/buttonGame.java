package br.com.sudoko.image.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class buttonGame extends JButton {
    public buttonGame(final ActionListener actionListener) {
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }
}
