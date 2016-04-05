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

        if (direction.equals("N")) {
            y += displacement;
        } else if (direction.equals("S")) {
            y -= displacement;
        } else if (direction.equals("W")) {
            x -= displacement;
        } else {
            x += displacement;
        }
    }

    private void rotate(String command) {
        if (direction.equals("N")) {
            if (command.equals("r")) {
                direction = "E";
            } else {
                direction = "W";
            }
        } else if (direction.equals("S")) {
            if (command.equals("r")) {
                direction = "W";
            } else {
                direction = "E";
            }
        } else if (direction.equals("W")) {
            if (command.equals("r")) {
                direction = "N";
            } else {
                direction = "S";
            }
        } else {
            if (command.equals("r")) {
                direction = "S";
            } else {
                direction = "N";
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
}
