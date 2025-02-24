import java.io.*;
import java.util.Scanner;

public class studentList {
    studentNode head;


    public studentList() {
        this.head = null;
    }

    // Thêm sinh viên vào cuối danh sách
    public void studentAddLast(Student student) {
        studentNode newNode = new studentNode(student);
        if (head == null) {
            head = newNode;
        } else {
            studentNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Hiển thị danh sách sinh viên
    public void studentDisplay() {
        if (head == null) {
            System.out.println("Student list is empty!");
            return;
        }
        studentNode current = head;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.info);
            current = current.next;
            position++;
        }
    }


    // Load data từ file students.txt
    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/FileFolder/students.txt"))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Invalid line format in file students.txt, line " + lineCount + ": " + line);
                    continue;
                }
                String scode = parts[0];
                String name = parts[1];
                int byear = Integer.parseInt(parts[2]);

                // Kiểm tra tuổi sinh viên (>= 18) -  **CẦN THÊM VALIDATION TUỔI**
                int currentYear = java.time.Year.now().getValue();
                if (currentYear - byear < 18) {
                    System.out.println("Invalid student data at line " + lineCount + ": Student must be at least 18 years old.");
                    continue; // Bỏ qua sinh viên không hợp lệ
                }


                Student student = new Student(scode, name, byear);
                studentAddLast(student);
            }
            System.out.println("Data loaded from file: students.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: students.txt. Please make sure the file exists in the specified directory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lưu data vào file students.txt
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/FileFolder/students.txt"))) {
            studentNode current = head;
            while (current != null) {
                Student s = current.info;
                String line = s.getScode() + " " + s.getName() + " " + s.getByear();
                bw.write(line);
                bw.newLine();
                current = current.next;
            }
            System.out.println("Data saved to file: students.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm sinh viên theo scode
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

    // Xóa sinh viên theo scode (và cần xóa trong registerList nếu có liên quan -  **CHƯA IMPLEMENT XÓA LIÊN QUAN REGISTER**)
    public boolean deleteByScode(String scode) {
        if (head == null) return false;

        if (head.info.getScode().equals(scode)) {
            head = head.next;
            System.out.println("Student with scode " + scode + " deleted.");
            return true;
        }

        studentNode current = head;
        studentNode prev = null;
        while (current != null && !current.info.getScode().equals(scode)) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            prev.next = current.next;
            System.out.println("Student with scode " + scode + " deleted.");
            return true;
        }

        System.out.println("Student with scode " + scode + " not found.");
        return false;
    }

    // Tìm kiếm sinh viên theo tên
    public studentList searchByName(String name) {
        studentList resultList = new studentList();
        studentNode current = head;
        while (current != null) {
            if (current.info.getName().toLowerCase().contains(name.toLowerCase())) { // Tìm kiếm không phân biệt hoa thường
                resultList.studentAddLast(current.info);
            }
            current = current.next;
        }
        return resultList;
    }

    // Tìm kiếm các khóa học đã đăng ký bởi sinh viên (dựa trên scode) - **CHƯA IMPLEMENT, CẦN KẾT NỐI VỚI REGISTER LIST**
    public void searchRegisteredCoursesByScode(String scode) {
        System.out.println("Function searchRegisteredCoursesByScode is not fully implemented yet. Needs integration with Registering List.");
    }


    public void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Student List Menu ---");
            System.out.println("2.1. Load data from file");
            System.out.println("2.2. Input & add to the end");
            System.out.println("2.3. Display data");
            System.out.println("2.4. Save student list to file");
            System.out.println("2.5. Search by scode");
            System.out.println("2.6. Delete by scode");
            System.out.println("2.7. Search by name (student name)");
            System.out.println("2.8. Search registered courses by scode");
            System.out.println("0. Back to main menu");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    loadFromFile();
                    break;
                case 2:
                    inputAndAddStudent(scanner);
                    break;
                case 3:
                    studentDisplay();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    searchStudentByScode(scanner);
                    break;
                case 6:
                    deleteStudentByScode(scanner);
                    break;
                case 7:
                    searchStudentByName(scanner);
                    break;
                case 8:
                    searchRegisteredCourses(scanner);
                    break;
                case 0:
                    System.out.println("Back to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Method for menu option 2.2: Input & add student
    public void inputAndAddStudent(Scanner scanner) {
        System.out.print("Enter student code (scode): ");
        String scode = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        int byear = 0;
        boolean validByear = false;
        while (!validByear) {
            System.out.print("Enter birth year: ");
            if (scanner.hasNextInt()) {
                byear = scanner.nextInt();
                scanner.nextLine(); // consume newline
                int currentYear = java.time.Year.now().getValue();
                if (currentYear - byear >= 18) {
                    validByear = true;
                } else {
                    System.out.println("Student must be at least 18 years old. Please re-enter birth year.");
                }
            } else {
                System.out.println("Invalid year format. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }


        Student newStudent = new Student(scode, name, byear);
        studentAddLast(newStudent);
        System.out.println("Student added successfully.");
    }

    // Method for menu option 2.5: Search student by scode
    public void searchStudentByScode(Scanner scanner) {
        System.out.print("Enter student code to search: ");
        String scode = scanner.nextLine();
        Student foundStudent = searchByScode(scode);
        if (foundStudent != null) {
            System.out.println("Student found: " + foundStudent);
        } else {
            System.out.println("Student with scode " + scode + " not found.");
        }
    }

    // Method for menu option 2.6: Delete student by scode
    public void deleteStudentByScode(Scanner scanner) {
        System.out.print("Enter student code to delete: ");
        String scode = scanner.nextLine();
        deleteByScode(scode);
    }

    // Method for menu option 2.7: Search student by name
    public void searchStudentByName(Scanner scanner) {
        System.out.print("Enter student name to search: ");
        String name = scanner.nextLine();
        studentList foundStudents = searchByName(name);
        if (!foundStudents.isEmpty()) {
            System.out.println("Students found:");
            foundStudents.studentDisplay();
        } else {
            System.out.println("No students found with name containing: " + name);
        }
    }

    // Method for menu option 2.8: Search registered courses (Placeholder - Needs RegisterList integration)
    public void searchRegisteredCourses(Scanner scanner) {
        System.out.print("Enter student code to search registered courses: ");
        String scode = scanner.nextLine();
        searchRegisteredCoursesByScode(scode); // Call the placeholder function
    }


    public boolean isEmpty() {
        return head == null;
    }
}