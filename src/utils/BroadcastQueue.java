package utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import models.Broadcast;

public class BroadcastQueue implements Iterable<Broadcast>{
    public class Node {
        Broadcast broadcast;
        Node next;
        Node(Broadcast b){
            this.broadcast = b;
            this.next = null;
        }
    }
    private Node front;
    private Node rear;
    private int size;
    public BroadcastQueue(){
        this.front = this.rear = null;
        this.size = 0;
    }
    public void enqueue(Broadcast broadcast){
        Node node = new Node(broadcast);
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
    public Broadcast dequeue(){
        if(front==null){
            System.out.println("Queue is Empty");
            return null;
        }else if(size==1){
            Node deletedNode = front;
            front = rear = null;
            size--;
            return deletedNode.broadcast;
        }else{
            Node deletedNode = front;
            front = front.next;
            size--;
            return deletedNode.broadcast;
        }
    }
    public int size(){
        return size;
    }

    @Override
    public Iterator<Broadcast> iterator() {
        return new BroadcastIterator();
    }

    private class BroadcastIterator implements Iterator<Broadcast> {
        private Node current = front;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Broadcast next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Broadcast broadcast = current.broadcast;
            current = current.next;
            return broadcast;
        }
    }
}
