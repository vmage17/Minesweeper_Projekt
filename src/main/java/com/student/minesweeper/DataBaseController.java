package com.student.minesweeper;

public class DataBaseController {

    int scoreToSet;
    String difficultyToSet;

    public boolean checkIfNewHighScore(String difficulty, int score) {
        System.out.println("Checking if score: " + score + " in " + difficulty + " difficulty is a new high score...");

        // check in database if it's a new high score...

        scoreToSet = score;
        difficultyToSet = difficulty;
        return true;
    }

    public void setNewHighScore(String name) {
        System.out.print("Setting new high score for " + name + " with score: ");
        System.out.println(scoreToSet + " in " + difficultyToSet + " difficulty!");
    }

}
