package edu.uiowa.cs.iterators;

import java.util.Iterator;

// Iterator that returns a single element that is the result of
// combining all the input elements
public class Reduce<InT, OutT> implements Iterator<OutT> {

    private final ReduceFunction<InT, OutT> f;

    // The Iterator that this Apply object will get its input from
    private final Iterator<InT> input;
    private OutT soFar;
    private InT cursor;
    private boolean finished;

    public Reduce(ReduceFunction<InT, OutT> f, Iterator<InT> input) {
        this.f = f;
        this.input = input;
        this.soFar = f.initialValue(cursor);
        finished = false;

    }
    @Override
    public boolean hasNext() {
        return !finished;
    }
    @Override
    public OutT next() {
        while (input.hasNext()) {
            cursor = input.next();
            soFar = f.combine(soFar, cursor);
        }
        finished = true;
        return soFar;
    }
}
