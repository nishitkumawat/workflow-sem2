package utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import models.Employee;

public class EmployeeList implements Iterable<Employee>{
    class Node{
        Employee employee;
        Node next;
        Node(Employee employee){
            this.employee = employee;
            this.next = null;
        }
    }
    private Node head;
    private int size;
    public EmployeeList(){
        this.head = null;
        this.size = 0;
    }

    public void addFirst(Employee employee){
        Node node = new Node(employee);
        if(head==null){
            head = node;
        }else{
            node.next = head;
            head = node;
        }
        size++;
    }
    public Employee removeLast(){
        if(head==null){
            System.out.println("No Element In List");
            return null;
        }else{
            Node pointer = head;
            while (pointer.next!=null) {
                pointer = pointer.next;
            }
            Node deletedNode = pointer.next;
            pointer.next = null;
            size--;
            return deletedNode.employee;
        }
    }
    public int size(){
        return size;
    }

    @Override
    public Iterator<Employee> iterator() {
        return new EmployeeIterator();
    }

    private class EmployeeIterator implements Iterator<Employee> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Employee next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Employee employee = current.employee;
            current = current.next;
            return employee;
        }
    }
}
