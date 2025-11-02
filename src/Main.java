// Erdem Tuzla
// 2024700114
// erdem.tuzla@std.bogazici.edu.tr
// CMPE436-Assignment 2

import java.io.IOException;
import utility.file.InputReader;
import logic.GenerationProcessor;

public class Main {

    public static void main(String[] args) {
        // Student information
        System.out.println("-----------------------------------------");
        System.out.println(
                "| CMPE436 Assignment-3 HW1-Q2 \t\t| \n| Erdem Tuzla - 2024700114\t\t|\n| erdem.tuzla@std.bogazici.edu.tr\t|");
        System.out.println("-----------------------------------------");

        try {
            InputReader reader = new InputReader(args[0]);

            // Read file and get required data for initialization
            int[] gridSize = reader.readGridSize();
            int generationCount = reader.readGenerationCount();

            GenerationProcessor processor = new GenerationProcessor(gridSize, generationCount);

            processor.process();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}