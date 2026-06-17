// ─────────────────────────────────────────────
// Exercise 3: Builder Pattern
// Constructs complex objects step-by-step.
// Optional fields are handled cleanly.
// ─────────────────────────────────────────────

class Computer {
    // Required
    private final String cpu;
    private final int    ramGB;
    // Optional
    private final int    storageGB;
    private final String gpu;
    private final boolean bluetoothEnabled;
    private final boolean wifiEnabled;

    // Private constructor — only Builder can call this
    private Computer(Builder builder) {
        this.cpu              = builder.cpu;
        this.ramGB            = builder.ramGB;
        this.storageGB        = builder.storageGB;
        this.gpu              = builder.gpu;
        this.bluetoothEnabled = builder.bluetoothEnabled;
        this.wifiEnabled      = builder.wifiEnabled;
    }

    @Override
    public String toString() {
        return String.format(
            "Computer{cpu='%s', ram=%dGB, storage=%dGB, gpu='%s', BT=%b, WiFi=%b}",
            cpu, ramGB, storageGB, gpu, bluetoothEnabled, wifiEnabled);
    }

    // ── Static nested Builder ─────────────────
    public static class Builder {
        // Required fields
        private final String cpu;
        private final int    ramGB;
        // Optional with defaults
        private int     storageGB        = 512;
        private String  gpu              = "Integrated";
        private boolean bluetoothEnabled = false;
        private boolean wifiEnabled      = true;

        public Builder(String cpu, int ramGB) {
            if (cpu == null || cpu.isEmpty()) throw new IllegalArgumentException("CPU is required.");
            if (ramGB <= 0)                   throw new IllegalArgumentException("RAM must be positive.");
            this.cpu   = cpu;
            this.ramGB = ramGB;
        }

        public Builder storageGB(int storageGB)           { this.storageGB        = storageGB;  return this; }
        public Builder gpu(String gpu)                    { this.gpu              = gpu;         return this; }
        public Builder bluetoothEnabled(boolean enabled)  { this.bluetoothEnabled = enabled;    return this; }
        public Builder wifiEnabled(boolean enabled)       { this.wifiEnabled      = enabled;    return this; }

        public Computer build() { return new Computer(this); }
    }
}

public class Exercise3_BuilderPattern {
    public static void main(String[] args) {
        // Gaming PC — all options set
        Computer gamingPC = new Computer.Builder("Intel Core i9-14900K", 64)
                .storageGB(2048)
                .gpu("NVIDIA RTX 4090")
                .bluetoothEnabled(true)
                .wifiEnabled(true)
                .build();

        // Budget Office PC — only required fields
        Computer officePC = new Computer.Builder("Intel Core i5-13400", 16)
                .storageGB(512)
                .build();

        // Developer Workstation
        Computer devWorkstation = new Computer.Builder("AMD Ryzen 9 7950X", 128)
                .storageGB(4096)
                .gpu("NVIDIA RTX 3060")
                .bluetoothEnabled(false)
                .wifiEnabled(true)
                .build();

        System.out.println("=== Computer Configurations ===");
        System.out.println("Gaming PC     : " + gamingPC);
        System.out.println("Office PC     : " + officePC);
        System.out.println("Dev Workstation: " + devWorkstation);

        System.out.println("\n--- Why Builder? ---");
        System.out.println("Avoids telescoping constructors with many optional params.");
        System.out.println("Produces immutable objects with a readable, fluent API.");
    }
}
