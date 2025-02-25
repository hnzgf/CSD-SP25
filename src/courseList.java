import java.io.*;
import java.util.Scanner;

public class courseList { // hoặc courseList.java nếu đổi tên Class.java thành Course.java
    courseNode head; // hoặc courseNode head

    public courseList() {
        this.head = null;
    }

    // 1.2. Input & add to the end
    public void classAddLast(Course course) { // hoặc Course course
        courseNode newNode = new courseNode(course); // hoặc courseNode newNode
        if (head == null) {
            head = newNode;
        } else {
            courseNode current = head; // hoặc courseNode current
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // 1.8. Input & add to beginning
    public void classAddFirst(Course course) { // hoặc Course course
        courseNode newNode = new courseNode(course); // hoặc courseNode newNode
        newNode.next = head;
        head = newNode;
    }

    // 1.9. Add after position k
    public void classAddAfterPosition(Course course, int k) { // hoặc Course course
        if (k < 0) {
            System.out.println("Invalid position.");
            return;
        }
        courseNode newNode = new courseNode(course); // hoặc courseNode newNode
        if (k == 0 || head == null) {
            classAddFirst(course);
            return;
        }
        courseNode current = head; // hoặc courseNode current
        int count = 1;
        while (current != null && count < k) {
            current = current.next;
            count++;
        }
        if (current == null) {
            System.out.println("Position exceeds list size. Adding to the end.");
            classAddLast(course);
        } else {
            newNode.next = current.next;
            current.next = newNode;
        }
    }


    // 1.3. Display data
    public void classDisplay() {
        if (head == null) {
            System.out.println("Course list is empty!");
            return;
        }
        courseNode current = head; // hoặc courseNode current
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.info);
            current = current.next;
            position++;
        }
    }

    // 1.4. Save course list to file
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/FileFolder/courses.txt"))) {
            courseNode current = head; // hoặc courseNode current
            while (current != null) {
                Course c = current.info; // hoặc Course c
                String line = c.getCcode() + " " + c.getScode() + " " + c.getSname() + " " + c.getSemester() + " " + c.getYear() + " " + c.getSeats() + " " + c.getRegistered() + " " + c.getPrice();
                bw.write(line);
                bw.newLine();
                current = current.next;
            }
            System.out.println("Data saved to file: courses.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 1.1. Load data from file
    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/FileFolder/courses.txt"))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] parts = line.split("\\s+");
                if (parts.length != 8) {
                    System.out.println("Invalid line format in file courses.txt, line " + lineCount + ": " + line);
                    continue;
                }
                String ccode = parts[0];
                String scode = parts[1];
                String sname = parts[2];
                String semester = parts[3];
                String year = parts[4];
                int seats = Integer.parseInt(parts[5]);
                int registered = Integer.parseInt(parts[6]);
                double price = Double.parseDouble(parts[7]);

                Course course = new Course(ccode, scode, sname, semester, year, seats, registered, price);
                classAddLast(course);
            }
            System.out.println("Data loaded from file: courses.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: courses.txt.txt. Please make sure the file exists in the specified directory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1.5. Search by ccode
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

    // 1.6. Delete by ccode (with registered == 0) - **CẦN THÊM XÓA LIÊN QUAN REGISTER LIST**
    public boolean deleteByCcode(String ccode) {
        if (head == null) return false;

        Course courseToDelete = searchByCcode(ccode);
        if (courseToDelete == null) {
            System.out.println("Course with ccode " + ccode + " not found.");
            return false;
        }
        if (courseToDelete.getRegistered() > 0) {
            System.out.println("Cannot delete course with registered students. Registered count: " + courseToDelete.getRegistered());
            return false; // Không xóa nếu đã có sinh viên đăng ký
        }

        if (head.info.getCcode().equals(ccode)) {
            head = head.next;
            System.out.println("Course with ccode " + ccode + " deleted.");
            return true;
        }

        courseNode current = head; // hoặc courseNode current
        courseNode prev = null;
        while (current != null && !current.info.getCcode().equals(ccode)) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            prev.next = current.next;
            System.out.println("Course with ccode " + ccode + " deleted.");
            return true;
        }

        System.out.println("Course with ccode " + ccode + " not found for deletion (shouldn't happen).");
        return false;
    }

