package student.minesweeper;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

    private final Tile[][] grid;
    private final int width;
    private final int height;
    private final int numberOfBombs;

    public static Image covered = new Image("file:src/main/resources/assets/covered.jpg");
    public static Image flagged = new Image("file:src/main/resources/assets/flagged.jpg");

    public static Image bomb = new Image("file:src/main/resources/assets/bomb.jpg");
    public static Image not_bomb = new Image("file:src/main/resources/assets/not_bomb.jpg");
    public static Image clicked_bomb = new Image("file:src/main/resources/assets/clicked_bomb.jpg");

    public static Image opened0 = new Image("file:src/main/resources/assets/opened0.jpg");
    public static Image opened1 = new Image("file:src/main/resources/assets/opened1.jpg");
    public static Image opened2 = new Image("file:src/main/resources/assets/opened2.jpg");
    public static Image opened3 = new Image("file:src/main/resources/assets/opened3.jpg");
    public static Image opened4 = new Image("file:src/main/resources/assets/opened4.jpg");
    public static Image opened5 = new Image("file:src/main/resources/assets/opened5.jpg");
    public static Image opened6 = new Image("file:src/main/resources/assets/opened6.jpg");
    public static Image opened7 = new Image("file:src/main/resources/assets/opened7.jpg");
    public static Image opened8 = new Image("file:src/main/resources/assets/opened8.jpg");

    boolean gameLost = false;
    boolean gameWon = false;

    private GridPane gridPane = null;
    private Label bombsLeft = null;

    public Grid(int width, int height, int numberOfBombs) {

        System.out.println("Grid created: " + height + "x" + width);

        this.height = height;
        this.width = width;
        grid = new Tile[height][width];
        this.numberOfBombs = numberOfBombs;

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

    public int open(GridPane gridPane, Integer colIndex, Integer rowIndex, Label bombsLeft) {
        if (this.gridPane == null)
            this.gridPane = gridPane;
        if (this.bombsLeft == null)
            this.bombsLeft = bombsLeft;
        return grid[colIndex][rowIndex].open(gridPane);
    }

    public void flag(GridPane gridPane, Integer colIndex, Integer rowIndex, Label bombsLeft) {
        if (this.gridPane == null)
            this.gridPane = gridPane;
        if (this.bombsLeft == null)
            this.bombsLeft = bombsLeft;
        grid[colIndex][rowIndex].flag(gridPane, bombsLeft, false);
    }

    public void setNumbers() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Tile tile = grid[x][y];
                tile.setNumber((int)getNeighbours(tile).stream().filter(Tile::hasBomb).count());
            }
        }
    }

    public void gameLost(Tile clicked) {
        this.gameLost = true;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Tile tile = grid[x][y];
                if (tile.isFlagged() && !tile.hasBomb())
                    tile.incorrectlyFlagged(gridPane);
                if (!tile.isFlagged() && tile.hasBomb() && tile != clicked)
                    tile.bombNotFlagged(gridPane);
            }
        }
    }

    public void gameWon() {
        this.gameWon = true;
        flagAll();
    }

    public boolean checkIfWon() {
        int uncovered = 0;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Tile tile = grid[x][y];
                if (!tile.hasBomb() && tile.isOpen())
                    uncovered++;
            }
        }
        if (uncovered == width*height - numberOfBombs) {
            gameWon();
            return true;
        }
        return false;
    }

    public void flagAll() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                grid[x][y].flag(gridPane, bombsLeft, true);
            }
        }
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public boolean isGameWon() {
        return gameWon;
    }
}
