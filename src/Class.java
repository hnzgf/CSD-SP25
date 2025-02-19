
public class Class{
    
    String ccode;
    String scode;
    String sname;
    String semester;
    String year;
    int seats;
    int registered;
    double price;


    public Class(String ccode, String scode, String sname, String semester, String year, int seats, int registered, double price) {
        this.ccode = ccode;
        this.scode = scode;
        this.sname = sname;
        this.semester = semester;
        this.year = year;
        this.seats = seats;
        this.registered = registered;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Class{" + "ccode=" + ccode + ", scode=" + scode + ", sname=" + sname + ", semester=" + semester + ", year=" + year + ", seats=" + seats + ", registered=" + registered + ", price=" + price + '}';
    }
    //duhasoidhasdhwaoi
    
    
    
    
}
