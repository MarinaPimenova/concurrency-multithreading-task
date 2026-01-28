# Task 2 - Deadlock-Free Multithreaded Java Example

## Task Description
The task is to implement a multithreaded Java program with three threads operating on a shared collection:

1. **Writer Thread**: Continuously writes random numbers into a collection.
2. **Sum Printer Thread**: Continuously calculates and prints the sum of all numbers in the collection.
3. **Square Root Printer Thread**: Continuously calculates and prints the square root of the sum of squares of all numbers in the collection.

The program must ensure **thread safety** when accessing the shared collection and avoid **deadlocks**.

---

## Approach Used
- A single shared `ArrayList<Integer>` is used as the collection.
- A single **lock object** is used to synchronize all access to the collection.
- Each thread uses a `synchronized` block on this lock whenever it **reads from** or **writes to** the collection.
- Random numbers are added continuously by the writer thread, while the other threads read and compute their respective results at regular intervals.

This approach guarantees that no two threads can modify or read the collection simultaneously in an unsafe way.

---

## Why This Is Deadlock-Free
- All threads synchronize on **the same lock object**, so no thread can hold one lock and wait for another.
- There is **no nested locking** or multiple locks with different acquisition orders.
- Since threads always acquire the lock in the same consistent way, there is **no possibility of circular waiting**, which is the main cause of deadlocks.

Thus, the program is both **thread-safe** and **deadlock-free**.

---

## Usage
1. Compile and run the Java program.
2. Observe the writer thread adding random numbers.
3. Observe the sum printer and square root printer threads calculating and printing results continuously.

---

# Task 3 - Where’s Your Bus, Dude? – Asynchronous Message Bus

## Task Description
The goal of this task is to implement a **message bus** using the **Producer-Consumer pattern** in Java, without using `java.util.concurrent` queue implementations.

The program must:

1. Allow multiple **producers** to generate and post messages to the bus asynchronously.
2. Allow multiple **consumers** to subscribe to specific **topics** and consume messages.
3. Run producers and consumers **in parallel**, handling messages asynchronously and safely.

Each message consists of:

- `topic` (String)
- `payload` (String)

---

## Approach Used
- A custom **MessageBus** class stores messages in a synchronized `ArrayList`.
- Producers generate random messages for random topics and post them to the bus using a **synchronized `post()` method**.
- Consumers subscribe to a topic and retrieve messages using a **synchronized `consume(topic)` method**:
    - If no message is available for the topic, the consumer **waits**.
    - Producers call `notifyAll()` after posting, waking up waiting consumers.
- This approach ensures **thread safety** and **asynchronous message delivery** without external concurrency utilities.

---

## Why This Is Thread-Safe and Asynchronous
- **Thread Safety**:
    - All access to the internal message list is synchronized.
    - Only one thread can add or remove messages at a time.
- **Asynchronous Behavior**:
    - Consumers wait if there are no messages, freeing the CPU for other threads.
    - Producers notify waiting consumers whenever a new message arrives.
- **Multiple Topics Support**:
    - Consumers only consume messages of their subscribed topic.
    - Messages are removed from the bus after consumption to avoid duplicates.
- **Deadlock-Free**:
    - There is only **one lock** (the `MessageBus` object), preventing circular waiting.
    - `wait()` and `notifyAll()` are used carefully within synchronized blocks.

---

## Usage
1. Compile and run the `MessageBusExample` Java program.
2. Observe multiple producers generating messages for random topics.
3. Observe multiple consumers consuming messages asynchronously and logging them to the console.

---

