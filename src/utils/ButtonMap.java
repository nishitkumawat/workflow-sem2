package utils;

import javax.swing.JButton;

public class ButtonMap {
    class Node{
        int key;
        JButton button;
        Node next;
        Node prev;
        Node(int key,JButton button){
            this.key = key;
            this.button = button;
            this.next = this.prev = null;
        }
    }
    private Node head;
    private int size;
    public ButtonMap(){
        head = null;
        size = 0;
    }
    
    public void put(int key,JButton button){
        Node node = new Node(key, button);
        if(head==null){
            head = node;
        }else{
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public JButton get(int key){
        if(head==null){
            return null;
        }else{
            System.out.println("Searching For key : "+key);
            Node pointer = head;
            while (pointer!=null) {
                System.out.println("On Key : "+pointer.key);
                if(pointer.key==key){
                    break;
                }
                pointer = pointer.next;
            }
            return pointer.button;
        }
    }

    public void clear(){
        head = null;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }
}
