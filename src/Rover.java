import java.util.HashMap;
import java.util.Map;

public class Rover {

    private final RotationConfiguration rotationLeft;
    private final RotationConfiguration rotationRight;
    private Vector vector;

    public Rover(int x, int y, String directionRepresentation) {
        vector = Vector.from(new Point(x,y), null);
        this.setDirection(directionRepresentation);
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

                vector = vector.displace(commandAction);
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
        return this.vector.position.y;
    }

    public int getX() {
        return this.vector.position.x;
    }

    public String getDirection() {
        return vector.direction.representation;
    }

    public void setDirection(String representation) {
        this.vector.direction = Direction.from(representation);
    }

    private static class Direction {
        private static final String NORTH = "N";
        private static final String SOUTH = "S";
        private static final String EAST = "E";
        private static final String WEST = "W";
        private final String representation;

        public Direction(String representation) {

            this.representation = representation;
        }

        public static Direction from(String representation) {
            return new Direction(representation);
        }
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

    private static class Vector {
        private Point position;
        private Direction direction;

        public Vector(Point position, Direction direction) {
            this.position = position;
            this.direction = direction;
        }

        public static Vector from(Point position, Direction direction) {
            return new Vector(position, direction);
        }

        public Vector displace(Command.DisplacementCommand commandAction) {
            if (getDirection().equals(Direction.NORTH)) {
                return this.with(position.displaceY(commandAction.displacement()));
            } else if (getDirection().equals(Direction.SOUTH)) {
                return this.with(position.displaceY(-commandAction.displacement()));
            } else if (getDirection().equals(Direction.WEST)) {
                return this.with(position.displaceX(-commandAction.displacement()));
            } else {
                return this.with(position.displaceX(commandAction.displacement()));
            }
        }

        private Vector with(Point position) {
            return from(position, this.direction);
        }

        public String getDirection(){
            return this.direction.representation;
        }
    }
}
