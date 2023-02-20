package com.student.minesweeper;

import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    public static final int TILE_SIZE = 32;
    private final int x;
    private final int y;
    private final boolean hasBomb;
    private boolean flagged = false;
    private boolean isOpen = false;

    private final Grid grid;

    private final Text number = new Text();
    //private ImageView imageView;

    public Tile(int x, int y, boolean hasBomb, Grid grid) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;
        this.grid = grid;
        //imageView = new ImageView();
        //imageView.setImage(grid.covered);

        int numberOfNeighbours = grid.getNeighbours(this).size();
        number.setText(Integer.toString(numberOfNeighbours));
        switch (numberOfNeighbours) {
            case 0 -> number.setText("");
            case 1 -> number.setFill(Color.BLUE);
            case 2 -> number.setFill(Color.GREEN);
            case 3 -> number.setFill(Color.RED);
            case 4 -> number.setFill(Color.DARKBLUE);
            case 5 -> number.setFill(Color.DARKRED);
            case 6 -> number.setFill(Color.TEAL);
            case 7 -> number.setFill(Color.BLACK);
            case 8 -> number.setFill(Color.GRAY);
        }
        number.setFont(Font.font(18));
        number.setVisible(false);

        getChildren().add(number); //root?

        setTranslateX(x * TILE_SIZE);
        setTranslateY(y * TILE_SIZE);

        ///setOnMouseClicked(e -> open());
    }

    public void flag(GridPane gridPane) {

        if (isOpen) {
            System.out.println("Already opened!");
            return;
        }

        ImageView imageView = getChildByRowColumn(gridPane);

        if (flagged) {
            System.out.println("Unflagging");
            imageView.setImage(Grid.covered);
        }
        else {
            System.out.println("Flagging");
            imageView.setImage(Grid.flagged);
        }
        flagged = !flagged;
    }

    public void open(GridPane gridPane) {

        if (isOpen) {
            System.out.println("Already opened!");
            return;
        }

        ImageView imageView = getChildByRowColumn(gridPane);

        if (hasBomb) {
            System.out.println("There is a bomb! Game Over");
            imageView.setImage(Grid.not_bomb);
            return;
        }

        System.out.println("Opening this tile...");
        info();

        isOpen = true;
        imageView.setImage(Grid.opened);
        number.setVisible(true);

        //if (number.getText().isEmpty()) {
        //    grid.getNeighbours(this).forEach(Tile::open);
        //}
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //public ImageView getImage() {
        //return imageView;
    //}

    ImageView getChildByRowColumn(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            ImageView iv = (ImageView)node;
            if (GridPane.getColumnIndex(iv) != null
                    && GridPane.getColumnIndex(iv) != null
                    && GridPane.getRowIndex(iv) == y
                    && GridPane.getColumnIndex(iv) == x) {
                System.out.println("node (" + x + ", " + y + ") exist");
                return iv;
            }
        }
        return null;
    }

    public void info() {
        System.out.print("Tile(" + x + ", " + y + ") open: " + isOpen);
        System.out.println(" bomb: " + hasBomb + " flag: " + flagged);
    }
}