    // 1.7. Sort by ccode
    public void sortByCcode() {
        if (head == null || head.next == null) {
            return; // Danh sách rỗng hoặc chỉ có 1 phần tử
        }

        boolean swapped;
        courseNode current; // hoặc courseNode current
        courseNode ptr = null; // hoặc courseNode ptr

        do {
            swapped = false;
            current = head;

            while (current.next != ptr) {
                if (current.info.getCcode().compareTo(current.next.info.getCcode()) > 0) {
                    // Hoán đổi dữ liệu giữa current và current.next
                    Course temp = current.info; // hoặc Course temp
                    current.info = current.next.info;
                    current.next.info = temp;
                    swapped = true;
                }
                current = current.next;
            }
            ptr = current;
        } while (swapped);

        classDisplay(); // Hiển thị danh sách đã sắp xếp
    }

    // 1.10. Delete position k
    public boolean deletePositionK(int k) {
        if (head == null || k <= 0) {
            System.out.println("Invalid position for deletion.");
            return false;
        }
        if (k == 1) {
            head = head.next;
            System.out.println("Course at position 1 deleted.");
            return true;
        }

        courseNode current = head; // hoặc courseNode current
        courseNode prev = null; // hoặc courseNode prev
        int count = 1;
        while (current != null && count < k) {
            prev = current;
            current = current.next;
            count++;
        }

        if (current == null) {
            System.out.println("Position exceeds list size. Cannot delete.");
            return false;
        }

        prev.next = current.next;
        System.out.println("Course at position " + k + " deleted.");
        return true;
    }


    // 1.11. Search course by name
    public courseList searchByName(String name) {
        courseList resultList = new courseList();
        courseNode current = head; // hoặc courseNode current
        while (current != null) {
            if (current.info.getSname().toLowerCase().contains(name.toLowerCase())) { // Tìm kiếm không phân biệt hoa thường
                resultList.classAddLast(current.info);
            }
            current = current.next;
        }
        return resultList;
    }

    // 1.12. Search course by ccode and list students - **CẦN KẾT NỐI VỚI REGISTER LIST và STUDENT LIST**
    public void searchCourseByCcodeAndListStudents(String ccode) {
        System.out.println("Function searchCourseByCcodeAndListStudents is not fully implemented yet. Needs integration with Registering List and Student List.");
    }


