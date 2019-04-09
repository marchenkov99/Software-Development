package marchenkov.ivan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static marchenkov.ivan.Utils.scanIntValue;

public class FileWorker {

    private int rows;
    private int words;

    public void copy(String path) {
        System.out.println("Enter last rows count: ");
        rows = scanIntValue(2, 100);

        System.out.println("Enter last words count: ");
        words = scanIntValue(1, 50);

        openDirectory(new File(path));
    }

    private void openDirectory(File directory) {
        System.out.println("In directory: " + directory.getName());
        File[] elements = directory.listFiles();

        if (elements != null) {
            ExecutorService executorService = Executors.newFixedThreadPool(elements.length);

            for(File element : elements) {
                executorService.submit(() -> {
                    if(element.isFile()){
                        openFile(element);
                    } else if(element.isDirectory()){
                        openDirectory(element);
                    }
                    return null;
                });
            }

            executorService.shutdown();
        }
    }

    private void openFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            StringBuilder builder = new StringBuilder();
            boolean createNewFile = false;
            List<String> lines = new ArrayList<>();

            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }

            for (int i = lines.size() - rows; i < lines.size(); i++) {
                String lastWords = getLastWords(lines.get(i), words);
                builder.append(lastWords).append("\r\n");

                if (lastWords.length() > 0) createNewFile = true;
            }

            if (createNewFile) {
                File newFile = new File(System.getProperty("user.dir") + File.separator + file.getName());
                BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));

                bw.write(builder.toString());
                bw.close();
            }

        } catch (IOException e) {
                System.err.println("Error occurred during reading file.");
        }
    }

    private String getLastWords(String row, int count) {
        String[] lexemes = row.split(" ");
        StringBuilder builder = new StringBuilder();

        for (int i = lexemes.length - count; i < lexemes.length; i++) {
            if (!lexemes[i].equals("")) {
                builder.append(lexemes[i]).
                        append(" ");

                System.out.println(String.format("      Copied \"%s\"", lexemes[i]));
            }
        }

        return builder.toString();
    }
}
