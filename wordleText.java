import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import java.io.File;

public class wordleText{
    private static String[][] guessSpace = new String[6][5];
    private static int guessNum = 0;
    private static String targetWord = "";
    private static String[] targetArray = new String[5];
    private static String wordList = "";
    private static String[] alphabetArray = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String WHITE = "\033[0;37m";   // WHITE
    public static final String RESET = "\033[0;35m";   // PURPLE


    public static void printGuessSpace(){
        System.out.println(RESET);
        System.out.println(alphabetArray[0] + " " + alphabetArray[1] + " " + alphabetArray[2] + " " + alphabetArray[3] + " " + alphabetArray[4] +  " " + alphabetArray[5] +  " " + alphabetArray[6] +  " " + alphabetArray[7] +  " " + alphabetArray[8] +  " " + alphabetArray[9] +  " " + alphabetArray[10] +  " " + alphabetArray[11] +  " " + alphabetArray[12]);
        System.out.println(alphabetArray[13] + " " + alphabetArray[14] + " " + alphabetArray[15] + " " + alphabetArray[16] + " " + alphabetArray[17] +  " " + alphabetArray[18] +  " " + alphabetArray[19] +  " " + alphabetArray[20] +  " " + alphabetArray[21] +  " " + alphabetArray[22] +  " " + alphabetArray[23] +  " " + alphabetArray[24] +  " " + alphabetArray[25]);
        System.out.println(" ");
        System.out.println(guessSpace[0][0] + " " + guessSpace[0][1] + " " + guessSpace[0][2] + " " + guessSpace[0][3]+ " " + guessSpace[0][4]);
        System.out.println(guessSpace[1][0] + " " + guessSpace[1][1] + " " + guessSpace[1][2] + " " + guessSpace[1][3]+ " " + guessSpace[1][4]);
        System.out.println(guessSpace[2][0] + " " + guessSpace[2][1] + " " + guessSpace[2][2] + " " + guessSpace[2][3]+ " " + guessSpace[2][4]);
        System.out.println(guessSpace[3][0] + " " + guessSpace[3][1] + " " + guessSpace[3][2] + " " + guessSpace[3][3]+ " " + guessSpace[3][4]);
        System.out.println(guessSpace[4][0] + " " + guessSpace[4][1] + " " + guessSpace[4][2] + " " + guessSpace[4][3]+ " " + guessSpace[4][4]);
        System.out.println(guessSpace[5][0] + " " + guessSpace[5][1] + " " + guessSpace[5][2] + " " + guessSpace[5][3]+ " " + guessSpace[5][4]);
        System.out.println(" ");
    } 

    public static void listReader() throws IOException{
        String wordsInAString = null;
        String location = "wordle-words.txt";
        File words = new File(location); // creates a new file using the file class
        StringBuilder wordsFile = new StringBuilder ((int)words.length()); // uses a stringbuilder to build a string from the file
        Scanner reader = new Scanner(words); // creates a scanner
        String linebreaks = System.getProperty("line.separator"); // uses the separation between lines as the linebreaks when separating words
        while (reader.hasNextLine()){
            wordsFile.append(reader.nextLine() + linebreaks); // appends linebreaks to every line in the StringBuilder 
            }
        wordsFile.toString(); // converts it to a string
        wordsInAString = wordsFile.toString();
        reader.close(); // closes the scanner 
        wordList = wordsInAString;
    } 

    public static String wordGenerator() throws IOException{
        String linebreaks = System.getProperty("line.separator"); // uses the separation between lines as the linebreaks when separating words
        // System.out.println("wordList:" + wordList);
        String[] wordListArray = wordList.split(linebreaks); // uses linebreaks to store it each word in an array
        Random random = new Random(); // creates a random object
        int randomNum = random.nextInt(999) + 1;  // generate a random word between 1 and 466550
        String generatedWord = wordListArray[randomNum]; // uses the random integer passed into the method to pick a random word
        return generatedWord;
    }

