# EventLogger

A simple Java console application for recording timestamps of consecutive real-world events.

## Purpose

This program was developed to collect event timestamps for a probability and statistics project. Each time the user presses Enter on an empty line, the program records an entry number, date, and time.

## Features

- Records event timestamps in 24-hour format (`HH:mm:ss`).
- Writes entries to a text file in real time.
- Prevents accidental overwriting of existing files.
- Supports early termination using the `CLOSE` command.
- Automatically stops after 100 entries.
- Records the session's closing timestamp.

## Requirements

- Java Development Kit (JDK) 11 or later.
- A terminal or Java-compatible development environment.

## How to Run

Compile the program:

```bash
javac EventLogger.java
```

Run the program:

```bash
java EventLogger
```

Follow the prompts to choose an output filename and begin recording events.

## Notes

Timestamps are obtained from the computer's system clock. The program records the time when the input is processed, so manual recording may include a small reaction-time delay.

This program records raw event timestamps. Inter-arrival times and descriptive statistics can be calculated separately from the collected data.
