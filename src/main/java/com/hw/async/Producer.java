package com.hw.async;

import java.util.Random;

public class Producer implements Runnable {
    private final MessageBus bus;
    private final String[] topics;
    private final Random random = new Random();

    public Producer(MessageBus bus, String[] topics) {
        this.bus = bus;
        this.topics = topics;
    }

    @Override
    public void run() {
        while (true) {
            String topic = topics[random.nextInt(topics.length)];
            String payload = "Message-" + random.nextInt(1000);
            bus.post(new Message(topic, payload));
            System.out.println("Produced: [" + topic + "] " + payload);
            try {
                Thread.sleep(200); // simulate work
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }
    }
}
