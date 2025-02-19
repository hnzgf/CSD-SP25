
public class Student {
    
    String scode;
    String name;
    int byear;

    public Student(String scode, String name, int byear) {
        this.scode = scode;
        this.name = name;
        this.byear = byear;
    }

    @Override
    public String toString() {
        return "Student{" + "sid=" + scode + ", name=" + name + ", mark=" + byear + '}';
    }

}
