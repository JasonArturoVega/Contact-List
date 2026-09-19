package Ui;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(int sizeX, int sizeY)
    {
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);

        setLayout(null);
        getContentPane().setBackground(Color.GRAY);
    }
}
