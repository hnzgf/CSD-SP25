
public class Register {
    String ccode;
    String scode;
    String bdate;
    double mark;
    int state; // 1 or 0

    public Register(String ccode, String scode, String bdate, double mark, int state) {
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
    
    
}
