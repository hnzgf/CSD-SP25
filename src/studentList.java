
public class studentList {
  studentNode head, tail;
    public studentList() {
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
    
void studentAddLast(studentNode x){
    if(isEmpty()){
        head = x;
        return;
    }
    tail.next = x;
    x = tail;
}
    

void studentDisplay(){
    if(isEmpty()){
        System.out.println("there's no data lately");
        return;
    }
    studentNode p = head;
    while(p != null){
        System.out.println(p.info);
    }
}

void studentSearchByScode(String scode){
    if(head == null){
        System.out.println("list is empty");
        return;
    }
    studentNode p = head;
    while(!p.info.scode.equals(scode) && p != null){
        p = p.next;
    }
    System.out.println(p.info);
}
    
void studentDeleteByScode(String scode){
    
    // if empty
    if(head == null){
        System.out.println("list is empty");
        return;
    }
    
    //if head
    if(head.info.scode.equals(scode)){
        head = head.next;
        if(head == null){
            tail = null;
        }
        return;
    }
    
    //if scode not available
    if(!studentCheckScode(scode)){
        System.out.println("there's no this scode");
        return;
    }
    
    //not head + find node right before the delete node
    studentNode p = head;
    while(p.next != null){
        if(p.next.info.scode.equals(scode)){
            
            //if delete tail
            if(p.next == tail){
                p = tail;
                p.next = null;
            }
            
            //not tail
            p.next.info = null;
            p.next = p.next.next;
            return;
        }
        p = p.next;
    }
    
}

boolean studentCheckScode(String scode){
    studentNode p = head;
    while(p != null){
        if(p.info.scode.equals(scode)){
            return true;
        }
        p = p.next;
    }
    return false;
}

boolean studentCheckName(String name){
    studentNode p = head;
    while(p != null){
        if(p.info.name.equals(name)){
            return true;
        }
        p = p.next;
    }
    return false;
}


void studentSearchByName(String name){
    
    // if empty
    if(head == null){
        System.out.println("list is empty");
        return;
    }
    
    //if head
    if(head.info.name.equals(name)){
        System.out.println(head.info.name);
        return;
    }
    
    //if name not available
    if(!studentCheckName(name)){
        System.out.println("there's no this name");
        return;
    }
    
    studentNode p = head;
    while(p != null){
        if(p.info.name.equals(name)){
            System.out.println(p.info.name);
            return;
        }
        p = p.next;
    }
}

//void registeredCourseByScode(String scode){
//    classList rl = new classList();
//    rl.searchCourseByStudentScode(scode);
//}
//
//    //for student list purpose
//void searchCourseByStudentScode(String scode){
//    boolean course = false;
//    studentList sl = new studentList();
//    if(isEmpty()){
//        System.out.println("no list lately");
//        return;
//    }
//    
//    //if head
//    if(head.info.scode.equals(scode)){
//        System.out.println(head.info);
//        return;
//    }
//    
//    //if scode available
//    if(!sl.studentCheckScode(scode)){
//        System.out.println("there's no this scode");
//        return;
//    }
//    
//    classNode p = head;
//    while(p != null){
//        
//        //find course
//        if(p.info.scode.equals(scode)){
//            System.out.println(p.info);
//            course = true;
//        }
//        
//        
//        
//        p = p.next;
//    }
//    if(course){
//        return;
//    }
//    else{
//        System.out.println("no course found");
//        return;
//    }
//}










}
