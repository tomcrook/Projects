import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class SentenceReader {

  Scanner scanner = new Scanner(Paths.get("clean.txt"));
  ArrayList<String> toDo = new ArrayList<>();
  ArrayList<String> done = new ArrayList<>();


  JTextArea sentence = new JTextArea("Press start to begin.");
  JTextArea vocab = new JTextArea();

  JButton problem = new JButton("Report Problem");
  JButton next = new JButton("Start");
  JButton addVocab = new JButton("Add Vocab Word");



  SentenceReader() throws IOException {
    JFrame frame = new JFrame("Sentence Reader");
    frame.setSize(new Dimension(500,250));
    frame.setVisible(true);
    frame.setLayout(new GridLayout(2,1));
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);


    sentence.setEditable(false);
    sentence.setLineWrap(true);
    frame.add(sentence);



    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() / 4));
    panel.add(problem);
    panel.add(addVocab);
    panel.add(next);
    frame.add(panel);

    addVocab.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame vocabBox = new JFrame();
        vocabBox.setSize(new Dimension(100,100));
        vocabBox.setVisible(true);
        vocabBox.setLayout(new GridLayout(2,1));
        vocabBox.add(vocab);
        JButton save = new JButton("Save Word");
        vocabBox.add(save);
        save.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              if (!vocab.getText().equals("")) {
                addVocabWord(vocab.getText());
              }
            } catch (IOException ioException) {
              ioException.printStackTrace();
            }
            vocab.setText("");
          }
        });
        vocab.setEditable(true);
      }
    });

    next.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (next.getText().equals("Start")) {
          next.setText("Next");
        } else {
          done.add(sentence.getText());
        }
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

    while(scanner.hasNextLine()) {
      String next = scanner.nextLine();
      toDo.add(next);
    }
  }

  public String getNext() {
    try {
      String next = toDo.remove((int) (Math.random() * toDo.size()));
      return next;
    } catch (IndexOutOfBoundsException e) {
      return "NO MORE SENTENCES!!!";
    }
  }

  public void save() throws IOException {
    FileWriter fw = new FileWriter("clean.txt");
    for (String s : toDo) {
      fw.append(s + "\n");
    }
    for (String s : done) {
      fw.append(s + "\n");
    }
    fw.flush();
  }

  public void addVocabWord(String word) throws IOException {
    FileWriter fw = new FileWriter("vocab.txt", true);
    fw.append(word + "\n");
    fw.close();
  }



}
