package com.virtusa.capitalization;

import java.io.*;

public class CapitalizationScenario {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        FileWriter fileWriter = null;
        PrintWriter printWriter = null;

        try {
            new FileReader("C:\\Users\\Asus\\Desktop\\test.txt");
            bufferedReader = new BufferedReader(fileReader);
            String line;
            String capitalizedLine = null;

            fileWriter = new FileWriter("C:\\Users\\Asus\\Desktop\\write.txt");
            printWriter = new PrintWriter(fileWriter);
            while ((line = bufferedReader.readLine()) != null){
                capitalizedLine = line.toUpperCase();
                System.out.println(capitalizedLine);
            }
            printWriter.write(capitalizedLine + "\n");
        }finally {
            if (fileWriter != null){
                fileWriter.close();
            }
            if (printWriter !=null){
                printWriter.close();
            }
            if (fileReader !=null){
                fileReader.close();
            }
            if (bufferedReader !=null){
                bufferedReader.close();
            }
        }
    }
}
