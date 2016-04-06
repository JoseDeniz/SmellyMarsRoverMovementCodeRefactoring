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

    public static Command backward() {
        int displacement1 = -1;
        return null;
    }

    public static Command forward() {
       int displacement1 = 1;
        return null;
    }
}
