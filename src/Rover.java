import java.util.HashMap;
import java.util.Map;

public class Rover {

    private String direction;
    private int y;
    private int x;
    private final RotationConfiguration rotationLeft;
    private final RotationConfiguration rotationRight;
    private Command command;
    public Rover(int x, int y, String direction) {
        this.direction = direction;
        this.y = y;
        this.x = x;
        rotationLeft = RotationConfiguration.left();
        rotationRight = RotationConfiguration.right();
    }

    public void receive(String commandsSequence) {
        for (int i = 0; i < commandsSequence.length(); ++i) {
            String command = commandsSequence.substring(i, i + 1);

            if (Command.from(command).isRotationLeft()) {
                direction = rotationLeft.apply(direction);
            } else if (Command.from(command).isRotationRight()) {
                direction = rotationRight.apply(direction);
            } else {
                int displacement1 = -1;

                if (command.equals(Command.FORWARD)) {
                    displacement1 = 1;
                }
                int displacement = displacement1;

                if (direction.equals(Direction.NORTH)) {
                    y += displacement;
                } else if (direction.equals(Direction.SOUTH)) {
                    y -= displacement;
                } else if (direction.equals(Direction.WEST)) {
                    x -= displacement;
                } else {
                    x += displacement;
                }
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

    private class Direction {
        private static final String NORTH = "N";
        private static final String SOUTH = "S";
        private static final String EAST = "E";
        private static final String WEST = "W";
    }

    private static class RotationConfiguration {
        private Map<String, String> values;

        public RotationConfiguration() {
            values = new HashMap<>();
        }

        public void add(String from, String to) {
            this.values.put(from, to);
        }

        public String apply(String from) {
            return values.get(from);
        }

        public static RotationConfiguration left() {
            RotationConfiguration result = new RotationConfiguration();
            result.add(Direction.NORTH, Direction.WEST);
            result.add(Direction.SOUTH, Direction.EAST);
            result.add(Direction.WEST, Direction.SOUTH);
            result.add(Direction.EAST, Direction.NORTH);
            return result;
        }

        public static RotationConfiguration right() {
            RotationConfiguration result = new RotationConfiguration();
            result.add(Direction.NORTH, Direction.EAST);
            result.add(Direction.SOUTH, Direction.WEST);
            result.add(Direction.WEST, Direction.NORTH);
            result.add(Direction.EAST, Direction.SOUTH);
            return result;
        }
    }
}
