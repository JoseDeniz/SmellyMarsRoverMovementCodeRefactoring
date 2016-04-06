public class Command {
    static final String FORWARD = "f";
    static final String RIGHT = "r";
    protected static final String LEFT = "l";
    private String representation;

    public Command(String representation) {
        this.representation = representation;
    }

    boolean isRotationRight() {
        return representation.equals(RIGHT);
    }

    boolean isRotationLeft() {
        return representation.equals(LEFT);
    }

    public static Command from(String representation) {

        return new Command(representation);
    }

    public static DisplacementCommand backward() {
        int displacement = -1;
        return new DisplacementCommand(displacement);
    }

    public static DisplacementCommand forward() {
        int displacement = 1;
        return new DisplacementCommand(displacement);
    }

    public static class DisplacementCommand {
        private final int displacement;

        public DisplacementCommand(int displacement) {
            this.displacement = displacement;
        }

        public int displacement() {
            return displacement;
        }
    }
}
