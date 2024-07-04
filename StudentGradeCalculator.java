import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculator {
    private JFrame frame;
    private JTextField[] marksFields;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;
    private JButton calculateButton;
    private JButton addButton;
    private JPanel marksPanel;
    private int numSubjects;

    public StudentGradeCalculator() {
        frame = new JFrame("Student Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        numSubjects = 0;
        marksPanel = new JPanel();
        marksPanel.setLayout(new BoxLayout(marksPanel, BoxLayout.Y_AXIS));

        addButton = new JButton("Add Subject");
        addButton.addActionListener(new AddButtonListener());
        frame.add(addButton, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(marksPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel resultsPanel = new JPanel(new GridLayout(3, 1));
        totalMarksLabel = new JLabel("Total Marks: ", SwingConstants.CENTER);
        averagePercentageLabel = new JLabel("Average Percentage: ", SwingConstants.CENTER);
        gradeLabel = new JLabel("Grade: ", SwingConstants.CENTER);
        resultsPanel.add(totalMarksLabel);
        resultsPanel.add(averagePercentageLabel);
        resultsPanel.add(gradeLabel);
        frame.add(resultsPanel, BorderLayout.SOUTH);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        frame.add(calculateButton, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField newMarksField = new JTextField();
            marksPanel.add(new JLabel("Marks for Subject " + (numSubjects + 1) + ":"));
            marksPanel.add(newMarksField);
            numSubjects++;
            frame.revalidate();
            frame.repaint();
        }
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int totalMarks = 0;
            boolean validInput = true;

            marksFields = new JTextField[numSubjects];
            Component[] components = marksPanel.getComponents();
            int index = 0;
            for (Component component : components) {
                if (component instanceof JTextField) {
                    JTextField marksField = (JTextField) component;
                    marksFields[index++] = marksField;
                }
            }

            for (JTextField marksField : marksFields) {
                try {
                    int marks = Integer.parseInt(marksField.getText());
                    if (marks < 0 || marks > 100) {
                        JOptionPane.showMessageDialog(frame, "Please enter marks between 0 and 100.");
                        validInput = false;
                        break;
                    }
                    totalMarks += marks;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numerical marks.");
                    validInput = false;
                    break;
                }
            }

            if (validInput) {
                double averagePercentage = (double) totalMarks / numSubjects;
                String grade = calculateGrade(averagePercentage);

                totalMarksLabel.setText("Total Marks: " + totalMarks);
                averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage));
                gradeLabel.setText("Grade: " + grade);
            }
        }
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A";
        } else if (averagePercentage >= 80) {
            return "B";
        } else if (averagePercentage >= 70) {
            return "C";
        } else if (averagePercentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeCalculator::new);
    }
}
