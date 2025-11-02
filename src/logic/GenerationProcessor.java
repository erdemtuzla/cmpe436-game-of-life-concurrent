package logic;

import components.*;
import utility.concurrency.*;

public class GenerationProcessor {
    private Grid currentGrid;
    private Grid nextGrid;
    private int generationCount;
    private int currentGeneration = 0;

    private Barrier calculationBarrier;
    private Barrier swapBarrier;
    private CellThread[] threads;

    public GenerationProcessor(int[] gridSize, int generationCount) {
        this.currentGrid = new Grid(gridSize[0], gridSize[1], true);
        this.nextGrid = new Grid(currentGrid.gerRowCount(), currentGrid.getColCount(), false);
        this.generationCount = generationCount;

        int row = currentGrid.gerRowCount();
        int col = currentGrid.getColCount();
        int threadCount = row * col;
        threads = new CellThread[threadCount];

        calculationBarrier = new Barrier(threadCount + 1);
        swapBarrier = new Barrier(threadCount + 1);

        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                threads[index++] = new CellThread(i, j, generationCount,
                        currentGrid, nextGrid,
                        calculationBarrier, swapBarrier);
            }
        }

        for (CellThread thread : threads) {
            thread.start();
        }
    }

    public void process() {
        printCurrentGeneration();

        for (int i = 0; i < generationCount; i++) {
            // 1- Wait for all threads to calculate next values
            calculationBarrier.await();

            // 2- Swap the current grid with the next grid
            Grid temp = currentGrid;
            currentGrid = nextGrid;
            nextGrid = temp;
            nextGrid.clearGrid();

            // Update the grids of all threads
            for (CellThread thread : threads) {
                thread.setCurrentGrid(currentGrid);
                thread.setNextGrid(nextGrid);
            }

            System.out.println("-----------------------------------------");
            System.out.println("Current Generation: " + (i + 1));
            currentGrid.printGrid();
            System.out.println("-----------------------------------------");

            swapBarrier.await();
        }

        for (CellThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printCurrentGeneration() {
        System.out.println("-----------------------------------------");
        System.out.println("Current Generation: " + currentGeneration);

        currentGrid.printGrid();

        System.out.println("-----------------------------------------");
    }
}
