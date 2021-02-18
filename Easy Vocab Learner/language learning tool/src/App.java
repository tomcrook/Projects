import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;


/**
 *  This class is responsible for the user interface
 */
public class App {

  JFrame frame;

  JTextField word = new JTextField();

  JButton nextWord = new JButton("Next");

  Language curr;

  boolean next = true;

  public App() throws IOException {
    this.frame = this.makeFrame("Language Learning Tool v1.0");
    this.frame.add(getHomePage());
    frame.pack();
    this.curr = null;
  }
  ///////////////////////////////////////////////
  private JFrame makeFrame(String name) {
    JFrame temp = new JFrame(name);
    temp.setPreferredSize(new Dimension(750, 300));
    temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    temp.setVisible(true);
    return temp;
  }
  ///////////////////////////////////////////////

  private JPanel getHomePage() {
    JPanel home = new JPanel();
    JTextField title = new JTextField("Select language below :");
    title.setFont(new Font("Verdana", Font.BOLD, 23));
    title.setHorizontalAlignment(JTextField.CENTER);
    title.setEditable(false);
    home.setLayout(new GridLayout(2,1));
    home.add(title);
    home.add(this.languageOptions());
    return home;
  }

  private JPanel languageOptions() {
    JPanel options = new JPanel();
    File folder = new File(System.getProperty("user.dir") + "/languages");
    File[] files = folder.listFiles();
    for (File f : files) {
      if (f.getName().contains(".txt")) {
        JButton b = new JButton(f.getName().substring(0, f.getName().indexOf(".txt")));
        b.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              curr = new Language(f.getCanonicalPath());
              updateLanguage();
            } catch (IOException ioException) {
              ioException.printStackTrace();
            }
          }
        });
        options.add(b);
      }
    }
    return options;
  }

  private void updateLanguage() {
    try {
      curr.save();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.frame.getContentPane().removeAll();
    word.setEditable(false);
    word.setFont(new Font("Times New Roman", Font.BOLD, 30));
    word.setHorizontalAlignment(JTextField.CENTER);
    frame.setLayout(new BorderLayout());

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    JButton back = new JButton("Back");
    panel.add(back, BorderLayout.WEST);

    back.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          curr.save();
          frame.getContentPane().removeAll();
          frame.add(getHomePage());
          curr = null;
          frame.pack();
          next = true;
        } catch (IOException ee) {
          ee.printStackTrace();
        }
      }
    });

    frame.add(panel, BorderLayout.NORTH);

    if (next) {
      frame.add(word);
      curr.getNewPair();
      word.setText(curr.getTranslation());
      frame.add(addShowAnswerButton(), BorderLayout.SOUTH);
    } else {
      JPanel reveal = new JPanel();
      reveal.setLayout(new BorderLayout());
      JTextField old = new JTextField(word.getText());
      old.setEditable(false);
      old.setHorizontalAlignment(JTextField.CENTER);
      old.setFont(new Font("Times New Roman", Font.BOLD, 22));
      word.setText(curr.getEnglish());
      reveal.add(old, BorderLayout.NORTH);
      reveal.add(word);
      frame.add(reveal);
      frame.add(addOptions(), BorderLayout.SOUTH);
      frame.pack();
    }
    next = !next;
    frame.pack();
  }

  private JButton addShowAnswerButton() {
    JButton answer = new JButton("Show Answer");
    answer.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateLanguage();
      }
    });
    return answer;
  }

  private JPanel addOptions() {
    JPanel options = new JPanel();
    JButton easy = new JButton("CORRECT");
    easy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        curr.correct();
        updateLanguage();
      }
    });
    JButton hard = new JButton("INCORRECT");
    hard.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        curr.incorrect();
        updateLanguage();
      }
    });
    options.add(hard);
    options.add(easy);
    return options;
  }


}
