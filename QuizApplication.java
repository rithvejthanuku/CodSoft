import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizApplication {
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft;
    private int currentQuestionIndex;
    private int score;
    private List<Question> questions;
    private List<Boolean> results;

    public QuizApplication() {
        questions = createQuestions();
        results = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;

        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(7, 1));

        timerLabel = new JLabel("Time left: 10", SwingConstants.CENTER);
        frame.add(timerLabel);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(questionLabel);

        options = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionsGroup.add(options[i]);
            frame.add(options[i]);
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        frame.add(submitButton);

        loadNextQuestion();

        frame.setVisible(true);
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText(question.getQuestion());
            for (int i = 0; i < 4; i++) {
                options[i].setText(question.getOptions()[i]);
            }

            timeLeft = 10;
            timerLabel.setText("Time left: " + timeLeft);
            if (timer != null) {
                timer.stop();
            }
            timer = new Timer(1000, new TimerListener());
            timer.start();
        } else {
            displayResults();
        }
    }

    private void displayResults() {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(questions.size() + 2, 1));

        JLabel scoreLabel = new JLabel("Your score: " + score, SwingConstants.CENTER);
        frame.add(scoreLabel);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            boolean correct = results.get(i);
            JLabel resultLabel = new JLabel((i + 1) + ". " + question.getQuestion() + " - " + (correct ? "Correct" : "Incorrect"), SwingConstants.CENTER);
            frame.add(resultLabel);
        }

        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(e -> System.exit(0));
        frame.add(finishButton);

        frame.revalidate();
        frame.repaint();
    }

    private List<Question> createQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 0));
        questions.add(new Question("Who wrote 'Hamlet'?", new String[]{"Charles Dickens", "J.K. Rowling", "William Shakespeare", "Leo Tolstoy"}, 2));
        questions.add(new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 2));
        questions.add(new Question("What is the chemical symbol for water?", new String[]{"H2O", "O2", "CO2", "NaCl"}, 0));
        return questions;
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            Question question = questions.get(currentQuestionIndex);
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected() && i == question.getCorrectAnswerIndex()) {
                    score++;
                    results.add(true);
                } else if (options[i].isSelected()) {
                    results.add(false);
                }
            }

            currentQuestionIndex++;
            loadNextQuestion();
        }
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft);

            if (timeLeft <= 0) {
                timer.stop();
                results.add(false);
                currentQuestionIndex++;
                loadNextQuestion();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApplication::new);
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
