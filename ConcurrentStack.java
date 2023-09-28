package chapter_22;

import java.util.EmptyStackException;

public class ConcurrentStack<T> {

    private Node<T> top;

    public synchronized void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
    }

    public synchronized T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        return data;
    }

    public synchronized boolean isEmpty() {
        return top == null;
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        ConcurrentStack<Integer> stack = new ConcurrentStack<>();

        Thread pushThread1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                stack.push(i);
                System.out.println("Pushed: " + i);
            }
        });

        Thread pushThread2 = new Thread(() -> {
            for (int i = 6; i <= 10; i++) {
                stack.push(i);
                System.out.println("Pushed: " + i);
            }
        });

        Thread popThread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int popped = -1;
                if(!stack.isEmpty()){
                    popped = stack.pop();
                }
                System.out.println("Popped by Thread 1: " + popped);
            }
        });

        Thread popThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int popped = -1;
                if(!stack.isEmpty()){
                    popped = stack.pop();
                }
                System.out.println("Popped by Thread 2: " + popped);
            }
        });

        pushThread1.start();
        pushThread2.start();
        popThread1.start();
        popThread2.start();
        
    }
    
}
