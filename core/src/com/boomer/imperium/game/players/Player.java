package com.boomer.imperium.game.players;

public class Player {
    private final String name;
    private final boolean isAI;
    private final Nation nation;

    public Player(String name, boolean isAI,Nation nation){
        this.name = name;
        this.isAI = isAI;
        this.nation = nation;
    }

    public String getName() {
        return name;
    }

    public boolean isAI() {
        return isAI;
    }

    public Nation getNation() {
        return nation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (isAI != player.isAI) return false;
        if (!name.equals(player.name)) return false;
        return nation.equals(player.nation);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (isAI ? 1 : 0);
        result = 31 * result + nation.hashCode();
        return result;
    }
}
