import javax.swing.*;
import java.awt.*;

public class MainWindow {

  public enum PageType {
    MAIN, START
  }

  final int height = CONSTANT.HEIGHT, width = CONSTANT.WIDTH;
  public JFrame frame;
  private JPanel current;

  MainWindow() {
    frame = new JFrame();
    frame.setPreferredSize(new Dimension(width, height));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    current = this.getPage(PageType.MAIN);
  }

  public void show() {
    frame.add(current, BorderLayout.CENTER);
    frame.setVisible(true);
    frame.pack();
  }

  public void changeView(PageType type) {
    frame.remove(current);
    this.current = this.getPage(type);
    frame.add(current, BorderLayout.CENTER);
    frame.pack();
  }

  public JPanel getPage(PageType type) {
    switch (type) {
      case MAIN : return new MainScreen(this);
      case START : return new NewGame(this);
    }
    return null;
  }


}
