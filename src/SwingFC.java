/*
    Try this 17-1
    A Swing-based file comparison utility.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class SwingFC implements ActionListener {

    JTextField jtfFirts;  // holds the first filename.
    JTextField jtfSecond;  // holds the second filename.

    JButton jbtnComp;  // button to compare the files.
    JCheckBox jcbPos;

    JLabel jlabFirst, jlabSecond;  // displays prompts.
    JLabel jlabResults, jlabLocation;  // displays results and error messages.
    int counter;
    String s;

    SwingFC() {
        // Create a new frame container.
        JFrame jfrm = new JFrame("Compare Files");

        // Specify FlowLayout for layout manager.
        jfrm.setLayout(new FlowLayout());

        // Give frame an initial size.
        jfrm.setSize(200, 240);

        // Terminate the program when user closes application.
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create text fields for file names.
        jtfFirts = new JTextField(14);
        jtfSecond = new JTextField(14);

        // Set the action commands for the text fields.
        jtfFirts.setActionCommand("fileA");
        jtfSecond.setActionCommand("fileB");

        // Create the compare button.
        jbtnComp = new JButton("Compare");

        // Add check box for showing mismatch position.
        jcbPos = new JCheckBox("Show position of mismatch");

        // Add action listener for Compare button and checkbox.
        jbtnComp.addActionListener(this);
        jcbPos.addActionListener(this);

        // Create labels.
        jlabFirst = new JLabel("First file: ");
        jlabSecond = new JLabel("Second file: ");
        jlabResults = new JLabel("");
        jlabLocation = new JLabel("");

        // Add components to content pane.
        jfrm.add(jlabFirst);
        jfrm.add(jtfFirts);
        jfrm.add(jlabSecond);
        jfrm.add(jtfSecond);
        jfrm.add(jcbPos);
        jfrm.add(jbtnComp);
        jfrm.add(jlabResults);
        jfrm.add(jlabLocation);

        // Display the frame.
        jfrm.setVisible(true);
    }

    // Compare files when Compare button is pressed.
    public void actionPerformed(ActionEvent ae) {
        int i=0, j=0;

        if(ae.getActionCommand().equals("Compare")) {
            // First, confirm that both filenames have been entered.
            if (jtfFirts.getText().equals("")) {
                jlabResults.setText("First file name missing.");
                return;
            }
            if (jtfSecond.getText().equals("")) {
                jlabResults.setText("Second file name missing.");
                return;
            }

            // Compare files. Use try-with-resources to manage files.
            try (var f1 = new FileInputStream(jtfFirts.getText());
                 var f2 = new FileInputStream(jtfSecond.getText())) {
                // Check contents of each file.
                counter = 0;
                do {
                    counter++;
                    i = f1.read();
                    j = f2.read();
                    if (i != j) break;
                } while (i != -1 && j != -1);

                if (i != j) {
                    jlabResults.setText("Files are not the same.");
                    if (jcbPos.isSelected())
                        jlabLocation.setText("Mismatch location: " + counter);
                    else
                        jlabLocation.setText("");
                }
                else {
                    jlabResults.setText("Files are the same.");
                    jlabLocation.setText("");
                }
            } catch (IOException exc) {
                jlabResults.setText("File Error");
            }
        }
    }

    public static void main(String[] args) {
        // Create frame on event dispatching thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingFC();
            }
        });
    }
}
