import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGame extends JPanel {

  NewGame(MainWindow window) {

    this.setPreferredSize(new Dimension(CONSTANT.WIDTH, CONSTANT.HEIGHT));
    this.setVisible(true);
    this.setLayout(new OverlayLayout(this));




  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(CONSTANT.COLOR);
    g.fillRect(0, 0, CONSTANT.WIDTH, CONSTANT.HEIGHT);
  }

}
