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
    public void loadFromFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        try (BufferedReader br = new BufferedReader(new FileReader("src/FileFolder/registerings.txt"))) {
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
            System.out.println("Data loaded from file: " + "registerings.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean findCcode(String ccode) {
        registerNode temp = head;
        while (temp.next != null) {
            if (temp.ccode == ccode) {
                return true;
            }
            temp = temp.next;
        }
        return false;

    }
    public boolean findScode(String scode) {
        registerNode temp = head;
        while (temp.next != null) {
            if (temp.scode == scode) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void createRegister(String ccode, String scode) {
        if (findCcode(ccode)) {
            System.out.println("Khóa học không tồn tại.");
            return;
        }
        if (findScode(scode)) {
            System.out.println("Sinh viên không tồn tại.");
            return;
        }
        if (classNode.seats == 0) {
            System.out.println("Lớp đã hết chỗ.");
            return;
        }
        Date bdate = new Date();
        int mark = 0;
        int state = 0;
        newNode.next = head;
        head = newNode;
        Register register = new Register(ccode, scode, bdate, mark, state);
        this.add(register);
        classNode.seats--;
        classNode.registered++;
        System.out.println("Đã đăng ký thành công");
    }
    public void saveToFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/FileFolder/registerings.txt"))) {
            registerNode current = head;
            while (current != null) {
                Register r = current.data;
                String line = r.getCcode() + " " + r.getScode() + " " + sdf.format(r.getBdate()) + " " + r.getMark() + " " + r.getState();
                bw.write(line);
                bw.newLine();
                current = current.next;
            }
            System.out.println("Data saved to file: registerings.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sortbyCcodeandScode() {
        if (head == null || head.next == null) {
            return; // Danh sách rỗng hoặc chỉ có 1 phần tử
        }

        boolean swapped;
        registerNode current;
        registerNode ptr = null;

        do {
            swapped = false;
            current = head;

            while (current.next != ptr) {
                if (current.data.getCcode().compareTo(current.next.data.getCcode()) > 0 ||
                        (current.data.getCcode().equals(current.next.data.getCcode()) &&
                                current.data.getScode().compareTo(current.next.data.getScode()) > 0)) {
                    // Hoán đổi dữ liệu giữa current và current.next
                    Register temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
            ptr = current;
        } while (swapped);

        display(); // Hiển thị danh sách đã sắp xếp
    }
    public void updateMarkByCcodeAndScode(String ccode, String scode) {
        Scanner scanner = new Scanner(System.in);
        registerNode current = head;
        while (current != null) {
            if (current.data.getCcode().equals(ccode) && current.data.getScode().equals(scode)) {
                System.out.print("Enter new mark: ");
                double newMark = scanner.nextDouble();
                current.data.setMark(newMark);
                System.out.println("Mark updated successfully.");
                return;
            }
            current = current.next;
        }
        System.out.println("Register not found.");
    }
    classSortByCcode()

}
