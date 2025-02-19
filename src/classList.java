
public class classList {
    classNode head, tail;
    public classList() {
        head = null;
        tail = null;
    }
    
    boolean isEmpty(){
        if( head == null){
        return true;
        }
        else{
            return false;
        }
    }
    
    void clear(){
        head = tail = null;
}

void searchCourseByStudentScode(String scode){
    boolean course = false;
    studentList sl = new studentList();
    if(isEmpty()){
        System.out.println("no list lately");
        return;
    }
    
    //if head
    if(head.info.scode.equals(scode)){
        System.out.println(head.info);
        return;
    }
    
    //if scode available
    if(!sl.studentCheckScode(scode)){
        System.out.println("there's no this scode");
        return;
    }
    
    classNode p = head;
    while(p != null){
        
        //find course
        if(p.info.scode.equals(scode)){
            System.out.println(p.info);
            course = true;
        }
        
        
        
        p = p.next;
    }
    if(course){
        return;
    }
    else{
        System.out.println("no course found");
        return;
    }
    
    
}


void classAddLast(classNode x){
    if(isEmpty()){
        head = x;
        tail = x;
        return;
    }
    
    tail.next = x;
    tail = x;
}

void classDisplay(){
    if(isEmpty()){
        System.out.println("no data");
        return;
    }
    classNode p = head;
    while(p != null){
        System.out.println(p.info);
        p = p.next;
    }
}

void classSearchByCcode(String ccode){
    if(isEmpty()){
        System.out.println("no course lately");
        return;
    }
    
    classNode p = head;
    while(p != null){
        if(p.info.ccode.equals(ccode)){
            System.out.println(p.info);
            return;
        }
        p = p.next;
    }
    System.out.println("ccode not available");
    
}

void classDeleteByCcode(String ccode){
    boolean delete = false;
    //empty
    if(isEmpty()){
        System.out.println("no course lately");
        return;
    }
    
    //if head is
    if(head.info.ccode.equals(ccode)){
        head = head.next;
    }
    
    classNode p = head;
    //find parent of delete node
    while(p.next != null){
        
        //found node
        if(p.next.info.ccode.equals(ccode)){
            delete = true;
            //if tail
            if(p.next == tail){
                p = tail;
                p.next = null;
                return;
            }
            
            p.next = p.next.next;
            return;
        }
        
        p = p.next;
    }
    //if no course deleted
    if(!delete){
        System.out.println("no ccode available");
    }
}







}
