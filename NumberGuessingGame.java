import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int minNum = 1;
        int maxNum = 100;
        int maxAttempts = 10;
        int score = 0;
        int roundsPlayed = 0;
        boolean playAgain = true;

        while (playAgain) {
            int targetNumber = random.nextInt(maxNum - minNum + 1) + minNum;
            int attemptsLeft = maxAttempts;
            boolean hasWon = false;
            roundsPlayed++;

            System.out.println("Round " + roundsPlayed + " start! Guess the number between " + minNum + " and " + maxNum);

            while (attemptsLeft > 0) {
                System.out.println("You have " + attemptsLeft + " attempts left. Enter your guess:");
                int guess = scanner.nextInt();
                attemptsLeft--;

                if (guess == targetNumber) {
                    System.out.println("Congratulations! You've guessed the correct number!");
                    hasWon = true;
                    score++;
                    break;
                } else if (guess > targetNumber) {
                    System.out.println("Too high! Try again.");
                } else {
                    System.out.println("Too low! Try again.");
                }
            }

            if (!hasWon) {
                System.out.println("Sorry, you've used all attempts. The correct number was: " + targetNumber);
            }

            System.out.println("Your current score is: " + score);
            System.out.println("Do you want to play another round? (yes/no)");
            String response = scanner.next();
            playAgain = response.equalsIgnoreCase("yes");
        }

        System.out.println("Game Over! You played " + roundsPlayed + " rounds and your final score is: " + score);
        scanner.close();
    }
}
