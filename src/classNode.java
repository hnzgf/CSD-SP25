
public class classNode {
    Class info;
    classNode next;

     classNode() {
    }

     classNode(Class x, classNode p) {
        info = x;
        next = p;
    }

     classNode(Class x) {
        this(x, null);
    }
}
