package com.example.coursework.logs;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Allows to create log-files and work with them
 * @author Trevertor
 * @version 1.0
 */
public class Logs {
    private static DateTimeFormatter nameFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HH-mm-ss");
    private static DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
    private LocalDateTime now;
    private String fileName;
    private Path file;

    private static Logs logs = null;

    private Logs(){
        this.now = LocalDateTime.now();
        this.fileName = System.getProperty("user.dir") + "\\logs\\log_" + nameFormat.format(LocalDateTime.now()) + ".txt";
        this.file = Path.of(System.getProperty("user.dir") + "/logs/log_" + nameFormat.format(LocalDateTime.now()) + ".txt");
    }

    public static Logs getInstance()
    {
        if (logs == null)
            logs = new Logs();

        return logs;
    }

    /** Write a log in file, even if it doesn`t exist
     * @return true if successful
     */
    public boolean writeLog(String text) throws IOException {
        File f;
        if(!new File(this.file.toUri()).exists()){
            f = new File(this.file.toUri());
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        try{
            Files.writeString(this.file,Files.readString(this.file) + "\n[" + this.logFormat.format(LocalDateTime.now()) + "] " + text);
            // logWriter.close();
            return true;
        } catch (IOException e){
            System.out.println("Writing log have failed...");
            e.printStackTrace();
            return false;
        }
    }
    public void printLogs(){
        Scanner inp = new Scanner(System.in);
        String[] names;
        short i;
        File l = new File(System.getProperty("user.dir") + "\\logs");

        names = l.list();
        i = 1;
        System.out.println("\n Choose the log-file: ");
        for (String pathname : names) {
            System.out.println(i + ". " + pathname);
            i++;
        }

        i = inp.nextShort();

        String fileName = names[i-1];
        try {
            FileReader r = new FileReader(System.getProperty("user.dir") +
                    "\\logs\\" + fileName);

            System.out.println("\n---The content of the chosen log-file:");
            Scanner rs = new Scanner(r);
            while (rs.hasNextLine()){
                System.out.println(rs.nextLine());
            }
            System.out.println("---------------------------------------");
            r.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String readLog(String fileName){
        String result = "";
        try {
            FileReader r = new FileReader(System.getProperty("user.dir") +
                    "\\logs\\" + fileName);

            Scanner rs = new Scanner(r);
            while (rs.hasNextLine()){
                result = result + "\n" +rs.nextLine();
            }
            r.close();
            return result;
        } catch (IOException e){
            e.printStackTrace();
            return "Error reading the file!";
        }
    }

}
