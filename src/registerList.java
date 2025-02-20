import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;


public class registerList {
    private registerNode head;

    // Constructor
    public registerList() {
        this.head = null;
    }

    // Method to add a new register to the end of the list
    public void add(Register register) {
        registerNode newNode = new registerNode(register);
        if (head == null) {
            head = newNode;
        } else {
            registerNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Method to display all registers
    public void display() {
        if (head == null) {
            System.out.println("Registering list is empty!");
            return;
        }
        registerNode current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    // Method to load data from file
    public void loadFromFile(String filename) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+"); // Split by spaces
                if (parts.length != 5) {
                    System.out.println("Invalid line format: " + line);
                    continue;
                }
                String ccode = parts[0];
                String scode = parts[1];
                Date bdate = sdf.parse(parts[2]);
                double mark = Double.parseDouble(parts[3]);
                int state = Integer.parseInt(parts[4]);

                // Add to the linked list
                Register register = new Register(ccode, scode, bdate, mark, state);
                this.add(register);
            }
            System.out.println("Data loaded from file: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
