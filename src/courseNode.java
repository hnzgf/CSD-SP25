public class courseNode { // hoặc courseNode.java nếu đổi tên Class.java thành Course.java
    Course info; // hoặc Course info
    courseNode next; // hoặc courseNode next

    public courseNode() {
    }

    public courseNode(Course info) { // hoặc Course info
        this.info = info;
        this.next = null;
    }
}