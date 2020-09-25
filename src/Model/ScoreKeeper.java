package Model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreKeeper {

    private ArrayList<Score> scores;
    public static final String SCORE_FILE_NAME = "scores.txt";

    public ScoreKeeper() {
       scores = new ArrayList<>();
    }

    private void createScoreFile(){
        File file = new File(SCORE_FILE_NAME);
        try {
            if(file.createNewFile()){
                System.out.println("Score file created");
            }else{
                System.out.println("Score file already exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scoresToFile(){
        PrintWriter pw = null;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortScoresFromHighestToLowest(){
        Collections.sort(scores);
    }

    private void addScore(Score score){
        scores.add(score);
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }
}
