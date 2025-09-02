package utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MessageQueue implements Iterable<String>{
    public class Node {
        String message;
        Node next;
        Node(String b){
            this.message = b;
            this.next = null;
        }
    }
    private Node front;
    private Node rear;
    private int size;
    public MessageQueue(){
        this.front = this.rear = null;
        this.size = 0;
    }
    public void enqueue(String message){
        Node node = new Node(message);
        if(front==null){
            front = rear = node;
            size++;
            return;
        }else{
            Node pointer = front;
            while (pointer!=rear) {
                pointer = pointer.next;
            }
            pointer.next = node;
            rear = node;
            size++;

        }
    }
    public String dequeue(){
        if(front==null){
            System.out.println("Queue is Empty");
            return null;
        }else if(size==1){
            Node deletedNode = front;
            front = rear = null;
            size--;
            return deletedNode.message;
        }else{
            Node deletedNode = front;
            front = front.next;
            size--;
            return deletedNode.message;
        }
    }
    public int size(){
        return size;
    }

    @Override
    public Iterator<String> iterator() {
        return new StringIterator();
    }

    private class StringIterator implements Iterator<String> {
        private Node current = front;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String String = current.message;
            current = current.next;
            return String;
        }
    }

}
