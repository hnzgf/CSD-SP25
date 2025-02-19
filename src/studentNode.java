
public class studentNode {
     Student info;
    studentNode next;

     studentNode() {
    }

     studentNode(Student x, studentNode p) {
        info = x;
        next = p;
    }

     studentNode(Student x) {
        this(x, null);
    }
    
}
