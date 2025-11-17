import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuSystem {

    // Constants for menu options
    private static final int OPTION_NUMBERS = 1;
    private static final int OPTION_WORDS = 2;
    private static final int OPTION_EXIT = 3;

    //counter for number of primitive operations
    static long counter = 0; 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != OPTION_EXIT) {
            // Display menu
            displayMenu();
            System.out.print("Enter your choice (1, 2, or 3): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Invalid input. Please enter an integer (1, 2, or 3).");
                scanner.nextLine(); 
                continue;
            }

            switch (choice) {
                case OPTION_NUMBERS:
                    System.out.println("\n=== Question 1: Radix Sort for Numbers ===");
                    runNumberProcessing(scanner);
                    break;
                case OPTION_WORDS:
                    System.out.println("\n=== Question 2: Radix Sort for Words ===");
                    runWordProcessing(scanner);
                    break;
                case OPTION_EXIT:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    // Method to display the menu options
    private static void displayMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Sort Numbers (Question 1)");
        System.out.println("2. Sort Words (Question 2)");
        System.out.println("3. Exit");
        System.out.println("==========================");
    }

    // === Question 1: Radix Sort for Numbers ===
    private static void runNumberProcessing(Scanner scanner) {
        // Get user input
        System.out.println("Enter the numbers separated by spaces (e.g. 275 087 426):");
        String input = scanner.nextLine();
        String[] oriArray = input.split("\\s+");
        int n = oriArray.length;

        // Validate input
        for (String num : oriArray) {
            if (!num.matches("\\d+")) {
                System.out.println("Error: Input must contain only numbers");
                return;
            }
        }

        //(Debug purpose) Printing original list of numbers
        System.out.print("\nOriginal array: ");
        for (String i : oriArray) {
            System.out.print(i + " ");
        }
        System.out.println("\n");

        //Use radix sort to sort the array
        String[] sortedArray = radixSortNumbers(oriArray);

        //(Debug purpose) Printing the sorted list of numbers
        System.out.print("Sorted array: ");
        for (String i : sortedArray) {
            System.out.print(i + " ");
        }
        System.out.println("\n");

        System.out.println("Complexity Analysis:");
        System.out.println("Number of elements (n): " + n);
        System.out.println("Total primitive operations: " + counter);
    }

    //to get the number of passes needed (number of digits)
    static int countNum(String oriArray[]) {
        int maxLength = 0;

        for (String i : oriArray) {
            if (i.length() > maxLength) {
                maxLength = i.length();
            }
        }
        return maxLength;
    }

    //Radix sort for numbers
    public static String[] radixSortNumbers(String oriArray[]) {
        //Get number of passes using countNum
        int numPass = countNum(oriArray);
        //Initialization
        //Create array1 and array2 with 10 buckets for digits 0-9
        ArrayList<ArrayList<String>> array1 = new ArrayList<>();
        ArrayList<ArrayList<String>> array2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            array1.add(new ArrayList<>());
            array2.add(new ArrayList<>());
        }

        // Alternate between array1 and array2 for each pass
        for (int pass = 0; pass < numPass; pass++) 
        {
            //set source and destination array for current pass
            //for example in second pass (pass=1) we read from array1 (source) and write to array2 (destination)
            ArrayList<ArrayList<String>> sourceArray = (pass % 2 == 0) ? array2 : array1;
            ArrayList<ArrayList<String>> destinationArray = (pass % 2 == 0) ? array1 : array2;

            //Clear the destination buckets before writing to them
            for (int i = 0; i < 10; i++) {
                destinationArray.get(i).clear();
            }
            
            //Flatten current source list
            List<String> currentArray = new ArrayList<>();
            if (pass == 0) {
                //Always take from input array for the first pass
                currentArray.addAll(Arrays.asList(oriArray)); 
            } else {
                //Take all numbers from source buckets for later passes
                for (int i = 0; i < 10; i++) {
                    currentArray.addAll(sourceArray.get(i));
                }
            }

            //Distribute numbers into buckets based on the current digit
            for (String s : currentArray) {
                //Pad left with '0' if needed
                while (s.length() < numPass) {
                    s = "0" + s;
                }
                //Get the correct digit from right
                int digitPositionFromRight = numPass - 1 - pass;
                char digitChar = s.charAt(digitPositionFromRight);
                int digit = digitChar - '0'; // Convert char to int

                //Add to the corresponding bucket in destination array
                destinationArray.get(digit).add(s);
            }

            //(Debug purpose) output after each pass
            System.out.println("After Pass " + (pass + 1) + " (place " + (int) Math.pow(10, pass) + "):");
            for (int i = 0; i < 10; i++) {
                if (!destinationArray.get(i).isEmpty()) {
                    System.out.print("Bucket " + i + ": ");
                    for (String str: destinationArray.get(i)) {
                        System.out.print(str + " ");
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }

        // Reorder by collecting from last used array
        //For example length of 3, numPass=3, so the last used array is array1
        ArrayList<ArrayList<String>> finalBuckets = (numPass % 2 == 0) ? array2 : array1;
        List<String> sortedArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sortedArray.addAll(finalBuckets.get(i));
        }

        return sortedArray.toArray(new String[0]);
    }

    // === Question 2: Radix Sort for Words ===
    private static void runWordProcessing(Scanner scanner) {
        //get user input
        System.out.println("Enter as many words as you like, separated by spaces:");
        String input = scanner.nextLine();
        String[] words = input.split("\\s+");
        int n = words.length;

        //Validate input
        if (words.length == 0) {
            System.out.println("No words were entered.");
            return;
        }

        //Use radix sort to sort the array
        String[] sortedWords = radixSortWords(words);


        //(Debug purpose) Printing the sorted list of words
        System.out.print("Sorted words: ");
        for (String word : sortedWords) {
            System.out.print(word + " ");  
        }
        System.out.println();  

        System.out.println("Number of words (n): " + n);
        System.out.println("Total operations: " + counter);
    }

    //to get the maximum length of the strings in the array (number of passes)
    private static int maxLength(String[] arr) {
        int maxLen = 0;
        for (String str : arr) {
            if (str.length() > maxLen) {
                maxLen = str.length();
            }
        }
        return maxLen;
    }

    // Radix sort for words
    public static String[] radixSortWords(String[] oriArray) {

        //Get number of passes using maxLength
        int numPass = maxLength(oriArray);

        //Initialization
        //Create array1 and array2 with 27 buckets (0 for space, 1–26 for a–z)
        ArrayList<ArrayList<String>> array1 = new ArrayList<>();
        ArrayList<ArrayList<String>> array2 = new ArrayList<>();

        for (int i = 0; i < 27; i++) { 
            array1.add(new ArrayList<>());
            array2.add(new ArrayList<>());
        }

        // Alternate between array1 and array2 for each pass
        for (int pass = 0; pass < numPass; pass++) {
            ArrayList<ArrayList<String>> sourceArray = (pass % 2 == 0) ? array2 : array1;
            ArrayList<ArrayList<String>> destArray = (pass % 2 == 0) ? array1 : array2;

            // Clear destination buckets
            for (int i = 0; i < 27; i++) {
                destArray.get(i).clear();
            }

            List<String> currentArray = new ArrayList<>();
            if (pass == 0) {
                //Always take from input array for the first pass
                currentArray.addAll(Arrays.asList(oriArray));
            } else {
                for (int i = 0; i < 27; i++) {
                    currentArray.addAll(sourceArray.get(i));
                }
            }

            //Distribute words into buckets based on the current character position
            for (String word : currentArray) {
                //Pad left with ' ' (space) if needed for shorter words
                while (word.length() < numPass) {
                    word = " " + word;
                }
                //Get the correct character from right
                int charPos = numPass - 1 - pass;
                char c = word.charAt(charPos);
                int bucketIndex = (c == ' ') ? 0 : (c - 'a' + 1); // 'Space → bucket 0, 'a'-'z' → buckets 1–26
                //Add to the corresponding bucket in destination array
                destArray.get(bucketIndex).add(word);
            }

            // Debug output after each pass
            System.out.println("After Pass " + (pass + 1) + " (character position " + (numPass - pass) + "):");
            for (int i = 0; i < 27; i++) {
                if (!destArray.get(i).isEmpty()) {
                    String label = (i == 0) ? " " : Character.toString((char) ('a' + i - 1));
                    System.out.print("Bucket " + label + ": ");
                    for (String str : destArray.get(i)) {
                        System.out.print(str + " ");
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }

        // Collect result from final bucket array
        ArrayList<ArrayList<String>> finalBuckets = (numPass % 2 == 0) ? array2 : array1;
        List<String> sortedArray = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            sortedArray.addAll(finalBuckets.get(i));
        }

        // Trim padding
        for (int i = 0; i < sortedArray.size(); i++) {
            sortedArray.set(i, sortedArray.get(i).strip());
        }

        return sortedArray.toArray(new String[0]);
    }
}

   
