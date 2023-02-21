package com.student.minesweeper;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

    public void flag(GridPane gridPane, Label bombsLeft, boolean won) {

        if (isOpen) {
            System.out.println("Already opened!");
            return;
        }

        ImageView imageView = getChildByRowColumn(gridPane);
        int bombsLeftAsInt = Integer.parseInt(bombsLeft.getText());

        if (flagged && !won) {
            System.out.println("Unflagging");
            imageView.setImage(Grid.covered);
            bombsLeftAsInt++;
            flagged = false;
        }
        else {
            System.out.println("Flagging");
            imageView.setImage(Grid.flagged);
            if (!won)
                bombsLeftAsInt--;
            flagged = true;
        }

        if (bombsLeftAsInt <= -100) {
            bombsLeftAsInt = -((-bombsLeftAsInt)%10);
        }
        if (bombsLeftAsInt <= -10) {
            bombsLeft.setText("-" + -bombsLeftAsInt);
        }
        if (bombsLeftAsInt < 0 && bombsLeftAsInt > -10) {
            bombsLeft.setText("-0" + -bombsLeftAsInt);
        }
        if (bombsLeftAsInt >= 0 && bombsLeftAsInt < 10) {
            bombsLeft.setText("00" + bombsLeftAsInt);
        }
        if (bombsLeftAsInt >= 10 && bombsLeftAsInt < 100) {
            bombsLeft.setText("0" + bombsLeftAsInt);
        }
        if (bombsLeftAsInt >= 100) {
            bombsLeft.setText("" + bombsLeftAsInt);
        }
        if (won) {
            bombsLeft.setText("000");
        }
    }

    public int open(GridPane gridPane) {
        ImageView imageView = getChildByRowColumn(gridPane);
        //System.out.println(imageView.getImage().getUrl());


        if (isOpen || flagged) {
            //System.out.println("Already opened!");
            return 0;
        }

        if (hasBomb) {
            System.out.println("There is a bomb! Game Over");
            imageView.setImage(Grid.clicked_bomb);
            grid.gameLost(this);
            return -1;
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

        if (grid.checkIfWon()) {
            System.out.println("You won!");
            return 1;
        }

        if (number == 0) {
            List<Tile> neighbours = grid.getNeighbours(this);
            for (Tile tile : neighbours) {
                if (tile.open(gridPane) == 1)
                    return 1;
            }
        }

        return 0;
    }

    public void incorrectlyFlagged(GridPane gridPane) {
        ImageView imageView = getChildByRowColumn(gridPane);
        imageView.setImage(Grid.not_bomb);
    }

    public void bombNotFlagged(GridPane gridPane) {
        ImageView imageView = getChildByRowColumn(gridPane);
        imageView.setImage(Grid.bomb);
    }

    ImageView getChildByRowColumn(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            ImageView iv = (ImageView)node;
            if (GridPane.getColumnIndex(iv) != null
                    && GridPane.getColumnIndex(iv) != null
                    && GridPane.getRowIndex(iv) == y
                    && GridPane.getColumnIndex(iv) == x) {
                //System.out.println("node (" + x + ", " + y + ") exist");
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

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
