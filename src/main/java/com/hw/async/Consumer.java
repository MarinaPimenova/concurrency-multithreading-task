package com.hw.async;

public class Consumer implements Runnable {
    private final MessageBus bus;
    private final String topic;

    public Consumer(MessageBus bus, String topic) {
        this.bus = bus;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = bus.consume(topic);
                System.out.println("Consumed [" + topic + "]: " + message.payload());
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }
    }
}