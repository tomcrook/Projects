import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JPanel {

  MainScreen(MainWindow window) {

    this.setPreferredSize(new Dimension(CONSTANT.WIDTH, CONSTANT.HEIGHT));
    this.setVisible(true);

    this.setLayout(new OverlayLayout(this));

    JPanel buttons = new JPanel(new GridLayout(2,1));
    JButton start = new JButton("Start");
    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        window.changeView(MainWindow.PageType.START);
      }
    });
    buttons.add(start);
    JButton exit = new JButton("Exit");
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    buttons.add(exit);

    buttons.setMaximumSize(new Dimension(200, 150));

    this.add(buttons);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(CONSTANT.COLOR);
    g.fillRect(0, 0, CONSTANT.WIDTH, CONSTANT.HEIGHT);
    g.setColor(new Color(0,0,0));
    g.setFont(new Font("Helvetica", 1, 67));
    g.drawString("Football Simulator v1.0",35, 120);
  }



}
