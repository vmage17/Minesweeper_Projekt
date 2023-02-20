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

import java.util.List;

public class Tile extends StackPane {

    public static final int TILE_SIZE = 32;
    private final int x;
    private final int y;
    private final boolean hasBomb;
    private boolean flagged = false;
    private boolean isOpen = false;

    private final Grid grid;

    private int number;

    public Tile(int x, int y, boolean hasBomb, Grid grid) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;
        this.grid = grid;
        number = 0;

        //getChildren().add(number); //root?

        //setTranslateX(x * TILE_SIZE);
        //setTranslateY(y * TILE_SIZE);

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
        ImageView imageView = getChildByRowColumn(gridPane);
        System.out.println(imageView.getImage().getUrl());

        if (isOpen) {
            //System.out.println("Already opened!");
            return;
        }



        if (hasBomb) {
            System.out.println("There is a bomb! Game Over");
            imageView.setImage(Grid.not_bomb);
            return;
        }

        //System.out.println("Opening this tile...");
        info();

        isOpen = true;
        switch (number) {
            case 0 -> imageView.setImage(Grid.opened0);
            case 1 -> imageView.setImage(Grid.opened1);
            case 2 -> imageView.setImage(Grid.opened2);
            case 3 -> imageView.setImage(Grid.opened3);
            case 4 -> imageView.setImage(Grid.opened4);
            case 5 -> imageView.setImage(Grid.opened5);
            case 6 -> imageView.setImage(Grid.opened6);
            case 7 -> imageView.setImage(Grid.opened7);
            case 8 -> imageView.setImage(Grid.opened8);
        }
        if (number == 0) {
            List<Tile> neighbours = grid.getNeighbours(this);
            for (Tile tile : neighbours)
                tile.open(gridPane);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean hasBomb() {
        return hasBomb;
    }
}
