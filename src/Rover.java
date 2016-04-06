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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Direction direction = (Direction) o;

            return representation != null ? representation.equals(direction.representation) : direction.representation == null;

        }

        @Override
        public int hashCode() {
            return representation != null ? representation.hashCode() : 0;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
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
            } else if (getDirection().equals(Direction.EAST)){
                return this.with(position.displaceX(commandAction.displacement()));
            } else {
                throw new RuntimeException("Defect: a direction not in the 4 cardinal points");
            }
        }

        private Vector with(Point position) {
            return from(position, this.direction);
        }

        public String getDirection(){
            return this.direction.representation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vector vector = (Vector) o;

            if (position != null ? !position.equals(vector.position) : vector.position != null) return false;
            return direction != null ? direction.equals(vector.direction) : vector.direction == null;

        }

        @Override
        public int hashCode() {
            int result = position != null ? position.hashCode() : 0;
            result = 31 * result + (direction != null ? direction.hashCode() : 0);
            return result;
        }
    }
}
