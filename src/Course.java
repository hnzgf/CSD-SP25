
public class Course {
    String ccode;
    String scode;
    String sname;
    String semester;
    String year;
    int seats;
    int registered;
    double price;

    public Course(String ccode, String scode, String sname, String semester, String year, int seats, int registered, double price) {
        this.ccode = ccode;
        this.scode = scode;
        this.sname = sname;
        this.semester = semester;
        this.year = year;
        this.seats = seats;
        this.registered = registered;
        this.price = price;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Class{" + // hoặc "Course{" nếu đổi tên file
                "ccode='" + ccode + '\'' +
                ", scode='" + scode + '\'' +
                ", sname='" + sname + '\'' +
                ", semester='" + semester + '\'' +
                ", year='" + year + '\'' +
                ", seats=" + seats +
                ", registered=" + registered +
                ", price=" + price +
                '}';
    }
}
