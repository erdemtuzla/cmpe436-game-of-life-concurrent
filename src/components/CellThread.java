package components;

import utility.concurrency.Barrier;
import utility.helpers.NeighborOffset;

public class CellThread extends Thread {
    private final int row;
    private final int col;
    private final int generationCount;

    // Shared
    private Grid currentGrid;
    private Grid nextGrid;

    private final Barrier calculationBarrier;
    private final Barrier swapBarrier;

    public CellThread(int row, int col, int generationCount, Grid currentGrid, Grid nextGrid,
            Barrier calculationBarrier, Barrier swapBarrier) {
        this.row = row;
        this.col = col;
        this.generationCount = generationCount;
        this.currentGrid = currentGrid;
        this.nextGrid = nextGrid;
        this.calculationBarrier = calculationBarrier;
        this.swapBarrier = swapBarrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < generationCount; i++) {
            // 1- Calculate
            int neighbors = countMyNeighbors();
            Cell nexCell = calculateNextGenerationValue(neighbors);
            nextGrid.setCell(row, col, nexCell);

            // 2- Wait for other threads to finish their processess
            calculationBarrier.await();

            // 3- Wait for swapping process between the nextGrid and the currentGrid
            swapBarrier.await();
        }
    }

    private int countMyNeighbors() {
        int count = 0;

        for (NeighborOffset offset : NeighborOffset.values()) {
            int neighborRow = row + offset.getDeltaRow();
            int neighborCol = col + offset.getDeltaCol();

            if (!currentGrid.isCoordinateValid(neighborRow, neighborCol)) {
                continue;
            }

            if (currentGrid.getCell(neighborRow, neighborCol).getValue() == 1) {
                count++;
            }
        }

        return count;
    }

    private Cell calculateNextGenerationValue(int neighbors) {
        int currentValue = currentGrid.getCell(row, col).getValue();
        int nextValue = currentValue;

        if (currentValue == 1) {
            if (!(neighbors == 2 || neighbors == 3)) {
                nextValue = 0;
            }
        }

        if (currentValue == 0) {
            if (neighbors == 3) {
                nextValue = 1;
            }
        }

        return new Cell(nextValue);
    }

    public void setCurrentGrid(Grid grid) {
        this.currentGrid = grid;
    }

    public void setNextGrid(Grid grid) {
        this.nextGrid = grid;
    }
}
