import java.util.ArrayDeque;
import java.util.Deque;

// ─────────────────────────────────────────────
// Exercise 9: Command Pattern
// Smart Home / Text Editor with Undo-Redo
// Encapsulates requests as objects, enabling
// queuing, logging, and undo/redo operations.
// ─────────────────────────────────────────────

// ── Command Interface ─────────────────────────
interface Command {
    void execute();
    void undo();
    String getDescription();
}

// ═══════════════════════════════════════════════
// PART A: Smart Home
// ═══════════════════════════════════════════════

class Light {
    private final String room;
    private boolean isOn = false;

    public Light(String room) { this.room = room; }

    public void turnOn()  { isOn = true;  System.out.println("[Light:" + room + "] ON"); }
    public void turnOff() { isOn = false; System.out.println("[Light:" + room + "] OFF"); }
    public boolean isOn() { return isOn; }
}

class AirConditioner {
    private boolean isOn;
    private int temperature;

    public void turnOn(int temp) {
        isOn = true; temperature = temp;
        System.out.println("[AC] ON at " + temp + "°C");
    }
    public void turnOff() { isOn = false; System.out.println("[AC] OFF"); }
    public int getTemperature() { return temperature; }
}

// ── Light Commands ────────────────────────────
class LightOnCommand implements Command {
    private final Light light;

    public LightOnCommand(Light light) { this.light = light; }

    @Override public void execute()          { light.turnOn(); }
    @Override public void undo()             { light.turnOff(); }
    @Override public String getDescription() { return "Light ON"; }
}

class LightOffCommand implements Command {
    private final Light light;

    public LightOffCommand(Light light) { this.light = light; }

    @Override public void execute()          { light.turnOff(); }
    @Override public void undo()             { light.turnOn(); }
    @Override public String getDescription() { return "Light OFF"; }
}

// ── AC Commands ───────────────────────────────
class ACOnCommand implements Command {
    private final AirConditioner ac;
    private final int temperature;
    private int previousTemp;

    public ACOnCommand(AirConditioner ac, int temperature) {
        this.ac          = ac;
        this.temperature = temperature;
    }

    @Override public void execute() { previousTemp = ac.getTemperature(); ac.turnOn(temperature); }
    @Override public void undo()    { ac.turnOn(previousTemp); }
    @Override public String getDescription() { return "AC ON at " + temperature + "°C"; }
}

class ACOffCommand implements Command {
    private final AirConditioner ac;

    public ACOffCommand(AirConditioner ac) { this.ac = ac; }

    @Override public void execute()          { ac.turnOff(); }
    @Override public void undo()             { ac.turnOn(24); } // default resume
    @Override public String getDescription() { return "AC OFF"; }
}

// ── Remote Control (Invoker) ──────────────────
class RemoteControl {
    private final Deque<Command> history = new ArrayDeque<>();

    public void pressButton(Command command) {
        System.out.print("[Remote] Execute → ");
        command.execute();
        history.push(command);
    }

    public void pressUndo() {
        if (history.isEmpty()) { System.out.println("[Remote] Nothing to undo."); return; }
        Command last = history.pop();
        System.out.print("[Remote] Undo '" + last.getDescription() + "' → ");
        last.undo();
    }
}

public class Exercise9_CommandPattern {
    public static void main(String[] args) {
        // ── Receivers ────────────────────────────
        Light        livingRoomLight = new Light("LivingRoom");
        Light        bedroomLight    = new Light("Bedroom");
        AirConditioner ac            = new AirConditioner();

        // ── Commands ─────────────────────────────
        Command lightOn   = new LightOnCommand(livingRoomLight);
        Command lightOff  = new LightOffCommand(livingRoomLight);
        Command bedOn     = new LightOnCommand(bedroomLight);
        Command acOn22    = new ACOnCommand(ac, 22);
        Command acOff     = new ACOffCommand(ac);

        // ── Invoker ───────────────────────────────
        RemoteControl remote = new RemoteControl();

        System.out.println("=== Smart Home Control ===\n");

        remote.pressButton(lightOn);
        remote.pressButton(acOn22);
        remote.pressButton(bedOn);
        remote.pressButton(acOff);

        System.out.println("\n--- Undo last 2 commands ---");
        remote.pressUndo();
        remote.pressUndo();

        System.out.println("\n--- Undo again ---");
        remote.pressUndo();

        System.out.println("\n--- Key Benefit ---");
        System.out.println("Commands are first-class objects: store, queue, log, undo.");
        System.out.println("Invoker (Remote) is decoupled from Receivers (Light, AC).");
    }
}
