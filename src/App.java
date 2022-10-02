import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {

    // The below method was to help test out the program in the early stages of
    // development and now looks squished after formatting the document
    /*
     * public static void printArray(int[] array) {
     * for (int a: array){
     * System.out.print(a + " ");
     * }
     * System.out.println();
     * }
     */

    // Ensures that the user enters a valid name for a .txt file
    public static String getTxtFileName(Scanner scnr) {
        String input = scnr.next();
        if (input.length() < 5) {
            System.out.println("Please input a file name that ends with \".txt\"");
            input = getTxtFileName(scnr);
        } else if (!(input.subSequence(input.length() - 4, input.length()).equals(".txt"))) {
            System.out.println("Please input a file name that ends with \".txt\"");
            input = getTxtFileName(scnr);
        }

        return input;
    }

    public static int[] createRandomArray(int arrayLength) {
        int[] intArray = new int[arrayLength];
        Random random = new Random();

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = random.nextInt(100);
        }

        return intArray;
    }

    public static void writeArrayToFile(int[] array, String filename) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filename);
            for (int i : array) {
                fileWriter.write(i + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Encountered an IOException.");
            e.printStackTrace();
        }

    }

    public static int[] readFileToArray(String filename) {
        File file = new File(filename);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                int a = Integer.parseInt(str);
                arrayList.add(a);
            }

            int[] intArray = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                intArray[i] = arrayList.get(i);
            }

            scanner.close();
            return intArray;
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong. Perhaps the file does not exist?");
            e.printStackTrace();
        }
        return null;
    }

    public static void bubbleSort(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j + 1] < array[j]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in);

        System.out.println("\nWelcome to the integer sorter!\n");
        System.out.println("Please type \"help\" for more information on how to use this program,");
        System.out.println("or simply type \"read\" or \"generate\" to start sorting your lists of integers!");
        System.out.println("Remember to type \"quit\" when you are done and ready to quit!\n");
        while (true) {
            System.out.println("What would you like to do?");
            String input = scnr.next();

            if (input.contains("read")) {
                System.out.println("Please input the name of the .txt file that you would like to read from.");
                String fileName = getTxtFileName(scnr);
                int[] array = readFileToArray(fileName);

                bubbleSort(array);

                System.out.println(
                        "Please input the name of a .txt file where you would like to store the sorted list of integers.");
                fileName = getTxtFileName(scnr);
                writeArrayToFile(array, fileName);

                System.out.println("Done!\n");
            } else if (input.contains("generate")) {
                System.out.println(
                        "Please input the name of a .txt file where you would like to store the sorted list of integers.");
                String fileName = getTxtFileName(scnr);
                System.out.println(
                        "How many random integers would you like to create? (Please enter a positive integer value.)");
                int length;
                try {
                    String str = scnr.next();
                    length = Integer.parseInt(str);
                    if (length > 0) {
                        int[] array = createRandomArray(length);
                        writeArrayToFile(array, fileName);
                        System.out
                                .println("Would you like to sort this file as well? (Please input \"yes\" or \"no\".)");
                        str = scnr.next();
                        if (str.contains("yes")) {
                            bubbleSort(array);

                            System.out.println(
                                    "Please input the name of a .txt file where you would like to store the sorted list of integers.");
                            fileName = getTxtFileName(scnr);
                            writeArrayToFile(array, fileName);

                            System.out.println("Done!\n");
                        } else if (str.contains("no")) {
                            System.out.println("The list will not be sorted.\n");
                        } else {
                            System.out.println(
                                    "Invalid input! \nThe list will not be sorted. Please read from the created list (\""
                                            + fileName + "\") if you wish to sort it in the future.\n");
                        }

                    } else {
                        System.out.println("Failure to input a positive integer value.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Failure to input a positive integer value.\n");
                }

            }
            // Displays information about the program's commands to the user
            else if (input.contains("help")) {
                System.out.println("The below are the commands which this program accepts:");
                System.out.println("\"help\": Displays this informational text.");
                System.out.println("\"quit\": Quits the program.");
                System.out.println(
                        "\"read\": Will sort an existing list of integers from an existing .txt file \n\tand save the sorted list of integers in a .txt file. \n\tThe file must have one integer per line.");
                System.out.println(
                        "\"generate\": Generates a new list of random integers from 0 to 100 (including 0, excluding 100) \n\tand saves the list to a .txt file. \n\tThe user will be able to specify the length of the list \n\tand if the user would like to sort this list and store it as a separate .txt file.\n");
            }
            // Quits the program
            else if (input.contains("quit")) {
                System.out.println("\nGoodbye!");
                break;
            }
        }

        scnr.close();
    }
}
