import java.text.SimpleDateFormat;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class registerList {
    private registerNode head;
    courseList cll = new courseList();
    studentList sdl = new studentList();

    // Constructor
    public registerList() {
        this.head = null;
    }

    // Phương thức thêm một Register vào cuối danh sách.
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

    // Phương thức in ra màn hình tất cả danh sách
    public void display() {
        if (head == null) {
            System.out.println("Registering list is empty!");
            return;
        }
        registerNode current = head;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.data);
            current = current.next;
            position++;
        }
    }

    // Phương thức load data từ file regisisster.txt
    public void loadFromFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader("src/FileFolder/registerings.txt"))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] parts = line.split("\\s+"); // Split by spaces
                if (parts.length != 5) {
                    System.out.println("Invalid line format in file registerings.txt, line " + lineCount + ": " + line);
                    continue;
                }
                String ccode = parts[0];
                String scode = parts[1];
                Date bdate = sdf.parse(parts[2]);
                double mark = Double.parseDouble(parts[3]);
                int state = Integer.parseInt(parts[4]);

                // Thêm các data load từ file vào registerLost
                Register register = new Register(ccode, scode, bdate, mark, state);
                this.add(register);
            }
            System.out.println("Data loaded from file: " + "registerings.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: registerings.txt. Please make sure the file exists in the specified directory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm xem Ccode có trong danh sách không
    public boolean findCcode(String ccode) {
        registerNode temp = head;
        if (head == null) return false; // handle empty list case
        while (temp != null) { // changed from temp.next to temp to check the last node
            if (temp.data.getCcode().equals(ccode)) { // use .equals for String comparison
                return true;
            }
            temp = temp.next;
        }
        return false;

    }

    // TÌm xem Scode có trong dsanh sách không
    public boolean findScode(String scode) {
        registerNode temp = head;
        if (head == null) return false; // handle empty list case
        while (temp != null) { // changed from temp.next to temp to check the last node
            if (temp.data.getScode().equals(scode)) { // use .equals for String comparison
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    private boolean isRegistered(String ccode, String scode) {
        registerNode current = head;
        while (current != null) {
            if (current.data.getCcode().equals(ccode) && current.data.getScode().equals(scode)) {
                return true; // Đã đăng ký rồi
            }
            current = current.next;
        }
        return false; // Chưa đăng ký
    }

    // Phương thức để tạo thêm danh sách đăng kí.
    public void createRegister(String ccode, String scode) {
        Course course = cll.searchByCcode(ccode);
        if (course == null) {
            System.out.println("Mã khóa học không tồn tại.");
            return;
        }

        // 2. Kiểm tra sinh viên (scode) có tồn tại trong studentList không
        Student student = sdl.searchByScode(scode);
        if (student == null) {
            System.out.println("Mã sinh viên không tồn tại.");
            return;
        }

        // 3. Kiểm tra xem sinh viên đã đăng ký khóa học này chưa (trong registerList)
        if (isRegistered(ccode, scode)) {
            System.out.println("Sinh viên đã đăng ký khóa học này rồi.");
            return;
        }


        // 4. Kiểm tra lớp còn chỗ trống không
        if (course.getRegistered() >= course.getSeats()) { // Sử dụng getRegistered() và getSeats() của Class object
            System.out.println("Lớp học đã hết chỗ.");
            return;
        }
        Date bdate = new Date();
        double mark = 0;
        int state = 0;
        Register newRegister = new Register(ccode, scode, bdate, mark, state);
        registerNode newNode = new registerNode(newRegister);
        newNode.next = head;
        head = newNode;

        // 5. Cập nhật số chỗ đã đăng ký và chỗ còn lại trong Class object (ĐÃ THÊM CODE VÀO ĐÂY)
        course.setRegistered(course.getRegistered() + 1); // Tăng số lượng đã đăng ký
        course.setSeats(course.getSeats() - 1); // Giảm số chỗ ngồi
    }

    public void getRegister() {
        System.out.println("Function getRegister not implemented yet.");
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
    void classSortByScode(){
        System.out.println("Function classSortByScode is not relevant to registerList and not implemented.");
    }
    void classSwap(studentNode x, studentNode y){
        System.out.println("Function classSwap is not relevant to registerList and not implemented.");
    }
    public studentList getStudentsByCcode(String ccode, studentList studentList) {
        studentList resultList = new studentList(); // Assuming studentList class exists
        registerNode current = head;
        while (current != null) {
            if (current.data.getCcode().equals(ccode)) {
                // Find the student in studentList using scode from register
                studentNode studentNode = studentList.head; // Assuming studentList has head and studentNode
                while (studentNode != null) {
                    if (studentNode.info.scode.equals(current.data.getScode())) { // Assuming studentNode.info is Register and has scode
                        resultList.studentAddLast(studentNode); // Assuming studentList has studentAddLast and takes studentNode
                        break; // Student found, no need to continue inner loop
                    }
                    studentNode = studentNode.next;
                }
            }
            current = current.next;
        }
        return resultList;
    }
    public classList getCoursesByScode(String scode, classList courseList) {
        classList resultList = new classList(); // Assuming classList class exists
        registerNode current = head;
        while (current != null) {
            if (current.data.getScode().equals(scode)) {
                // Find the course in courseList using ccode from register
                courseNode courseNode = courseList.head; // Assuming classList has head and classNode
                while (courseNode != null) {
                    if (courseNode.info.ccode.equals(current.data.getCcode())) { // Assuming courseNode.info is Class and has ccode
                        resultList.classAddLast(courseNode); // Assuming classList has classAddLast and takes classNode
                        break; // Course found, no need to continue inner loop
                    }
                    courseNode = courseNode.next;
                }
            }
            current = current.next;
        }
        return resultList;
    }
    public void registerMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Registering List Menu ---");
            System.out.println("3.1. Load data from file");
            System.out.println("3.2. Register the course");
            System.out.println("3.3. Display data");
            System.out.println("3.4. Save registering list to file");
            System.out.println("3.5. Sort by ccode + scode");
            System.out.println("3.6. Update mark by ccode + scode");
            System.out.println("0. Back to main menu");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    loadFromFile();
                    break;
                case 2:
                    inputAndAddRegister(scanner);
                    break;
                case 3:
                    display();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    sortbyCcodeandScode();
                    break;
                case 6:
                    updateMarkByCcodeAndScode(scanner);
                    break;
                case 0:
                    System.out.println("Back to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Method for menu option 3.2: Register the course
    public void inputAndAddRegister(Scanner scanner) {
        System.out.print("Enter course code (ccode): ");
        String ccode = scanner.nextLine();
        System.out.print("Enter student code (scode): ");
        String scode = scanner.nextLine();

        // Assuming createRegister handles checks and adds the register
        createRegister(ccode, scode);
    }


    // Method for menu option 3.6: Update mark by ccode + scode
    public void updateMarkByCcodeAndScode(Scanner scanner) {
        System.out.print("Enter course code to update mark: ");
        String ccode = scanner.nextLine();
        System.out.print("Enter student code to update mark: ");
        String scode = scanner.nextLine();
        updateMarkByCcodeAndScode(ccode, scode, scanner); // Call the overloaded method
    }

    //Overload method for updateMarkByCcodeAndScode to take Scanner as argument
    public void updateMarkByCcodeAndScode(String ccode, String scode, Scanner scanner) {
        registerNode current = head;
        while (current != null) {
            if (current.data.getCcode().equals(ccode) && current.data.getScode().equals(scode)) {
                System.out.print("Enter new mark: ");
                double newMark = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                current.data.setMark(newMark);
                System.out.println("Mark updated successfully.");
                return;
            }
            current = current.next;
        }
        System.out.println("Register not found.");
    }


    public boolean isEmpty() {
        return head == null;
    }


    // Example studentLis/t and classList classes - you might have these defined elsewhere
    public static class studentList {
        studentNode head;
        public studentList() {
            this.head = null;
        }
        public void studentAddLast(studentNode node) {
            if (head == null) {
                head = node;
            } else {
                studentNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = node;
            }
        }
        public boolean isEmpty() {
            return head == null;
        }
        public void display() {
            if (head == null) {
                System.out.println("Student list is empty.");
                return;
            }
            studentNode current = head;
            while (current != null) {
                System.out.println(current.info); // Assuming studentNode.info.toString() is defined
                current = current.next;
            }
        }

        public Student searchByScode(String scode) {
            studentNode current = head;
            while (current != null) {
                if (current.info.getScode().equals(scode)) {
                    return current.info;
                }
                current = current.next;
            }
            return null; // Not found
        }
    }

    public static class classList {
        courseNode head;

        public classList() {
            this.head = null;
        }

        public void classAddLast(courseNode node) {
            if (head == null) {
                head = node;
            } else {
                courseNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = node;
            }
        }

        public Course searchByCcode(String ccode) {
            courseNode current = head; // hoặc courseNode current
            while (current != null) {
                if (current.info.getCcode().equals(ccode)) {
                    return current.info;
                }
                current = current.next;
            }
            return null; // Not found
        }
    }

}