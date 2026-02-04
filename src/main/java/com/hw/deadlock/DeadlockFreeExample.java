package com.hw.deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeadlockFreeExample {

    // Shared collection
    private final List<Integer> numbers = new ArrayList<>();
    // Single lock object
    private final Object lock = new Object();

    public static void main(String[] args) {
        new DeadlockFreeExample().startThreads();
    }

    public void startThreads() {
        // Thread 1: write random numbers
        Thread writer = new Thread(() -> {
            Random random = new Random();
            while (true) {
                int num = random.nextInt(100); // random number 0-99
                synchronized (lock) {          // lock collection for writing
                    numbers.add(num);
                }
                try {
                    Thread.sleep(100); // small delay
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }
        });

        // Thread 2: print sum of numbers
        Thread sumPrinter = new Thread(() -> {
            while (true) {
                int sum;
                synchronized (lock) {   // lock collection for reading
                    sum = numbers.stream().mapToInt(Integer::intValue).sum();
                }
                System.out.println("Sum: " + sum);
                try {
                    Thread.sleep(500); // print every 0.5 sec
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }
        });

        // Thread 3: print sqrt of sum of squares
        Thread sqrtPrinter = new Thread(() -> {
            while (true) {
                double sqrtSumSquares;
                synchronized (lock) {   // lock collection for reading
                    sqrtSumSquares = Math.sqrt(numbers.stream()
                            .mapToDouble(n -> n * n)
                            .sum());
                }
                System.out.println("Sqrt of sum of squares: " + sqrtSumSquares);
                try {
                    Thread.sleep(500); // print every 0.5 sec
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }
        });

        // Start all threads
        writer.start();
        sumPrinter.start();
        sqrtPrinter.start();
    }
}

