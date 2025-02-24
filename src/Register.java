import java.util.Date;
public class Register {
    String ccode;
    String scode;
    Date bdate;
    double mark;
    int state; // 1 or 0

    public Register(String ccode, String scode, Date bdate, double mark, int state) {
        this.ccode = ccode;
        this.scode = scode;
        this.bdate = bdate;
        this.mark = mark;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Register{" + "ccode=" + ccode + ", scode=" + scode + ", bdate=" + bdate + ", mark=" + mark + ", state=" + state + '}';
    }
    public String getCcode() {
        return ccode;
    }

    public String getScode() {
        return scode;
    }

    public Date getBdate() {
        return bdate;
    }

    public double getMark() {
        return mark;
    }

    public int getState() {
        return state;
    }
    public void setMark(double mark) {
        this.mark = mark;
    }
    public void setState(int state) {
        this.state = state;
    }

}
