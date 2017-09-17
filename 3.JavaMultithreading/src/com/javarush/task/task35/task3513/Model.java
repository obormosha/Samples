package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    int score;
    int maxTile;
    Stack<Tile[][]> previousStates = new Stack<>();
    Stack<Integer> previousScores = new Stack<>();
    boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    void resetGameTiles() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
        score = 0;
        maxTile = 2;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value > maxTile) {
                    maxTile = gameTiles[i][j].value;
                }
            }
        }
    }

    private void addTile() {
        List<Tile> emptyTails = getEmptyTiles();
        if (!emptyTails.isEmpty()) {
            int numberOfTile = (int) (Math.random() * emptyTails.size());
            emptyTails.get(numberOfTile).value = (Math.random() < 0.9 ? 2 : 4);
        }
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTails = new ArrayList<>();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    emptyTails.add(gameTiles[i][j]);
                }
            }
        }
        return emptyTails;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    void left() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }

        Tile[][] toLeftGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                toLeftGameTiles[i][j] = gameTiles[i][j];
            }
        }

        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(toLeftGameTiles[i]) | mergeTiles(toLeftGameTiles[i])) {
                isChanged = true;
            }
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = toLeftGameTiles[i][j];
            }
        }

        if (isChanged) {
            addTile();
        }

        isSaveNeeded = true;
    }

    void right() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        Tile[][] toRightGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                toRightGameTiles[i][j] = gameTiles[i][FIELD_WIDTH - j - 1];
            }
        }

        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(toRightGameTiles[i]) | mergeTiles(toRightGameTiles[i])) {
                isChanged = true;
            }
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][FIELD_WIDTH - j - 1] = toRightGameTiles[i][j];
            }
        }

        if (isChanged) {
            addTile();
        }
        isSaveNeeded = true;
    }

    void down() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        Tile[][] toDownGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                toDownGameTiles[i][j] = gameTiles[FIELD_WIDTH - j - 1][FIELD_WIDTH - i - 1];
            }
        }
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(toDownGameTiles[i]) | mergeTiles(toDownGameTiles[i])) {
                isChanged = true;
            }
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[FIELD_WIDTH - j - 1][FIELD_WIDTH - i - 1] = toDownGameTiles[i][j];
            }
        }

        if (isChanged) {
            addTile();
        }
        isSaveNeeded = true;
    }

    void up() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        Tile[][] toUpGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                toUpGameTiles[i][j] = gameTiles[j][FIELD_WIDTH - i - 1];
            }
        }
        boolean isChanged = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(toUpGameTiles[i]) | mergeTiles(toUpGameTiles[i])) {
                isChanged = true;
            }
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[j][FIELD_WIDTH - i - 1] = toUpGameTiles[i][j];
            }
        }

        if (isChanged) {
            addTile();
        }
        isSaveNeeded = true;
    }


    private boolean compressTiles(Tile[] tiles) {
        boolean shifted = true;
        boolean isChanged = false;
        while (shifted) {
            shifted = false;
            for (int i = 1; i < tiles.length; i++) {
                if (tiles[i - 1].value == 0 && !tiles[i].isEmpty()) {
                    tiles[i - 1] = tiles[i];
                    tiles[i] = new Tile(0);
                    shifted = true;
                    isChanged = true;
                }
            }
        }

        return isChanged;
    }

    private boolean mergeTiles(Tile[] tiles) {
        int thisScore = 0;
        boolean isChanged = false;
        for (int i = 0; i < tiles.length - 1; i++) {
            if ((tiles[i].value == tiles[i + 1].value) && !tiles[i].isEmpty() && !tiles[i + 1].isEmpty()) {
                thisScore = tiles[i].value * 2;
                tiles[i] = new Tile(thisScore);
                tiles[i + 1] = new Tile();
                score += thisScore;
                if (thisScore > maxTile) {
                    maxTile = thisScore;
                }
                isChanged = true;
            }
            compressTiles(tiles);
        }
        return isChanged;
    }

    public boolean canMove() {
        //если есть пустые - то можно сделать ход
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value == 0) {
                    return true;
                }
            }
        }
        //если есть два одинаковых значения по горизонтали - то можно сделать ход
        for (int i = 0; i < FIELD_WIDTH - 1; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                    return true;
                }
            }
        }
        //если есть два одинаковых значения по вертикали - то можно сделать ход
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH - 1; j++) {
                if (gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                    return true;
                }
            }
        }

        return false;
    }

    private void saveState(Tile[][] state) {
        previousScores.push(score);
        Tile[][] toSaveState = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                toSaveState[i][j] = new Tile(state[i][j].value);
            }
        }
        previousStates.push(toSaveState);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public void randomMove() {
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                left();
            case 1:
                right();
            case 2:
                up();
            case 3:
                down();
        }
    }

    public boolean hasBoardChanged() {
        Tile[][] upperStatesInStack = previousStates.peek();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (upperStatesInStack[i][j].value != gameTiles[i][j].value) {
                    return true;
                }
            }
        }
        return false;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency;
        if (hasBoardChanged()) {
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        } else {
            moveEfficiency = new MoveEfficiency(-1, 0, move);
        }
        rollback();

        return moveEfficiency;
    }

    public void autoMove() {
        PriorityQueue priorityQueue = new PriorityQueue(4, Collections.reverseOrder());
        priorityQueue.offer(getMoveEfficiency(this::left));
        priorityQueue.offer(getMoveEfficiency(this::up));
        priorityQueue.offer(getMoveEfficiency(this::down));
        priorityQueue.offer(getMoveEfficiency(this::right));
        MoveEfficiency moveEfficiency = (MoveEfficiency) priorityQueue.peek();
        moveEfficiency.getMove().move();
    }
}
