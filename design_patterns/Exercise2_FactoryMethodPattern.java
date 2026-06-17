// ─────────────────────────────────────────────
// Exercise 2: Factory Method Pattern
// Document Management System
// Decouples object creation from usage.
// ─────────────────────────────────────────────

// ── Product Interface ─────────────────────────
interface Document {
    void open();
    void save();
    String getType();
}

// ── Concrete Products ─────────────────────────
class WordDocument implements Document {
    @Override public void open()  { System.out.println("[Word] Opening .docx file..."); }
    @Override public void save()  { System.out.println("[Word] Saving as .docx..."); }
    @Override public String getType() { return "Word Document"; }
}

class PdfDocument implements Document {
    @Override public void open()  { System.out.println("[PDF] Rendering PDF viewer..."); }
    @Override public void save()  { System.out.println("[PDF] Exporting as .pdf..."); }
    @Override public String getType() { return "PDF Document"; }
}

class ExcelDocument implements Document {
    @Override public void open()  { System.out.println("[Excel] Loading spreadsheet..."); }
    @Override public void save()  { System.out.println("[Excel] Saving .xlsx..."); }
    @Override public String getType() { return "Excel Document"; }
}

// ── Abstract Creator ──────────────────────────
abstract class DocumentFactory {
    // Factory Method — subclasses decide which Document to create
    public abstract Document createDocument();

    // Template method: common workflow
    public void openDocument() {
        Document doc = createDocument();
        System.out.println("Created: " + doc.getType());
        doc.open();
        doc.save();
        System.out.println();
    }
}

// ── Concrete Factories ────────────────────────
class WordDocumentFactory extends DocumentFactory {
    @Override public Document createDocument() { return new WordDocument(); }
}

class PdfDocumentFactory extends DocumentFactory {
    @Override public Document createDocument() { return new PdfDocument(); }
}

class ExcelDocumentFactory extends DocumentFactory {
    @Override public Document createDocument() { return new ExcelDocument(); }
}

public class Exercise2_FactoryMethodPattern {
    public static void main(String[] args) {
        DocumentFactory[] factories = {
            new WordDocumentFactory(),
            new PdfDocumentFactory(),
            new ExcelDocumentFactory()
        };

        System.out.println("=== Document Management System ===\n");
        for (DocumentFactory factory : factories) {
            factory.openDocument();
        }

        System.out.println("--- Key Benefit ---");
        System.out.println("Adding a new document type (e.g., CSV) requires only");
        System.out.println("a new class — no modification to existing code. (Open/Closed Principle)");
    }
}
