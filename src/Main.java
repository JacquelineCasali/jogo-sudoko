import br.com.sudoko.image.frame.MainFrame;
import br.com.sudoko.image.panel.MainPanel;


import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        var dimension= new Dimension(600,600);
        JPanel mainPanel=new MainPanel(dimension);
        JFrame mainFrame=new MainFrame(dimension,mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

}
