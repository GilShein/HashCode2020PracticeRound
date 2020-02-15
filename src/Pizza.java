import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Pizza {
    static final String[] files = {"in/a_example.in", "in/b_small.in", "in/c_medium.in", "in/d_quite_big.in", "in/e_also_big.in"};
    static final String[] output = {"out/a_example.out", "out/b_small.out", "out/c_medium.out", "out/d_quite_big.out", "out/e_also_big.out"};

    public static void main(String[] args) throws FileNotFoundException {
        long totalSum = 0;
        long totalMax = 0;
        //read from Files into String data
        for (int i = 0; i < files.length; i++) {
            File fileToRead = new File(files[i]);
            Scanner reader = new Scanner(fileToRead);

            int maxPizzaSlices = 0;
            int numOfPizzaTypes = 0;
            int[] numOfSlicesEachType = new int[0];
            boolean firstLine = true;

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] tokens = data.trim().split("\\s+"); //split data by whitespace regex into tokens
                if (firstLine) {
                    maxPizzaSlices = Integer.parseInt(tokens[0]);
                    numOfPizzaTypes = Integer.parseInt(tokens[1]);
                } else {
                    numOfSlicesEachType = Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray(); //convert String Array into Integer Array
                }
                firstLine = false;

            }

            reader.close();
            long sumScore = runAlgorithm(maxPizzaSlices, numOfPizzaTypes, numOfSlicesEachType, output[i]);
            System.out.println("File_" + (i + 1) + ": " + sumScore + "/" + maxPizzaSlices);
            totalSum += sumScore;
            totalMax += maxPizzaSlices;

        }
        System.out.println("Total Sum: " + totalSum + "/" + totalMax);
    }

    private static long runAlgorithm(int maxPizzaSlices, int numOfPizzaTypes, int[] numOfSlicesEachType, String outputFileName) {
        int sum = 0;
        int typesCounter = 0;
        ArrayList<Integer> results = new ArrayList<>();
        ArrayList<Integer> score = new ArrayList<>();
        if (results.size() > numOfPizzaTypes) throw new IllegalStateException("BUG!!");
        for (int i = numOfSlicesEachType.length - 1; i >= 0; i--) {
            if (sum + numOfSlicesEachType[i] < maxPizzaSlices) {
                sum += numOfSlicesEachType[i];
                typesCounter++;
                results.add(i);
                score.add(numOfSlicesEachType[i]);
            }
        }
        Collections.sort(results);
        String output = results.stream().map(Object::toString).collect(Collectors.joining(" "));
        PrintToFile(typesCounter, output, outputFileName);
        return calcScore(score);
    }

    private static long calcScore(ArrayList<Integer> score) {
        return score.stream().mapToLong(a -> a).sum();
    }

    private static void PrintToFile(int typesCounter, String output, String outputFileName) {
        try (PrintWriter out = new PrintWriter(outputFileName)) { //try with resources
            out.println(typesCounter);
            out.println(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}

