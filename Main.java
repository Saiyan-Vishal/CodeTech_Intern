package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {
    private JTextField[] subjectFields; // Array for subject input fields
    private JButton submitButton;
    private int numSubjects = 5; // Number of subjects (modifiable)

    public Main() {
        setTitle("Student Marks Entry");
        setSize(400, 300);
        setLayout(new GridLayout(numSubjects + 2, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels and Text Fields for Subject Marks
        subjectFields = new JTextField[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            add(new JLabel("Subject " + (i + 1) + " Marks:"));
            subjectFields[i] = new JTextField();
            add(subjectFields[i]);
        }

        // Submit Button
        submitButton = new JButton("Calculate Result");
        submitButton.addActionListener(this);
        add(submitButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        int totalMarks = 0;
        int maxMarks = numSubjects * 100; // Assuming each subject has max 100 marks
        boolean validInput = true;

        // Retrieve marks from text fields
        for (int i = 0; i < numSubjects; i++) {
            try {
                int marks = Integer.parseInt(subjectFields[i].getText());
                if (marks < 0 || marks > 100) {
                    throw new NumberFormatException();
                }
                totalMarks += marks;
            } catch (NumberFormatException ex) {
                validInput = false;
                JOptionPane.showMessageDialog(this, "Enter valid marks (0-100) for Subject " + (i + 1), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }

        if (validInput) {
            // Calculate percentage
            double percentage = (totalMarks / (double) maxMarks) * 100;

            // Display result in a new window
            new ResultWindow(totalMarks, percentage);
        }
    }

    // Separate Window to Display Result
    class ResultWindow extends JFrame {
        public ResultWindow(int total, double percentage) {
            setTitle("Student Result");
            setSize(300, 150);
            setLayout(new GridLayout(3, 1, 10, 10));

            add(new JLabel("Total Marks: " + total));
            add(new JLabel("Percentage: " + String.format("%.2f", percentage) + "%"));

            String result = (percentage >= 40) ? "Status: Pass 🎉" : "Status: Fail ❌";
            add(new JLabel(result));

            setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
