package hello.aop;

import org.junit.jupiter.api.Test;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class Tests {

    public static class Stack<E> {
        public void pushAll(Iterable<? extends E> src) {
            for (E e : src) {
                push(e);
            }
        }
        public void push(E e) {
        }

        public E pop() {
            E e = null;
            return e;
        }

        public void popAll(Collection<? super E> dst) {
            while (!dst.isEmpty()) {
                dst.add(pop()); // compile error
            }
        }
    }
}
