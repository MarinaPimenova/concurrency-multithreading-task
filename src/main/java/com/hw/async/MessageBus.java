package com.hw.async;

import java.util.ArrayList;
import java.util.List;

public class MessageBus {
    private final List<Message> messages = new ArrayList<>();

    public synchronized void post(Message message) {
        messages.add(message);
        notifyAll(); // notify consumers that a new message arrived
    }

    public synchronized Message consume(String topic) throws InterruptedException {
        while (true) {
            for (int i = 0; i < messages.size(); i++) {
                if (messages.get(i).topic().equals(topic)) {
                    return messages.remove(i);
                }
            }
            wait(); // wait if no message for this topic
        }
    }
}
