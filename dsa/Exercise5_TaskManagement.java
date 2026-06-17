// ─────────────────────────────────────────────
// Exercise 5: Task Management System
// Data Structure: Singly Linked List
//
// Time Complexity:
//   addFirst / addLast → O(1)
//   search             → O(n)
//   traverse           → O(n)
//   delete             → O(n)
// ─────────────────────────────────────────────

class Task {
    int    taskId;
    String taskName;
    String status; // "PENDING", "IN_PROGRESS", "DONE"

    public Task(int taskId, String taskName, String status) {
        this.taskId   = taskId;
        this.taskName = taskName;
        this.status   = status;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, name=%s, status=%s]", taskId, taskName, status);
    }
}

class Node {
    Task data;
    Node next;

    Node(Task data) {
        this.data = data;
        this.next = null;
    }
}

class TaskLinkedList {
    private Node head;
    private int  size;

    /** Add at head: O(1) */
    public void addFirst(Task task) {
        Node newNode = new Node(task);
        newNode.next = head;
        head = newNode;
        size++;
        System.out.println("Added (head): " + task);
    }

    /** Add at tail: O(n) without tail pointer; O(1) with one */
    public void addLast(Task task) {
        Node newNode = new Node(task);
        if (head == null) { head = newNode; size++; return; }
        Node curr = head;
        while (curr.next != null) curr = curr.next;
        curr.next = newNode;
        size++;
        System.out.println("Added (tail): " + task);
    }

    /** Search: O(n) */
    public Task search(int taskId) {
        Node curr = head;
        while (curr != null) {
            if (curr.data.taskId == taskId) return curr.data;
            curr = curr.next;
        }
        return null;
    }

    /** Traverse: O(n) */
    public void traverse() {
        if (head == null) { System.out.println("  List is empty."); return; }
        System.out.println("--- Tasks (" + size + ") ---");
        Node curr = head;
        while (curr != null) {
            System.out.println("  " + curr.data);
            curr = curr.next;
        }
    }

    /** Delete: O(n) — scan for node then unlink */
    public boolean delete(int taskId) {
        if (head == null) return false;
        if (head.data.taskId == taskId) { // O(1) head deletion
            System.out.println("Deleted: " + head.data);
            head = head.next;
            size--;
            return true;
        }
        Node prev = head, curr = head.next;
        while (curr != null) {
            if (curr.data.taskId == taskId) {
                prev.next = curr.next;
                size--;
                System.out.println("Deleted: " + curr.data);
                return true;
            }
            prev = curr; curr = curr.next;
        }
        System.out.println("Task ID " + taskId + " not found.");
        return false;
    }
}

public class Exercise5_TaskManagement {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        list.addLast(new Task(1, "Design DB schema",  "DONE"));
        list.addLast(new Task(2, "Build REST API",    "IN_PROGRESS"));
        list.addLast(new Task(3, "Write Unit Tests",  "PENDING"));
        list.addFirst(new Task(0, "Gather Requirements", "DONE"));

        System.out.println();
        list.traverse();

        System.out.println("\n--- Search Task ID 2 ---");
        Task t = list.search(2);
        System.out.println(t != null ? "Found: " + t : "Not Found");

        System.out.println("\n--- Delete Task ID 1 ---");
        list.delete(1);
        list.traverse();

        System.out.println("\n--- Complexity vs Array ---");
        System.out.println("Linked List add/delete (head) : O(1) — no shifting needed");
        System.out.println("Array      add/delete (mid)   : O(n) — shifting required");
        System.out.println("Linked List search            : O(n) — no random access");
        System.out.println("Array      access by index    : O(1) — contiguous memory");
    }
}
