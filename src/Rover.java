import java.util.HashMap;
import java.util.Map;

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
        for (int i = 0; i < commandsSequence.length(); ++i) {
            String command = commandsSequence.substring(i, i + 1);

            if (command.equals("l")) {
                Actions actions = new Actions();
                actions.add(Direction.NORTH, Direction.WEST);
                // Rotate Rover
                if (direction.equals(Direction.NORTH)) {
                    direction = actions.apply(Direction.NORTH);
                } else if (direction.equals(Direction.SOUTH)) {
                    direction = Direction.EAST;
                } else if (direction.equals("W")) {
                    direction = Direction.SOUTH;
                } else {
                    direction = Direction.NORTH;
                }
            } else if (command.equals("r")) {
                // Rotate Rover
                if (direction.equals(Direction.NORTH)) {
                    direction = Direction.EAST;
                } else if (direction.equals(Direction.SOUTH)) {
                    direction = "W";
                } else if (direction.equals("W")) {
                    direction = Direction.NORTH;
                } else {
                    direction = Direction.SOUTH;
                }
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
                } else if (direction.equals("W")) {
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
        private static final String WEST = "W";
        private static final String SOUTH = "S";
        private static final String EAST = "E";
    }

    private class Actions {
        private Map<String, String> values;

        public Actions() {
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
