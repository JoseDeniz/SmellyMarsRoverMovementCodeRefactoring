import java.util.HashMap;
import java.util.Map;

public class Rover {

    private String direction;
    private int y;
    private int x;
    private final RotationConfiguration rotationLeft;
    private final RotationConfiguration rotation;
    private final RotationConfiguration rotationRight;

    public Rover(int x, int y, String direction) {
        this.direction = direction;
        this.y = y;
        this.x = x;
        rotationLeft = new RotationConfiguration();
        rotationLeft.add(Direction.NORTH, Direction.WEST);
        rotationLeft.add(Direction.SOUTH, Direction.EAST);
        rotationLeft.add(Direction.WEST, Direction.SOUTH);
        rotationLeft.add(Direction.EAST, Direction.NORTH);
        rotationRight = new RotationConfiguration();
        rotationRight.add(Direction.NORTH, Direction.EAST);
        rotationRight.add(Direction.SOUTH, Direction.WEST);
        rotationRight.add(Direction.WEST, Direction.NORTH);
        rotationRight.add(Direction.EAST, Direction.SOUTH);
    }

    public void receive(String commandsSequence) {
        for (int i = 0; i < commandsSequence.length(); ++i) {
            String command = commandsSequence.substring(i, i + 1);

            if (command.equals("l")) {
                direction = rotationLeft.apply(direction);
            } else if (command.equals("r")) {
                direction = rotationRight.apply(direction);
            } else {
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

    private class RotationConfiguration {
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
    }
}
