// ─────────────────────────────────────────────
// Exercise 6: Proxy Pattern
// Virtual Proxy — lazy-loads a heavy image
// Protection Proxy — access control on server ops
// ─────────────────────────────────────────────

// ═══════════════════════════════════════════════
// PART A: Virtual Proxy (lazy image loading)
// ═══════════════════════════════════════════════

interface Image {
    void display();
}

// Real (expensive) object — simulates loading from disk
class RealImage implements Image {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk(); // expensive operation
    }

    private void loadFromDisk() {
        System.out.println("[RealImage] Loading image from disk: " + filename);
    }

    @Override public void display() {
        System.out.println("[RealImage] Displaying: " + filename);
    }
}

// Proxy — defers loading until display() is actually called
class ProxyImage implements Image {
    private final String filename;
    private RealImage realImage;

    public ProxyImage(String filename) {
        this.filename  = filename;
        this.realImage = null; // not loaded yet
    }

    @Override public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // lazy init
        }
        realImage.display();
    }
}

// ═══════════════════════════════════════════════
// PART B: Protection Proxy (access control)
// ═══════════════════════════════════════════════

interface Server {
    void start();
    void stop();
    void restart();
}

class RealServer implements Server {
    private final String serverName;

    public RealServer(String serverName) { this.serverName = serverName; }

    @Override public void start()   { System.out.println("[Server:" + serverName + "] Started."); }
    @Override public void stop()    { System.out.println("[Server:" + serverName + "] Stopped."); }
    @Override public void restart() { System.out.println("[Server:" + serverName + "] Restarted."); }
}

class ServerProxy implements Server {
    private final RealServer realServer;
    private final String     userRole;  // "ADMIN" or "USER"

    public ServerProxy(String serverName, String userRole) {
        this.realServer = new RealServer(serverName);
        this.userRole   = userRole;
    }

    private boolean isAdmin() { return "ADMIN".equalsIgnoreCase(userRole); }

    @Override public void start() {
        if (isAdmin()) realServer.start();
        else System.out.println("[Proxy] Access DENIED: start() requires ADMIN role.");
    }

    @Override public void stop() {
        if (isAdmin()) realServer.stop();
        else System.out.println("[Proxy] Access DENIED: stop() requires ADMIN role.");
    }

    @Override public void restart() {
        if (isAdmin()) realServer.restart();
        else System.out.println("[Proxy] Access DENIED: restart() requires ADMIN role.");
    }
}

public class Exercise6_ProxyPattern {
    public static void main(String[] args) {
        // ── Part A: Virtual Proxy ─────────────────
        System.out.println("=== Part A: Virtual Proxy (Lazy Image Loading) ===\n");

        Image img1 = new ProxyImage("banner.png");
        Image img2 = new ProxyImage("thumbnail.jpg");

        System.out.println("Images created — nothing loaded yet.");
        System.out.println("\nFirst display() call:");
        img1.display(); // loads now

        System.out.println("\nSecond display() call (same image):");
        img1.display(); // uses cached real image

        System.out.println("\nFirst display() of second image:");
        img2.display();

        // ── Part B: Protection Proxy ──────────────
        System.out.println("\n=== Part B: Protection Proxy (Access Control) ===\n");

        Server adminProxy = new ServerProxy("prod-server-01", "ADMIN");
        Server userProxy  = new ServerProxy("prod-server-01", "USER");

        System.out.println("--- Admin operations ---");
        adminProxy.start();
        adminProxy.restart();
        adminProxy.stop();

        System.out.println("\n--- Regular user operations ---");
        userProxy.start();
        userProxy.restart();
        userProxy.stop();

        System.out.println("\n--- Key Benefit ---");
        System.out.println("Virtual Proxy  : Defers expensive operations until truly needed.");
        System.out.println("Protection Proxy: Enforces access control without touching RealServer.");
    }
}
