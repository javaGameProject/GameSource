/**
 * Created by Fantomasa on 19.4.2016 г..
 */
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Breakout extends JFrame {
    public Breakout() {
        this.initUI();
    }

    private void initUI() {
        this.add(new Board());
        this.setTitle("Breakout");
        this.setDefaultCloseOperation(3);
        this.setSize(380, 400);
        this.setLocationRelativeTo((Component)null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Breakout game = new Breakout();
                game.setVisible(true);
            }
        });
    }
}

