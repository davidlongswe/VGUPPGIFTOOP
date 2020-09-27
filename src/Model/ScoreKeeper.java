package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreKeeper {

    private ArrayList<Score> scores;
    public static final String SCORE_FILE_NAME = "scores.txt";

    public ScoreKeeper() {
        scores = new ArrayList<>();
        createScoreFile();
        getScoresFromFile();
    }

    private void createScoreFile(){
        File file = new File(SCORE_FILE_NAME);
        try {
            if(file.createNewFile()){
                System.out.println("Score file created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scoresToFile(){
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream(SCORE_FILE_NAME));
            for (Score score : scores)
                pw.println(score.getName() + " " + score.getScore());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getScoresFromFile(){
        try {
            FileReader fileReader = new FileReader(SCORE_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] splitLine = line.split(" ");
                Score score = new Score(splitLine[0], Integer.parseInt(splitLine[1]));
                scores.add(score);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortScoresFromHighestToLowest(){
        Collections.sort(scores);
    }

    public void addScore(Score score){
        scores.add(score);
        sortScoresFromHighestToLowest();
        scoresToFile();
    }

    public ArrayList<Score> getScores() {
        return scores;
    }
}
