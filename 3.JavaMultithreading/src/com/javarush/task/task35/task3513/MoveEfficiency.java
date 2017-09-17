package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency moveEfficiency) {

        if (this.numberOfEmptyTiles == moveEfficiency.numberOfEmptyTiles) {
            return Integer.compare(this.score, moveEfficiency.score);
        } else {
            return Integer.compare(this.numberOfEmptyTiles, moveEfficiency.numberOfEmptyTiles);
        }

    }
}
