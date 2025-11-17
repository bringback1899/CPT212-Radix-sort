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

        if (n == 0) {
         System.out.println("No numbers were entered. ");
         return; 
        }
        
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

        counter = 0; // RESET COUNTER FOR ALGORITHM
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
        System.out.println("Total primitive operations (Algorithm only): " + counter);
    }

    //to get the number of passes needed (number of digits)
    static int countNum(String oriArray[]) {
        counter++; // assignment
        int maxLength = 0;

        counter++; // loop initialization
        for (String i : oriArray) {
            counter += 3; // assignment, comparison, and increment
            counter +=2; // call length fucntion and comparison
            if (i.length() > maxLength) {
                counter += 2; // call length fucntion and assignment
                maxLength = i.length();
            }
        }
        counter++; // final loop check
        counter++; // return
        return maxLength;
    }

    //Radix sort for numbers
    public static String[] radixSortNumbers(String oriArray[]) {
        //Get number of passes using countNum
        counter+=2; // call countNum and assignment
        int numPass = countNum(oriArray);

        //Initialization
        //Create array1 and array2 with 10 buckets for digits 0-9
        counter+=2; // assignment array1 and array2
        ArrayList<ArrayList<String>> array1 = new ArrayList<>();
        ArrayList<ArrayList<String>> array2 = new ArrayList<>();

        counter++; // loop initialization
        for (int i = 0; i < 10; i++) {
            counter+=3; // assignment, loop condition, and increment
            counter+=2; // call add to array1 and array2
            array1.add(new ArrayList<>());
            array2.add(new ArrayList<>());
        }
        counter++; // final loop check

        // Alternate between array1 and array2 for each pass
        counter++; // loop initialization
        for (int pass = 0; pass < numPass; pass++) 
        {
            counter+=3; // assignment, loop condition, and increment
            
            //set source and destination array for current pass
            //for example in second pass (pass=1) we read from array1 (source) and write to array2 (destination)
            counter+=3; // modulus, comparison and assignment
            ArrayList<ArrayList<String>> sourceArray = (pass % 2 == 0) ? array2 : array1;
            counter+=3; // modulus, comparison and assignment
            ArrayList<ArrayList<String>> destinationArray = (pass % 2 == 0) ? array1 : array2;

            //Clear the destination buckets before writing to them
            counter++; // loop initialization
            for (int i = 0; i < 10; i++) {
                counter+=3; // assignment, loop condition, and increment
                counter+=2; // call get and clear
                destinationArray.get(i).clear();
            }
            counter++; // final loop check
            
            //Flatten current source list
            counter++; // assignment
            List<String> currentArray = new ArrayList<>();

            counter++; // comparison
            if (pass == 0) {
                //Always take from input array for the first pass
                counter+=2; // call asList and addAll
                currentArray.addAll(Arrays.asList(oriArray)); 
            } else {
                //Take all numbers from source buckets for later passes
                counter++; // loop initialization
                for (int i = 0; i < 10; i++) {
                    counter+=3; // assignment, loop condition, and increment
                    counter+=2; // call get and addAll
                    currentArray.addAll(sourceArray.get(i));
                }
                counter++; // final loop check
            }

            //Distribute numbers into buckets based on the current digit
            counter++; // loop initialization
            for (String s : currentArray) {
                counter+=3; // assignment, loop condition, and increment
                
                //Pad left with '0' if needed
                counter+=2; // call length and comparison
                while (s.length() < numPass) {
                    counter+=2; // concatenation and assignment
                    s = "0" + s;
                    counter+=2; // call length and comparison
                }

                //Get the correct digit from right
                counter+=3; // subtractions and assignment
                int digitPositionFromRight = numPass - 1 - pass;
                counter+=2; // call charAt and assignment
                char digitChar = s.charAt(digitPositionFromRight);
                counter+=2; // subtraction and assignment
                int digit = digitChar - '0'; // Convert char to int

                //Add to the corresponding bucket in destination array
                counter+=2; // call get and add
                destinationArray.get(digit).add(s);
            }
            counter++; // final loop check

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
        counter++; // final loop check

        // Reorder by collecting from last used array
        //For example length of 3, numPass=3, so the last used array is array1
        counter+=3; // modulus, comparison and assignment
        ArrayList<ArrayList<String>> finalBuckets = (numPass % 2 == 0) ? array2 : array1;
        counter++; // assignment
        List<String> sortedArray = new ArrayList<>();
        counter++; // loop initialization
        for (int i = 0; i < 10; i++) {
            counter+=3; // assignment, loop condition, and increment
            counter+=2; // call get and addAll
            sortedArray.addAll(finalBuckets.get(i));
        }
        counter++; // final loop check
        counter+=2; // call toArray and return
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

        // Ensure all words are made of only lowercase letters a-z
        for (String word : words) {
            if (!word.matches("[a-z]+")) {
                System.out.println("Error: All words must consist of lowercase letters only (a-z).");
                return;
            }
        }

        //(Debug purpose) Printing original list of words
        System.out.print("\nOriginal words: ");
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println("\n");

        counter = 0; // RESET COUNTER FOR ALGORITHM
        //Use radix sort to sort the array
        String[] sortedWords = radixSortWords(words);


        //(Debug purpose) Printing the sorted list of words
        System.out.print("Sorted words: ");
        for (String word : sortedWords) {
            System.out.print(word + " ");  
        }
        System.out.println();  

        System.out.println("Number of words (n): " + n);
        System.out.println("Total primitive operations (algorithm only): " + counter);
    }

    //to get the maximum length of the strings in the array (number of passes)
    private static int maxLength(String[] arr) {
        counter++; // assignment
        int maxLen = 0;
        counter++; // loop initialization
        for (String str : arr) {
            counter+=3; // assignment, comparison, and increment
            counter+=2; // call length function and comparison
            if (str.length() > maxLen) {
                counter+=2; // call length function and assignment
                maxLen = str.length();
            }
        }
        counter++; // final loop check
        counter++; // return
        return maxLen;
    }

    // Radix sort for words
    public static String[] radixSortWords(String[] oriArray) {

        //Get number of passes using maxLength
        counter+=2; // call maxLength and assignment
        int numPass = maxLength(oriArray);

        //Initialization
        //Create array1 and array2 with 27 buckets (0 for space, 1–26 for a–z)
        counter+=2; // assignment array1 and array2
        ArrayList<ArrayList<String>> array1 = new ArrayList<>();
        ArrayList<ArrayList<String>> array2 = new ArrayList<>();

        counter++; // loop initialization
        for (int i = 0; i < 27; i++) { 
            counter+=3; // assignment, loop condition, and increment
            counter+=2; // call add to array1 and array2
            array1.add(new ArrayList<>());
            array2.add(new ArrayList<>());
        }
        counter++; // final loop check

        // Alternate between array1 and array2 for each pass
        counter++; // loop initialization
        for (int pass = 0; pass < numPass; pass++) {
            counter+=3; // assignment, loop condition, and increment
            //set source and destination array for current pass
            //for example in second pass (pass=1) we read from array1 (source) and write to array2 (destination)
            counter+=3; // modulus, comparison and assignment
            ArrayList<ArrayList<String>> sourceArray = (pass % 2 == 0) ? array2 : array1;
            counter+=3; // modulus, comparison and assignment
            ArrayList<ArrayList<String>> destArray = (pass % 2 == 0) ? array1 : array2;

            // Clear destination buckets
            counter++; // loop initialization
            for (int i = 0; i < 27; i++) {
                counter+=3; // assignment, loop condition, and increment
                counter+=2; // call get and clear
                destArray.get(i).clear();
            }
            counter++; // final loop check

            counter++; // assignment    
            List<String> currentArray = new ArrayList<>();

            counter++; // comparison
            if (pass == 0) {
                //Always take from input array for the first pass
                counter+=2; // call asList and addAll
                currentArray.addAll(Arrays.asList(oriArray));
            } else {
                //Take all words from source buckets for later passes
                counter++; // loop initialization
                for (int i = 0; i < 27; i++) {
                    counter+=3; // assignment, loop condition, and increment
                    counter+=2; // call get and addAll
                    currentArray.addAll(sourceArray.get(i));
                }
                counter++; // final loop check
            }

            //Distribute words into buckets based on the current character position
            counter++; // loop initialization
            for (String word : currentArray) {
                counter+=3; // assignment, loop condition, and increment

                //Pad left with ' ' (space) if needed for shorter words
                counter+=2; // call length and comparison
                while (word.length() < numPass) {
                    counter+=2; // concatenation and assignment
                    word = " " + word;
                    counter+=2; // call length and comparison
                }

                //Get the correct character from right
                counter+=3; // subtractions and assignment
                int charPos = numPass - 1 - pass;
                counter+=2; // call charAt and assignment
                char c = word.charAt(charPos);
                counter+=3; // comparison, arithmetic, and assignment
                int bucketIndex = (c == ' ') ? 0 : (c - 'a' + 1); // 'Space → bucket 0, 'a'-'z' → buckets 1–26
                
                //Add to the corresponding bucket in destination array
                counter+=2; // call get and add
                destArray.get(bucketIndex).add(word);
            }
            counter++; // final loop check

            // (Debug purpose)output after each pass
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
        counter++; // final loop check

        // Collect result from final bucket array
        counter+=3; // modulus, comparison and assignment
        ArrayList<ArrayList<String>> finalBuckets = (numPass % 2 == 0) ? array2 : array1;
        counter++; // assignment
        List<String> sortedArray = new ArrayList<>();
        counter++; // loop initialization
        for (int i = 0; i < 27; i++) {
            counter+=3; // assignment, loop condition, and increment
            counter+=2; // call get and addAll
            sortedArray.addAll(finalBuckets.get(i));
        }
        counter++; // final loop check

        // Trim padding
        counter++; // loop initialization
        for (int i = 0; i < sortedArray.size(); i++) {
            counter+=4; // assignment, call size, loop condition, and increment
            counter+=4; // call get,trim, set and assignment
            sortedArray.set(i, sortedArray.get(i).trim());
        }
        counter++; // final loop check
        counter+=2; // call toArray and return
        return sortedArray.toArray(new String[0]);
    }
}

   
