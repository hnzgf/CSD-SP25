public class Main {
    public static void main(String[] args) {
        registerList registeringList = new registerList();

        // Load data from file
        registeringList.loadFromFile();

        // Display all registers
        System.out.println("\nRegistering List:");
        registeringList.display();
    }
}

