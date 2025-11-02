package utility.concurrency;

/**
 * This class implements a two-phase barrier to synchronize a fixed number
 * of threads (N).
 *
 * This barrier uses a "two-gate" system to solve the problem
 * of "lapping" or race conditions. .
 *
 * [[Initial State]]
 * - n: The total number of threads to wait for.
 * - count: The number of threads currently waiting at the barrier.
 * - mutex: A semaphore (value 1) to protect the 'count' variable.
 * - gate1 (Entry Gate):  Locked (Semaphore value 0)
 * - gate2 (Exit Gate):   Open   (Semaphore value 1)
 *
 * [[How it Works (The 'await()' func)]]
 ***********************************************************************
 *
 * PHASE 1: Arriving at the Barrier
 * 1. All (N-1) threads arrive, increment the counter, and block on `gate1.P()`
 * (which is locked).
 * 
 * 2. The Nth (last) thread arrives and increments the counter to N.
 * 
 * 3. It first calls `gate2.P()` to LOCK the EXIT GATE.
 * This ensures all threads (including itself) will get trapped in Phase 2.
 * 
 * 4. It then calls `gate1.V()` to OPEN the entry gate.
 * 
 * 5. All N threads are released one-by-one via the `gate1.P(); gate1.V();`
 * They are now "in" the barrier and proceed to Phase 2.
 *
 * **************************************************************************
 * 
 * PHASE 2: Resetting and Leaving the Barrier
 * 1. All (N-1) threads arrive at `gate2.P()` and block (because the last
 * thread in Phase 1 just locked it).
 * 
 * 2. The Nth (last) thread arrives and decrements the counter to 0.
 * 
 * 3. It first calls `gate1.P()` to LOCK the entry gate, resetting it
 * to its initial state for the *next* generation.
 * 
 * 4. It then calls `gate2.V()` to OPEN the exit gate.
 * 
 * 5. All N threads are released one-by-one via the `gate2.P(); gate2.V();`
 * and can now safely proceed.
 * 
 */

public class Barrier {
    // Thread count
    private final int n;
    private int count;

    private final CountingSemaphore mutex;
    private final CountingSemaphore gate1;
    private final CountingSemaphore gate2;

    public Barrier (int n) {
        this.n = n;
        this.count = 0;
        this.mutex = new CountingSemaphore(1);
        this.gate1 = new CountingSemaphore(0);
        this.gate2 = new CountingSemaphore(1);
    }

    public void await() {
        // When entered, grab the mutex and increment the count
        mutex.P();
        count++;

        // The last arrived thread, closses the gate2, while opening the gate 1
        if (count == n) {
            // Last thread arrived, open the gate 1
            gate2.P();
            gate1.V();
        }

        //
        mutex.V();

        // All threads wait here
        gate1.P();
        gate1.V();

        // Phase 2
        mutex.P();
        count--;

        if (count == 0) {
            gate1.P();
            gate2.V();
        }

        mutex.V();

        gate2.P();
        gate2.V();
    }
}
