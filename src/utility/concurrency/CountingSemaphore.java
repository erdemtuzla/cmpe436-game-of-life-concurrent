package utility.concurrency;

public class CountingSemaphore {
    private int value;

    // Constructor
    public CountingSemaphore(int initialValue) {
        if (initialValue < 0) {
            throw new IllegalArgumentException("Initial value shouldn't be negative!");
        }

        this.value = initialValue;
    }

    // The P() operation (wait or acquire)
    public synchronized void P() {
        while (this.value == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.value--;
    }

    // The V() operation (signal or release)
    public synchronized void V() {
        this.value++;

        notifyAll();
    }
}