
public class registerNode {
    
    Register info;
    registerNode next;

     registerNode() {
    }

     registerNode(Register x, registerNode p) {
        info = x;
        next = p;
    }

     registerNode(Register x) {
        this(x, null);
    }


}