    public static Boolean guess(){
        Scanner scanner = new Scanner(System.in);
        // taking in guesses
        String guess = "";
        boolean isWordValid = false;
        while (isWordValid == false){
            System.out.print("Guess: ");
            guess = scanner.nextLine();  // Read user input
            // makes sure guess is a 5 letter word
            boolean isWordFive = false;
            if (guess.length() == 5){
                guess = guess.toLowerCase();
                isWordFive = true;
            } 

            // makes sure guess is a word in the dictionary
            String[] wordListArray = wordList.split(System.getProperty("line.separator"));
            Boolean isWordInList = false;
            for (int i=0; i<wordListArray.length; i++){
                if (guess.equals(wordListArray[i])){
                    isWordInList = true;
                }
            }
            if (isWordInList == true && isWordFive == true){
                isWordValid = true;
            } 

            // printing our errors for the player if needed
            if (isWordFive == false){
                System.out.println("Your guess isn't 5 letters. Try again!");
            } else if (isWordInList == false){
                System.out.println("Your guess isn't in our dictionary. Try again!");
            } 
        }   

        String wordArray[] = guess.split("");

        // adding guess to the word array w colors
        for (int x=0; x<5; x++){
            String letter = wordArray[x];
            // if absolutely no match:
            String letterWColor = letter;
            // if the letter is in the word but not the right spot:
            Boolean letterInWord = false;
            for (int m=0; m<5 ; m++){ // checking the whole target
                // System.out.println("does " + targetArray[m] + " = " + letter);
                if (targetArray[m].equals(letter)){
                    letterInWord = true;
                    break;
                } else {
                    letterInWord = false;
                }
            }
            // System.out.println(String.valueOf(letterInWord));

            if (letterInWord == true){
                // System.out.println("YELLOW");
                letterWColor = YELLOW + letter + RESET;
            } else {
                // System.out.println("WHITE");
                letterWColor = WHITE + letter + RESET;
            }
            // if the letter is in the same spot in the word and the target:
            if (wordArray[x].equals(targetArray[x])){
                letterWColor = GREEN + letter + RESET;
            } 
            guessSpace[guessNum][x] = letterWColor;
            // System.out.println("*");

            // updating the alphabet array w color :-)
            for (int i=0; i<26; i++){
                if (letter.equalsIgnoreCase(alphabetArray[i])){
                    alphabetArray[i] = letterWColor;
                }
            }
        }

        printGuessSpace();
        guessNum++;

        if (guess.equals(targetWord)){
            return true;
        }

        return false;
    }

    public static void main(String[] args){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        Boolean active = true;
        while (active == true){
            Boolean winCondition = false;
            Scanner scanner = new Scanner(System.in);
            // generating a space for guesses
            for (int x=0; x<6; x++){
                for (int y=0; y<5; y++){
                    guessSpace[x][y] = "_";
                }
            }
            printGuessSpace();

            // generating a target word
            try {
                listReader();
                targetWord = wordGenerator();
            }
            catch(IOException e) {
                e.printStackTrace();
            }

            targetArray = targetWord.split("");

            // guessing
            while (guessNum < 6 && winCondition == false){
                if (guess() == true){
                    winCondition = true;
                }
            }

            if (winCondition == true){
                System.out.println("You Win!");
            } else {
                System.out.println("The word was " + targetWord + ".");
                System.out.println("Better luck next time!");
            }

            System.out.println("Play again (Y/N)?");
            String input = scanner.nextLine();
            Boolean validInput = false;
            while (validInput == false){
                if (input.equalsIgnoreCase("N")){
                    return;
                } if (input.equalsIgnoreCase("Y")){
                    validInput = true;
                    winCondition = false;
                    guessNum = 0;
                    alphabetArray = alphabet.split("");
                } else {
                    System.out.println("I didn't quite catch that. Try again?");
                }
            }
        }
    }
}