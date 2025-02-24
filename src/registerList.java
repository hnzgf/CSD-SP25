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
    // Kiểm tra xem đơn đăng kí có bị trùng không.
    private boolean isRegistered(String ccode, String scode) {
        registerNode current = head;
        while (current != null) {
            if (current.data.getCcode().equals(ccode) && current.data.getScode().equals(scode)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Phương thức để tạo thêm danh sách đăng kí.
    public void createRegister(String ccode, String scode) {
        Course course = cll.searchByCcode(ccode); // Kiểm tra xem khóa học có trong CourseList không.
        if (course == null) {
            System.out.println("Mã khóa học không tồn tại.");
            return;
        }

        Student student = sdl.searchByScode(scode); // Kiểm tra xem mã sinh viên có trong StudentList không.
        if (student == null) {
            System.out.println("Mã sinh viên không tồn tại.");
            return;
        }

        // Kiểm tra xem sinh viên đã đăng ký khóa học này chưa (trong registerList)
        if (isRegistered(ccode, scode)) {
            System.out.println("Sinh viên đã đăng ký khóa học này rồi.");
            return;
        }


        // Kiểm tra lớp còn chỗ trống không
        if (course.getSeats() <= 0) {
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

        // Cập nhật số chỗ đã đăng ký và chỗ còn lại trong Courses
        course.setRegistered(course.getRegistered() + 1); // Tăng số lượng đã đăng ký
        course.setSeats(course.getSeats() - 1); // Giảm số chỗ ngồi
    }

    public void getRegister() {
        System.out.println("Function getRegister not implemented yet.");
    }

    // Lưu registerList vào File registerings.txt.
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

    // Main
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

    // 3.2 Tạo đăng ký khóa học.
    public void inputAndAddRegister(Scanner scanner) {
        System.out.print("Enter course code (ccode): ");
        String ccode = scanner.nextLine();
        System.out.print("Enter student code (scode): ");
        String scode = scanner.nextLine();
        createRegister(ccode, scode);
    }

    // 3.6 Cập nhật điểm Khóa học của Sinh viên.
    public void updateMarkByCcodeAndScode(Scanner scanner) {
        System.out.print("Enter course code to update mark: ");
        String ccode = scanner.nextLine();
        System.out.print("Enter student code to update mark: ");
        String scode = scanner.nextLine();
        updateMarkByCcodeAndScode(ccode, scode, scanner); // Sử dụng lại Overload
    }

    //Overload
    public void updateMarkByCcodeAndScode(String ccode, String scode, Scanner scanner) {
        registerNode current = head;
        while (current != null) {
            if (current.data.getCcode().equals(ccode) && current.data.getScode().equals(scode)) {
                while (true) {
                    System.out.print("Enter new mark: ");
                    String input = scanner.nextLine();
                    if (input.trim().isEmpty()) { // Kiểm tra input rỗng
                        System.out.println("Mark cannot be empty. Please try again!");
                        continue;
                    }
                    try {
                        double newMark = Double.parseDouble(input);
                        if (newMark >= 0 && newMark <= 10 && newMark != -0.0) {
                            current.data.setMark(newMark);
                            current.data.setState(newMark >= 5 ? 1 : 0);
                            System.out.println("Mark updated successfully.");
                            return;
                        } else {
                            System.out.println("Invalid mark! (0<=Mark<=10), Please try again!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number!");
                    }
                }
            }
            current = current.next;
        }
        System.out.println("Register not found.");
    }


    public boolean isEmpty() {
        return head == null;
    }


}