    public void classMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Course List Menu ---");
            System.out.println("1.1. Load data from file");
            System.out.println("1.2. Input & add to the end");
            System.out.println("1.3. Display data");
            System.out.println("1.4. Save course list to file");
            System.out.println("1.5. Search by ccode");
            System.out.println("1.6. Delete by ccode");
            System.out.println("1.7. Sort by ccode");
            System.out.println("1.8. Input & add to beginning");
            System.out.println("1.9. Add after position k");
            System.out.println("1.10. Delete position k");
            System.out.println("1.11. Search course by name");
            System.out.println("1.12. Search course by ccode and list students");
            System.out.println("0. Back to main menu");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    loadFromFile();
                    break;
                case 2:
                    inputAndAddClassEnd(scanner);
                    break;
                case 3:
                    classDisplay();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    searchClassByCcode(scanner);
                    break;
                case 6:
                    deleteClassByCcode(scanner);
                    break;
                case 7:
                    sortClassByCcode();
                    break;
                case 8:
                    inputAndAddClassBegin(scanner);
                    break;
                case 9:
                    inputAndAddClassAfterPosition(scanner);
                    break;
                case 10:
                    deleteClassPositionK(scanner);
                    break;
                case 11:
                    searchClassByName(scanner);
                    break;
                case 12:
                    searchCourseAndListStudents(scanner);
                    break;
                case 0:
                    System.out.println("Back to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }


    // Method for menu option 1.2: Input & add course to end
    public void inputAndAddClassEnd(Scanner scanner) {
        Course newCourse = inputCourseData(scanner);
        if (newCourse != null) {
            classAddLast(newCourse);
            System.out.println("Course added to the end successfully.");
        }
    }

    // Method for menu option 1.8: Input & add course to beginning
    public void inputAndAddClassBegin(Scanner scanner) {
        Course newCourse = inputCourseData(scanner);
        if (newCourse != null) {
            classAddFirst(newCourse);
            System.out.println("Course added to the beginning successfully.");
        }
    }

    // Method for menu option 1.9: Input & add course after position k
    public void inputAndAddClassAfterPosition(Scanner scanner) {
        System.out.print("Enter position k to add after: ");
        if (scanner.hasNextInt()) {
            int k = scanner.nextInt();
            scanner.nextLine(); // consume newline
            Course newCourse = inputCourseData(scanner);
            if (newCourse != null) {
                classAddAfterPosition(newCourse, k);
                System.out.println("Course added after position " + k + " successfully.");
            }
        } else {
            System.out.println("Invalid position format. Please enter an integer.");
            scanner.nextLine(); // consume invalid input
        }
    }


    // Helper method to input course data
    private Course inputCourseData(Scanner scanner) {
        System.out.print("Enter course code (ccode): ");
        String ccode = scanner.nextLine();
        System.out.print("Enter subject code (scode): ");
        String scode = scanner.nextLine();
        System.out.print("Enter subject name (sname): ");
        String sname = scanner.nextLine();
        System.out.print("Enter semester: ");
        String semester = scanner.nextLine();
        System.out.print("Enter year: ");
        String year = scanner.nextLine();
        int seats = 0;
        boolean validSeats = false;
        while (!validSeats) {
            System.out.print("Enter seats: ");
            if (scanner.hasNextInt()) {
                seats = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (seats >= 0) {
                    validSeats = true;
                } else {
                    System.out.println("Seats must be a non-negative number. Please re-enter seats.");
                }
            } else {
                System.out.println("Invalid seats format. Please enter an integer.");
                scanner.nextLine(); // consume invalid input
            }
        }
        double price = 0;
        boolean validPrice = false;
        while (!validPrice) {
            System.out.print("Enter price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                if (price >= 0) {
                    validPrice = true;
                } else {
                    System.out.println("Price must be a non-negative number. Please re-enter price.");
                }
            } else {
                System.out.println("Invalid price format. Please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }

        return new Course(ccode, scode, sname, semester, year, seats, 0, price); // Registered starts at 0
    }


    // Method for menu option 1.5: Search course by ccode
    public void searchClassByCcode(Scanner scanner) {
        System.out.print("Enter course code to search: ");
        String ccode = scanner.nextLine();
        Course foundCourse = searchByCcode(ccode);
        if (foundCourse != null) {
            System.out.println("Course found: " + foundCourse);
        } else {
            System.out.println("Course with ccode " + ccode + " not found.");
        }
    }

    // Method for menu option 1.6: Delete course by ccode
    public void deleteClassByCcode(Scanner scanner) {
        System.out.print("Enter course code to delete: ");
        String ccode = scanner.nextLine();
        deleteByCcode(ccode);
    }

    // Method for menu option 1.7: Sort course by ccode
    public void sortClassByCcode() {
        sortByCcode();
    }

    // Method for menu option 1.10: Delete course at position k
    public void deleteClassPositionK(Scanner scanner) {
        System.out.print("Enter position k to delete: ");
        if (scanner.hasNextInt()) {
            int k = scanner.nextInt();
            scanner.nextLine(); // consume newline
            deletePositionK(k);
        } else {
            System.out.println("Invalid position format. Please enter an integer.");
            scanner.nextLine(); // consume invalid input
        }
    }


    // Method for menu option 1.11: Search course by name
    public void searchClassByName(Scanner scanner) {
        System.out.print("Enter course name to search: ");
        String name = scanner.nextLine();
        courseList foundCourses = searchByName(name);
        if (!foundCourses.isEmpty()) {
            System.out.println("Courses found:");
            foundCourses.classDisplay();
        } else {
            System.out.println("No courses.txt found with name containing: " + name);
        }
    }

    // Method for menu option 1.12: Search course by ccode and list students (Placeholder)
    public void searchCourseAndListStudents(Scanner scanner) {
        System.out.print("Enter course code to search and list students: ");
        String ccode = scanner.nextLine();
        searchCourseByCcodeAndListStudents(ccode); // Call the placeholder function
    }


    public boolean isEmpty() {
        return head == null;
    }
}