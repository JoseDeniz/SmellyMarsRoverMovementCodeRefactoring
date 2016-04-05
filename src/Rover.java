import java.util.ArrayList;
import java.util.List;

public class Rover {

    private String direction;
    private int y;
    private int x;

    public Rover(int x, int y, String direction) {
        this.direction = direction;
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

        if (direction.equals(Direction.NORTH)) {
            y += displacement;
        } else if (direction.equals(Direction.SOUTH)) {
            y -= displacement;
        } else if (direction.equals(Direction.WEST)) {
            x -= displacement;
        } else if (direction.equals(Direction.EAST)){
            x += displacement;
        }
    }

    private void rotate(String command) {
        if (direction.equals(Direction.NORTH)) {
            if (command.equals("r")) {
                direction = Direction.EAST;
            } else {
                direction = Direction.WEST;
            }
        } else if (direction.equals(Direction.SOUTH)) {
            if (command.equals("r")) {
                direction = Direction.WEST;
            } else {
                direction = Direction.EAST;
            }
        } else if (direction.equals(Direction.WEST)) {
            if (command.equals("r")) {
                direction = Direction.NORTH;
            } else {
                direction = Direction.SOUTH;
            }
        } else {
            if (command.equals("r")) {
                direction = Direction.SOUTH;
            } else {
                direction = Direction.NORTH;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Rover other = (Rover) obj;

        if (direction == null) {
            if (other.direction != null)
                return false;
        } else if (!direction.equals(other.direction))
            return false;

        if (x != other.x)
            return false;

        if (y != other.y)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + y;
        result = 31 * result + x;
        return result;
    }

    public class Direction {
        public static final String NORTH = "N";
        public static final String SOUTH = "S";
        public static final String WEST = "W";
        public static final String EAST = "E";
    }
}
