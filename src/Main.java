import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        courseList courseList = new courseList();
        studentList studentList = new studentList();
        registerList registerList = new registerList();

        int choice;
        do {
            System.out.println("\n--- Course Management System ---");
            System.out.println("1. Course List");
            System.out.println("2. Student List");
            System.out.println("3. Registering List");
            System.out.println("0. Exit");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    courseList.classMenu();
                    break;
                case 2:
                    studentList.studentMenu();
                    break;
                case 3:
                    registerList.registerMenu();
                    break;
                case 0:
                    System.out.println("Exiting CMS. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

