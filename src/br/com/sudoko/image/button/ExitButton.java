package br.com.sudoko.image.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ExitButton extends JButton {

    public ExitButton(final ActionListener actionListener) {
        this.setText("Finalizar jogo");
        this.addActionListener(actionListener);
    }
}
