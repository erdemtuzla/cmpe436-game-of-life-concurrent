package utility.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {
    private BufferedReader reader;

    public InputReader(String fileName) throws IOException {
        this.reader = new BufferedReader(new FileReader(fileName));
    }

    public int[] readGridSize() throws IOException {
        String line = reader.readLine();

        if (line == null) {
            throw new IOException("File is empty or in a wrong format!");
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2) {
            throw new IOException("Unexpected amount of data in dimension line!");
        }

        try {
            int rowCount = Integer.parseInt(parts[0]);
            int colCount = Integer.parseInt(parts[1]);

            if (rowCount <= 0 || colCount <= 0) {
                throw new IOException("Dimensions should be positive integers!");
            }

            return new int[] { rowCount, colCount };
        } catch (Exception e) {
            throw new IOException("Something wrong happened while reading the input file. Please check its format.");
        }
    }

    public int readGenerationCount() throws IOException {
        String line = reader.readLine();

        if (line == null) {
            throw new IOException("File is truncated. Cannot read generation count.");
        }

        try {
            int genCount = Integer.parseInt(line.trim());
            
            if (genCount < 0) {
                throw new IOException("Generation count cannot be negative.");
            }

            return genCount;
        } catch (NumberFormatException e) {
            throw new IOException("Generation count is not a valid integer.", e);
        }
    }

    public int[][] readGrid(int rows, int cols) throws IOException {
        int[][] grid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("File truncated. Grid data is incomplete.");
            }

            String[] parts = line.trim().split("\\s+");
            if (parts.length < cols) {
                throw new IOException("Row " + i + " is too short. Expected " + cols + " columns.");
            }

            for (int j = 0; j < cols; j++) {
                try {
                    int value = Integer.parseInt(parts[j]);
                    
                    if (value != 0 && value != 1) {
                        throw new IOException("Invalid cell value at [" + i + "][" + j + "]: " + value);
                    }
                    grid[i][j] = value;
                } catch (NumberFormatException e) {
                    throw new IOException("Non-integer value in grid at [" + i + "][" + j + "].", e);
                }
            }
        }
        return grid;
    }

}
