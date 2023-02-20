package com.student.minesweeper;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

    private final Tile[][] grid;
    private final int width;
    private final int height;

    public static Image bomb = new Image("file:src/main/resources/assets/bomb.jpg");

    public static Image opened = new Image("file:src/main/resources/assets/opened.jpg");
    public static Image covered = new Image("file:src/main/resources/assets/covered.jpg");
    public static Image flagged = new Image("file:src/main/resources/assets/flagged.jpg");
    public static Image not_bomb = new Image("file:src/main/resources/assets/not_bomb.jpg");

    public Grid(int height, int width, int numberOfBombs) {

        System.out.println("Grid created");

        this.height = height;
        this.width = width;
        grid = new Tile[height][width];

        // Placing bombs

        Random random = new Random();
        boolean[][] haveBomb = new boolean[height][width];
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                haveBomb[x][y] = false;
            }
        }
        while (numberOfBombs > 0) {
            int x = random.nextInt(height);
            int y = random.nextInt(width);
            if (!haveBomb[x][y]) {
                haveBomb[x][y] = true;
                numberOfBombs--;
            }
        }

        // Placing tiles

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Tile tile = new Tile(x, y, haveBomb[x][y], this);
                grid[x][y] = tile;
            }
        }
    }

    public List<Tile> getNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>();

        int [] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getX() + dx;
            int newY = tile.getY() + dy;

            if (newX >= 0 && newX < height && newY >=0 && newY < width) {
                neighbours.add(grid[newX][newY]);
            }
        }

        return neighbours;
    }

    public void placeImages(GridPane gridPane) {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Tile tile = grid[x][y];
                //GridPane.setRowIndex(tile.getImage(), x);
                //GridPane.setColumnIndex(tile.getImage(), y);
                //gridPane.getChildren().add(tile.getImage());
            }
        }
    }

    public void open(GridPane gridPane, Integer colIndex, Integer rowIndex) {
        grid[colIndex][rowIndex].open(gridPane);
    }

    public void flag(GridPane gridPane, Integer colIndex, Integer rowIndex) {
        grid[colIndex][rowIndex].flag(gridPane);
    }
}
