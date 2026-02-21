package iteradorConInnerClass;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleStack<T> implements Iterable<T> {

    // Static nested class: independiente de la instancia de SimpleStack
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> top; // Referencia al nodo superior
    private int size;    // Contador de elementos

    public void push(T item) {
        // Creamos un nuevo nodo que apunta al antiguo top
        top = new Node<>(item, top);
        size++;
    }

    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack vacio");
        
        // Guardamos el dato para devolverlo
        T data = top.data;
        // El nuevo top es el siguiente nodo en la cadena
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack vacio");
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    // Inner class: necesita acceso al campo 'top' de la clase externa
    private class StackIterator implements Iterator<T> {
        private Node<T> current = top; // Empezamos el recorrido desde arriba

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            // Guardamos el dato actual, avanzamos el puntero y retornamos el dato
            T data = current.data;
            current = current.next;
            return data;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack[");
        Node<T> current = top;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(" -> ");
            current = current.next;
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        SimpleStack<Integer> stack = new SimpleStack<>();

        System.out.println("=== Stack de Enteros ===");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Despues de push 10, 20, 30: " + stack);
        System.out.println("Peek: " + stack.peek());
        System.out.println("Size: " + stack.size());

        System.out.print("Iterando (LIFO): ");
        for (Integer n : stack) {
            System.out.print(n + " ");
        }
        System.out.println();

        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Peek despues de pops: " + stack.peek());
        System.out.println("Size: " + stack.size());

        System.out.println("\n=== Stack de Strings ===");
        SimpleStack<String> palabras = new SimpleStack<>();
        palabras.push("Hola");
        palabras.push("Mundo");
        palabras.push("Java");

        System.out.print("For-each: ");
        for (String s : palabras) {
            System.out.print(s + " ");
        }
        System.out.println();

        System.out.println("\n=== Error: pop en stack vacio ===");
        SimpleStack<Integer> vacio = new SimpleStack<>();
        try {
            vacio.pop();
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}