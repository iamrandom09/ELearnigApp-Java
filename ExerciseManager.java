import java.io.*;
import java.util.*;

public class ExerciseManager {
    private static final String FILE_NAME = "exercises.txt";

    public static class Exercise {
        String level;
        String question;
        String answer;
        int points;

        public Exercise(String level, String question, String answer, int points) {
            this.level = level;
            this.question = question;
            this.answer = answer;
            this.points = points;
        }

        @Override
        public String toString() {
            return level + ";" + question + ";" + answer + ";" + points;
        }

        public static Exercise fromString(String line) {
            String[] parts = line.split(";");
            if (parts.length != 4) return null;
            return new Exercise(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
        }
    }

    public static List<Exercise> loadExercises() {
        List<Exercise> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Exercise ex = Exercise.fromString(line);
                if (ex != null) list.add(ex);
            }
        } catch (IOException ignored) {}
        return list;
    }

    public static void saveExercises(List<Exercise> exercises) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Exercise ex : exercises) {
                writer.write(ex.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la salvarea exercițiilor.");
        }
    }
}