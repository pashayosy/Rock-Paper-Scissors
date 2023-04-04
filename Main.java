package rockpaperscissors;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int score = 0;
    public static String[]chose = {"rock", "gun", "lightning", "devil", "dragon", "water", "air","paper",
                                    "sponge", "wolf", "tree", "human", "snake", "scissors", "fire"};
    public static void main(String[] args) {
        // write your code here
        File f = new File("rating.txt");
        Scanner fileScan = null;

        try {
            fileScan = new Scanner(f);
        } catch (Exception e){
            System.out.println(e.getMessage() + " The file not found");
        }

        Scanner in = new Scanner(System.in);

        String[]pcChoseFrom = new String[15];
        int pcChoseFromSize = 0;
        String listOfChose;
        int startWord = 0;
        int endWord = 0;

        String userName;
        String userChose;
        String pcChose;
        Boolean exit = false;

        System.out.println("Enter your name:");
        userName = in.nextLine();
        System.out.println("Hello, " + userName);

        listOfChose = in.nextLine();

        if (listOfChose.length() > 0) {
            while(listOfChose.length() - 1> endWord) {
                if (listOfChose.charAt(endWord) == ',') {
                    pcChoseFrom[pcChoseFromSize] = listOfChose.substring(startWord, endWord);
                    endWord++;
                    startWord = endWord;
                    pcChoseFromSize++;
                } else {
                    endWord++;
                }
            }
            pcChoseFrom[pcChoseFromSize] = listOfChose.substring(startWord);
            pcChoseFromSize++;
        } else {
            pcChoseFrom[0] = "rock";
            pcChoseFrom[1] = "paper";
            pcChoseFrom[2] = "scissors";
            pcChoseFromSize = 3;

        }

        System.out.println("Okay, let's start");
        score = checkIfExists(userName, fileScan);

        while (!exit) {
            userChose = in.next();

            if (isCorrectChose(userChose, pcChoseFrom, pcChoseFromSize)) {
                pcChose = pcPick(pcChoseFromSize, pcChoseFrom);
                whoWinPrint(userChose, pcChose);
            } else {
                switch (userChose) {
                    case "!exit":
                        exit = true;
                        break;
                    case "!rating":
                        System.out.println("Your rating: " + score);
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            }
        }
        System.out.println("Bye!");
    }

    public static String pcPick(int size, String[]listOfChosies) {
        Random r = new Random();
        int randomChose;
        randomChose = r.nextInt(size);

        return listOfChosies[randomChose];
    }
    public static void whoWinPrint(String userChose, String pcChose) {
        Boolean isWin = true;
        if (pcChose.matches(userChose)) {
            System.out.println("There is a draw (" + userChose + ")");
            score += 50;
        } else {
            int userIndex = 0;
            int pcIndex= 0;
            for (int i = 0; i < 15; i++) {
                if (userChose.matches(chose[i]))
                    userIndex = i;

                if (pcChose.matches(chose[i]))
                    pcIndex = i;
            }

            for(int i = 0; i < 7; i++){
                if (userIndex + 1 > 14) {
                    userIndex = 0;
                } else {
                    userIndex++;
                }

                if (userIndex == pcIndex)
                    isWin = false;

            }

            if (isWin) {
                System.out.println("Well done. The computer chose " + pcChose + " and failed");
                score += 100;
            } else {
                System.out.println("Sorry, but the computer chose " + pcChose);
            }

        }
    }

    public static boolean isCorrectChose(String userChose, String[]listOfChosies, int size) {
        for (int i = 0; i < size; i++) {
            if (userChose.matches(listOfChosies[i]))
                return true;
        }

        return false;
    }

    public static int checkIfExists(String userName, Scanner fileScan) {
        if (fileScan == null) {
            return 0;
        } else {
            while (fileScan.hasNext()) {
                if (fileScan.next().matches(userName)) {
                    return Integer.parseInt(fileScan.next());
                }
            }

            return 0;
        }
    }
}
