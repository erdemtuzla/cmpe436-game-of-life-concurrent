- Build: `javac -d build -sourcepath src src/Main.java`
- Run: `java -cp build Main input.txt`
- Example: `javac -d build -sourcepath src src/Main.java && java -cp build Main input.txt`

---

# CMPE436 Game of Life Assignment
- This project created for the Assignment-1 Question-2 of CMPE436 course
- It reads the input.txt and does the things below:
    - Gets row count and column count from the input.txt for the `Grid`
    - Randomly puts 0 or 1 in each `Cell` in the `Grid`
    - Gets generation count from the input.txt
    - Applies these rules in each generation:
        - A 1 cell value stays 1 if exactly two or three neighbors are 1 valued.
        - A 1 cell value goes to 0 if less than two or greater than three neighbors are 1 valued.
        - A 0 cell value goes to 1 if exactly three neighbors are 1 valued.
        - A 0 cell value stays 0 if less than three or greater than three neighbors are 1 valued

## Updates for the concurrency
- All `Cell` objects are now controller by `CellThread` objects
- The rule logic moved into `CellThread` objects as they are the ones that responsible from it
- Introduced a simple `CountingSemaphore` implementation.
- Introduced `Barrier` class. It is synchronizing the threads by using 3 `CountingSemaphore` implementation. A more in-depth explanation of how it works can be found in the file itself