package com.lld.interviewquestions.chess;

import com.lld.interviewquestions.chess.enums.PieceColour;

public class Player {
    private String name;
    private PieceColour playingSide;

    public Player(String name, PieceColour playingSide) {
        this.name = name;
        this.playingSide = playingSide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PieceColour getPlayingSide() {
        return playingSide;
    }

    public void setPlayingSide(PieceColour playingSide) {
        this.playingSide = playingSide;
    }

    @Override
    public String toString() {
        return name;
    }
}
