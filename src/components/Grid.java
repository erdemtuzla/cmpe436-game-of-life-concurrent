package components;

public class Grid {
    private Cell[][] grid;
    private int rowCount;
    private int colCount;

    public Grid(int rowCount, int columnCount, boolean randomGeneration) {
        this.rowCount = rowCount;
        this.colCount = columnCount;
        grid = new Cell[rowCount][colCount];

        if (randomGeneration) {
            // Fill the grid
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    grid[i][j] = new Cell();
                }
            }
        }
    }

    public int gerRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public Cell getCell(int row, int column) {
        return grid[row][column];
    }

    public void setCell(int row, int col, Cell cell) {
        if (isCoordinateValid(row, col)) {
            grid[row][col] = cell;
        }
    }

    public void printGrid() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                System.out.print(grid[i][j].getValue() + "\t");
            }

            System.out.println("\n");
        }
    }

    public boolean isCoordinateValid(int row, int col) {
        if (row < 0 || row >= this.rowCount ||
                col < 0 || col >= this.colCount) {
            return false;
        }
        return true;
    }

    public void clearGrid() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                grid[i][j] = new Cell(0);
            }
        }
    }
}
