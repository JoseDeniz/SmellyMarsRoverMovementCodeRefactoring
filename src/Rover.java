import java.util.HashMap;
import java.util.Map;

public class Rover {

    private String direction;
    private Point position;
    private final RotationConfiguration rotationLeft;
    private final RotationConfiguration rotationRight;

    public Rover(int x, int y, String direction) {
        this.setDirection(direction);
        position = new Point(x, y);
        rotationLeft = RotationConfiguration.left();
        rotationRight = RotationConfiguration.right();
    }

    public void receive(String commandsSequence) {
        for (int i = 0; i < commandsSequence.length(); ++i) {
            String command = commandsSequence.substring(i, i + 1);

            if (Command.from(command).isRotationLeft()) {
                setDirection(rotationLeft.apply(getDirection()));
            } else if (Command.from(command).isRotationRight()) {
                setDirection(rotationRight.apply(getDirection()));
            } else {
                Command.DisplacementCommand commandAction = obtainDisplacement(command);

                if (getDirection().equals(Direction.NORTH)) {
                    position = position.displaceY(commandAction.displacement());
                } else if (getDirection().equals(Direction.SOUTH)) {
                    position = position.displaceY(-commandAction.displacement());
                } else if (getDirection().equals(Direction.WEST)) {
                    position = position.displaceX(-commandAction.displacement());
                } else {
                    position = position.displaceX(commandAction.displacement());
                }
            }
        }
    }

    private Command.DisplacementCommand obtainDisplacement(String command) {
        Command.DisplacementCommand commandAction = Command.backward();

        if (command.equals(Command.FORWARD)) {
            commandAction = Command.forward();
        }
        return commandAction;
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

        if (getDirection() == null) {
            if (other.getDirection() != null)
                return false;
        } else if (!getDirection().equals(other.getDirection()))
            return false;

        if (getX() != other.getX())
            return false;

        if (getY() != other.getY())
            return false;

        return true;
    }

    public int getY() {
        return position.y;
    }

    public int getX() {
        return position.x;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point displaceY(int displacement) {
            return new Point(x, y + displacement);
        }

        public Point displaceX(int displacement) {
            return new Point(x + displacement, y);
        }
    }
}
