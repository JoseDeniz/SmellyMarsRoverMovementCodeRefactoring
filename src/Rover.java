import java.util.ArrayList;
import java.util.List;

public class Rover {

    private Direction direction;
    private int y;
    private int x;

    public Rover(int x, int y, String directionValue) {
        setDirection(Direction.from(directionValue));
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

        if (isDirection(Direction.NORTH)) {
            y += displacement;
        } else if (isDirection(Direction.SOUTH)) {
            y -= displacement;
        } else if (isDirection(Direction.WEST)) {
            x -= displacement;
        } else if (isDirection(Direction.EAST)){
            x += displacement;
        }
    }

    private boolean isDirection(Direction value) {
        return direction == value;
    }

    private void rotate(String command) {
        if (isDirection(Direction.NORTH)) {
            if (command.equals("r")) {
                setDirection(Direction.EAST);
            } else {
                setDirection(Direction.WEST);
            }
        } else if (isDirection(Direction.SOUTH)) {
            if (command.equals("r")) {
                setDirection(Direction.WEST);
            } else {
                setDirection(Direction.EAST);
            }
        } else if (isDirection(Direction.WEST)) {
            if (command.equals("r")) {
                setDirection(Direction.NORTH);
            } else {
                setDirection(Direction.SOUTH);
            }
        } else {
            if (command.equals("r")) {
                setDirection(Direction.SOUTH);
            } else {
                setDirection(Direction.NORTH);
            }
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
