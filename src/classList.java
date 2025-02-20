// write your name or else that code belongs to hnzgf

/*1.1. 
Load data from file 
1.2. X
Input & add to the end  
1.3. X
Display data 
1.4. 
Save course list to file 
1.5. X
Search by ccode 
1.6. X
Delete by ccode  
1.7. 
Sort by ccode 
(display courses in ascending order of the ccode) 
1.8. 
Input & add to beginning 
(input and validate course data, then add the course to the begin of the list) 
1.9. 
Add after position  k 
Delete position k 
(input and validate course data, then add the course to the position k+1 of the list) 
1.10. 
(delete the course to the position k of the list) 
1.11.  
Search course by name 
(input name to be searched, then return  the found courses data or not found) 
1.12. 
Search course by ccode 
(input ccode to be searched, then return  the found course data or not found; 
Then list all students registered the course) */

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

void classSortByCcode(){
    if(isEmpty()){
        System.out.println("no course lately");
    }
    classNode q = head;
    while(q != null){
        classNode p = head;
        int charAt = 0;
        while(p.next != null){
            while(p.info.ccode.charAt(charAt) != '\n' && p.next.info.ccode.charAt(charAt) != '\n' && p.next != null){
                if(p.info.ccode.charAt(charAt)<p.next.info.ccode.charAt(charAt)){
                classSwap(p,p.next);
                break;
                }
                charAt++;
            }
        p = p.next;
        }
        q = q.next;
    }
}

void classSwap(classNode x, classNode y){
    classNode temp = head;
    temp.info = x.info;
    x.info = y.info;
    y.info = temp.info;
}

int classLength(){
    int count = 0;
    classNode p = head;
    while(p != null){
        p = p.next;
        count++;
    }
    return count;
}

void classAddFirst(classNode x){
    if(isEmpty()){
        head = x;
        tail = x;
        return;
    }
    x.next = head;
    head = x;
}

void classAddAfter(int k, classNode x){
    if(k > classLength()){
        System.out.println(" position not available");
        return;
    }
    int count = 0;
    if(isEmpty()){
        head = x;
        tail = x;
        return;
    }
    classNode p = head;
    while(p != null){
        if(count == k){
            break;
        }
        p = p.next;
        count++;
    }
    if(p == tail){
        p.next = x;
        tail = x;
    }
    x.next = p.next;
    p.next = x;
    
}

void classDeletePosition(int k){
    if(k > classLength()){
        System.out.println(" position not available");
        return;
    }
    int count = 0;
    classNode p = head;
    if(isEmpty()){
        System.out.println("there's no course lately");
        return;
    }
    while(count+1 != k){
        p = p.next;
        count++;
    }
    p.next = p.next.next;
}

void classSearchByName(String name){
    classNode p = head;
    boolean printed = false;
    if(isEmpty()){
        System.out.println("no course");
        return;
    }
    
    while(p != null){
        //if course(s) found 
        if(p.info.sname.equals(name)){
            System.out.println(p.info);
            printed = true;
        }
        p = p.next;
    }
    //searched all list and no name fit
    if(!printed){
        System.out.println("name not available");
    }
    else{
        return;
    }
}





}
