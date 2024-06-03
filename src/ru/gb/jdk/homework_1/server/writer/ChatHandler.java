package ru.gb.jdk.homework_1.server.writer;

import java.io.*;

public class ChatHandler implements Writable{
    String filePath = "src/ru/gb/jdk/homework_1/server/writer/messages.txt";
    File file;

    public ChatHandler() {
        file = new File(filePath);
    }

    public void save(String msg){
        try(FileWriter writer = new FileWriter(file, true)){
            writer.write(msg);
        } catch (IOException e){
            throw new RuntimeException("Ошибка в процессе записи в файл");
        }
    }

    public String read(){
        StringBuilder builder = new StringBuilder();
        try (FileReader reader = new FileReader(file)){
            int line;
            while ((line = reader.read()) > 0){
                builder.append((char)line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в процессе чтения из файла");
        }
        return builder.toString();
    }
}
