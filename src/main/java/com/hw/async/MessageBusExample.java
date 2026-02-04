package com.hw.async;

public class MessageBusExample {
    public static void main(String[] args) {
        MessageBus bus = new MessageBus();
        String[] topics = {"Sports", "Weather", "News"};

        // Start producers
        for (int i = 0; i < 3; i++) {
            new Thread(new Producer(bus, topics)).start();
        }

        // Start consumers
        for (String topic : topics) {
            new Thread(new Consumer(bus, topic)).start();
        }
    }
}
