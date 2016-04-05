package com.dodevjutsu.kata.smellymarsrover;

import java.util.ArrayList;
import java.util.List;

import static com.dodevjutsu.kata.smellymarsrover.Rover.Direction.*;

public class Rover {

    private Direction direction;
    private int y;
    private int x;

    public Rover(int x, int y, String directionValue) {
        setDirection(from(directionValue));
        this.y = y;
        this.x = x;
    }

    public void receive(String commandsSequence) {
        List<String> commands = parseCommands(commandsSequence);
        commands.forEach(this::applyCommand);
    }

    private List<String> parseCommands(String commandsSequence) {
        List<String> commands = new ArrayList<>();
        for (int i = 0; i < commandsSequence.length(); ++i) {
            String command = String.valueOf(commandsSequence.charAt(i));
            commands.add(command);
        }
        return commands;
    }

    private void applyCommand(String command) {
        if (isRotation(command)) {
            rotate(command);
        } else {
            displace(command);
        }
    }

    private boolean isRotation(String command) {
        return command.equals("l") || command.equals("r");
    }

    private void displace(String command) {
        int displacement1 = -1;

        if (command.equals("f")) {
            displacement1 = 1;
        }
        int displacement = displacement1;

        if (isDirection(NORTH)) {
            y += displacement;
        } else if (isDirection(SOUTH)) {
            y -= displacement;
        } else if (isDirection(WEST)) {
            x -= displacement;
        } else if (isDirection(EAST)){
            x += displacement;
        }
    }

    private boolean isDirection(Direction value) {
        return direction == value;
    }

    private void rotate(String command) {
        if (isDirection(NORTH)) {
//            setDirection(direction.rotate(command));
            rotateWhenNorth(command);
        } else if (isDirection(SOUTH)) {
            if (command.equals("r")) {
                setDirection(WEST);
            } else {
                setDirection(EAST);
            }
        } else if (isDirection(WEST)) {
            if (command.equals("r")) {
                setDirection(NORTH);
            } else {
                setDirection(SOUTH);
            }
        } else {
            if (command.equals("r")) {
                setDirection(SOUTH);
            } else {
                setDirection(NORTH);
            }
        }
    }

    private void rotateWhenNorth(String command) {
        if (command.equals("r")) {
            setDirection(EAST);
        } else {
            setDirection(WEST);
        }
    }

    private void setDirection(Direction value) {
        this.direction = (value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rover rover = (Rover) o;

        if (y != rover.y) return false;
        if (x != rover.x) return false;
        return direction == rover.direction;

    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + y;
        result = 31 * result + x;
        return result;
    }

    public enum Direction {
        NORTH ("N"),
        SOUTH ("S"),
        WEST ("W"),
        EAST ("E");

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        public static Direction from(String value){
            for (Direction current : values()) {
                if (current.value.equals(value)) {
                    return current;
                }
            }
            throw new RuntimeException("Passed value is not correct: " + value);
        }

    }
}
