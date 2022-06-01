import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//The class Map works like a Graph but with some changes
public class Map {
    private String path;
    private ArrayList<Position> players;
    private ArrayList<Position> markedPositions;
    // After the running, pass through this list to see if exists doors to unlock
    private ArrayList<Position> listOfLockedDoors;
    private ArrayList<Position> listOfKeys;

    ArrayList<Position> visited;

    public Map(File file) {
        this.players = new ArrayList<>();
        this.markedPositions = new ArrayList<>();
        this.listOfLockedDoors = new ArrayList<>();
        this.listOfKeys = new ArrayList<>();
        this.visited = new ArrayList<>();
        this.path = file.toPath().toString();
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileReader arq = new FileReader(file);
            BufferedReader lerArq = new BufferedReader(arq);
            String line = "hi";
            while (line != null) {
                line = lerArq.readLine();
                if (line != null && line.length() == 0) {
                    break;
                }
                if (line != null) {
                    lines.add(line);
                }
            }
            arq.close();
        } catch (IOException e) {
            System.out.printf("Error while reading the file: %s.\n", e.getMessage());
        }

        int width = lines.get(0).length();
        int height = lines.size();

        Position[][] matrix = new Position[height][width];

        for (int h = 0; h < height; h++) {
            String line = lines.get(h);
            for (int w = 0; w < width; w++) {
                matrix[h][w] = new Position(line.charAt(w));
                if (Character.isDigit(matrix[h][w].getLabel())) {
                    players.add(matrix[h][w]);
                }
            }
        }
        ArrayList<Position> list = transformToGraph(matrix);
    }

    // Transform matrix to graph
    private ArrayList<Position> transformToGraph(Position[][] matrix) {
        ArrayList<Position> positions = new ArrayList<>();
        int rows = matrix.length;
        int columns = matrix[0].length;
        // Connects the first line
        // Pass from the second column beacuse the first one is always wall
        // Until the last but one, because the last is always wall
        for (int i = 1; i < columns - 1; i++) { // For each column
            // Always connecting with the next Position
            Position current = matrix[1][i];
            if (!(current.getType() == Type.WALL)) {
                positions.add(current);
                Position next = matrix[1][i + 1];
                if (!(i == columns - 2) && !(next.getType() == Type.WALL)) {
                    // Connect the nodes
                    current.addNeighbor(next);
                    next.addNeighbor(current);
                }
            }
        }

        // Connect the others lines with the next node and the above
        // Pass from the second line until the last but one because the last has only
        // walls
        for (int h = 2; h < rows - 1; h++) { // For each row
            // Pass from the second column beacuse the first one is always wall
            for (int w = 1; w < columns - 1; w++) {// For each column
                Position current = matrix[h][w];
                if (!(current.getType() == Type.WALL)) {
                    positions.add(current);
                    Position next = matrix[h][w + 1];
                    if (!(w == columns - 2) && !(next.getType() == Type.WALL)) {
                        // Connect the nodes
                        current.addNeighbor(next);
                        next.addNeighbor(current);
                    }
                    Position above = matrix[h - 1][w];
                    if (above.getType() != Type.WALL) {
                        // Connect the nodes
                        current.addNeighbor(above);
                        above.addNeighbor(current);
                    }
                }
            }
        }
        return positions;
    }

    private int walk(Position position) {
        if (!position.isAccessible()) {
            return 0;
        }
        // It means that the method is called by a player and it's needed to markOff all
        // the nodes
        if (position.getType() == Type.PLAYER) {
            beginWalk();
            // System.out.println("Jogando com o player: " + position.getLabel());
        }

        if (position.IsMarked()) {
            return 0;
        }

        ArrayList<Position> list = new ArrayList<Position>();

        // This number will be returned meaning the number of positions
        // the node can "walk"
        int count = 0;
        position.markPosition(true);
        markedPositions.add(position);
        list.add(position);
        while (!list.isEmpty()) {
            Position v = list.get(0);
            if (visited.contains(v)) {
                System.out.println("Repetido: " + v);
            } else {
                visited.add(v);
            }
            ;
            // Counts the position
            count++;
            for (Position neighbor : v.getNeighbors()) {
                if (!neighbor.IsMarked()) {
                    if (neighbor.getType() == Type.KEY) {
                        // System.out
                        // .println("Nodo " + position.getLabel() + " encontrou a chave: " +
                        // neighbor.getLabel());
                        listOfKeys.add(neighbor);
                        neighbor.markPosition(true);
                        markedPositions.add(neighbor);
                        list.add(neighbor);
                    } else if (neighbor.getType() == Type.DOOR) {
                        // If has the key, unlock the door and visit it
                        if (unlockDoor(neighbor)) {
                            neighbor.markPosition(true);
                            markedPositions.add(neighbor);
                            list.add(neighbor);
                        } else {
                            listOfLockedDoors.add(neighbor);
                        }
                        // System.out
                        // .println("Nodo " + position.getLabel() + " encontrou a porta: " +
                        // neighbor.getLabel());

                    } else {
                        neighbor.markPosition(true);
                        markedPositions.add(neighbor);
                        list.add(neighbor);
                    }
                }
            }
            list.remove(0);
        }

        // Keeps a list of doors to walk from the locked doors

        ArrayList<Position> doorsToWalk = new ArrayList<>();

        if (!listOfLockedDoors.isEmpty()) {
            for (Position door : listOfLockedDoors) {
                if (unlockDoor(door) && !door.IsMarked()) {
                    // count += walk(door);
                    doorsToWalk.add(door);
                }
            }
        }

        while (!doorsToWalk.isEmpty()) {
            Position door = doorsToWalk.get(0);
            doorsToWalk.remove(0);
            count += walk(door);
        }

        return count;
    }

    // If finds the key, unlock the door. Else, add to the list of Locked doors
    private boolean unlockDoor(Position door) {
        if (!listOfKeys.isEmpty()) {
            for (Position key : listOfKeys) {
                if (Character.toUpperCase(key.getLabel()) == door.getLabel()) {
                    door.setAcessible(true);
                    return true;
                }
            }
        }
        return false;
    }

    // beginWalk will prepare the lists to start a new walk in the map
    private void beginWalk() {
        clearMarkedPositions();
        clearListOfLockedDoors();
        clearListOfKeys();
        clearVisited();
    }

    private void clearVisited() {
        visited.clear();
    }

    private void clearMarkedPositions() {
        for (Position p : markedPositions) {
            if (p.getType() == Type.DOOR) {
                p.setAcessible(false);
            }
            p.markPosition(false);
        }
        markedPositions.clear();
    }

    private void clearListOfKeys() {
        listOfKeys.clear();
    }

    private void clearListOfLockedDoors() {
        // First set the accessibility to the default value
        for (Position door : listOfLockedDoors) {
            if (door.isAccessible()) {
                door.setAcessible(false);
            }
        }
        listOfLockedDoors.clear();
    }

    public String results() {
        String results = "Results\n";
        results += path + "\n";
        for (Position player : players) {
            results += "Player " + player.getLabel() + " pode percorrer por " + walk(player) + " posições!\n";
        }
        return results;
    }
}
