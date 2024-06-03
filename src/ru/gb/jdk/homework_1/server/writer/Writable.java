package ru.gb.jdk.homework_1.server.writer;

public interface Writable {
    void save(String message);
    String read();
}
