import java.util.ArrayList;

public class Position {
    private Type type;
    private boolean accessible, marked;
    private char label;
    private ArrayList<Position> neighbors;

    public Position(char label) {
        this.label = label;
        this.type = type();
        this.marked = false;
        this.neighbors = new ArrayList<Position>();
    }

    private Type type() {
        switch (label) {
            case '.':
                this.accessible = true;
                return Type.COMMON;
            case '#':
                this.accessible = false;
                return Type.WALL;
            default: {
                // The label is a digit, what means that it's a player
                if (Character.isDigit(label)) {
                    this.accessible = true;
                    return Type.PLAYER;
                }
                // The label is in lowerCase, what means that it's a key
                else if (Character.toLowerCase(label) == label) {
                    this.accessible = true;
                    return Type.KEY;
                } else {
                    this.accessible = false;
                    return Type.DOOR;
                }
            }
        }
    }

    public Type getType() {
        return type;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public char getLabel() {
        return label;
    }

    public ArrayList<Position> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Position position) {
        this.neighbors.add(position);
    }

    public void markPosition(boolean bool) {
        this.marked = bool;
    }

    public void setAcessible(Boolean bool) {
        this.accessible = bool;
    }

    public boolean IsMarked() {
        return this.marked;
    }

    public String toString() {
        String toString = "";
        switch (getType()) {
            case DOOR:
                toString = "Porta :";
                break;
            case WALL:
                toString = "Parede :";
                break;
            case PLAYER:
                toString = "Jogador :";
                break;
            case KEY:
                toString = "Chave :";
                break;
            case COMMON:
                toString = "Posição :";
                break;
        }
        toString += " | Símbolo: '" + label + (accessible ? "' | Acessível " : "' | Não acessível ")
                + (marked ? "' | Marcado " : "' | Não Marcado") + "| Possui: "
                + getNeighbors().size() + " vizinho(s) -> {";
        for (int i = 0; i < getNeighbors().size(); i++) {
            toString += getNeighbors().get(i).getLabel();
            if (i < getNeighbors().size() - 1) {
                toString += ", ";
            }
        }
        toString += "}";
        return toString;
    }
}