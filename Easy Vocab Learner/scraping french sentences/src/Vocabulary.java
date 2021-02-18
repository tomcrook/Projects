import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Vocabulary {

  Scanner scanner = new Scanner(Paths.get("vocab.txt"));
  ArrayList<String> toDo = new ArrayList<>();
  ArrayList<String> done = new ArrayList<>();


  JTextField sentence = new JTextField("Press start to begin.");
  JTextArea vocab = new JTextArea();

  JButton problem = new JButton("Report Problem");
  JButton next = new JButton("Start");
  JButton flip = new JButton("Flip");
  JButton delete = new JButton("Delete");
  boolean v = true;

  String show;
  String answer;


  Vocabulary() throws IOException {
    JFrame frame = new JFrame("Vocabulary");
    frame.setSize(new Dimension(500,250));
    frame.setVisible(true);
    frame.setLayout(new GridLayout(2,1));
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);


    sentence.setEditable(false);
    sentence.setHorizontalAlignment(JTextField.CENTER);
    frame.add(sentence);



    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() / 4));
    panel.add(delete);
    panel.add(problem);
    panel.add(flip);
    panel.add(next);
    frame.add(panel);

    delete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        done.remove(done.size() - 1);
        sentence.setText(getNext());
        sentence.setEditable(false);
        try {
          save();
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });

    next.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (next.getText().equals("Start")) {
          next.setText("Next");
        } else {
          done.set(done.size() - 1, show + "," + answer);
        }
        frame.setTitle("English");
        sentence.setText(getNext());
        sentence.setEditable(false);
        try {
          save();
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });

    problem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        problem.setText("Edit above");
        sentence.setEditable(true);
      }
    });

    flip.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (v) {
          show = sentence.getText();
          frame.setTitle("French");
          sentence.setText(answer);
        } else {
          answer = sentence.getText();
          frame.setTitle("English");
          sentence.setText(show);
        }
        v = !v;
      }
    });

    while(scanner.hasNextLine()) {
      String next = scanner.nextLine();
      toDo.add(next);
    }
  }

  public String getNext() {
    try {
      show = toDo.remove((int) (Math.random() * toDo.size()));
      done.add(show);
      answer = show.substring(show.indexOf(",") + 1);
      show = show.substring(0, show.indexOf(","));
      return show;
    } catch (IndexOutOfBoundsException e) {
      return "NO MORE WORDS!!!";
    }
  }

  public void save() throws IOException {
    FileWriter fw = new FileWriter("vocab.txt");
    for (String s : toDo) {
      fw.append(s + "\n");
    }
    for (String s : done) {
      fw.append(s + "\n");
    }
    fw.flush();
  }





}
