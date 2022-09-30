import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        downloadTab app = new downloadTab();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height / 4;
        int width = screenSize.width / 3;
        JFrame frame = new JFrame("Download");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setContentPane(app.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}