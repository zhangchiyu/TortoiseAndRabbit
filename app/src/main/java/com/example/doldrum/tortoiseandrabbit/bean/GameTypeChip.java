package com.example.doldrum.tortoiseandrabbit.bean;

public class GameTypeChip {
    private Chip chip;
    private GameType gameType;

    public GameTypeChip(Chip chip, GameType gameType) {
        this.chip = chip;
        this.gameType = gameType;
    }

    public Chip getChip() {
        return chip;
    }

    public void setChip(Chip chip) {
        this.chip = chip;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
