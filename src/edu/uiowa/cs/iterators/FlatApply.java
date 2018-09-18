package edu.uiowa.cs.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Iterator that may produce 0 or more output elements for every input element
public class FlatApply<InT, OutT> implements Iterator<OutT> {

    private FlatApplyFunction<InT, OutT> f;
    private Iterator<InT> input;
    private Iterator<OutT> queue;

    public FlatApply(FlatApplyFunction<InT, OutT> f, Iterator<InT> input) {
        this.f = f;
        this.input = input;
        queue = new LinkedList<OutT>().iterator();
        fillQueue();
    }
    @Override
    public boolean hasNext() {
        return (queue.hasNext() || (!queue.hasNext() && (input.hasNext())));
    }
    @Override
    public OutT next() {
        if (queue.hasNext() ) {
            OutT out = queue.next();
            fillQueue();     
            return out;
        } else {
            return queue.next();
        }
    }
    private void fillQueue() {
        while (!queue.hasNext() && input.hasNext()) {
            queue = f.apply(input.next()).iterator();
        }
    }
    // feel free to create private methods if helpful
}