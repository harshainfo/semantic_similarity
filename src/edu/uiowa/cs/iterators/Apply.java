package edu.uiowa.cs.iterators;


import java.util.Iterator;

public class Apply<InT,OutT> implements Iterator<OutT> {
        // The function that will be applied to each input element to make an output element
		private final ApplyFunction<InT,OutT> f;

        // The Iterator that this Apply object will get its input from
		private final Iterator<InT> input;		

		public Apply(ApplyFunction<InT, OutT> f, Iterator<InT> input) {
                    this.f = f;
                    this.input = input;
		}

		@Override
		public boolean hasNext() {
                    return this.input.hasNext();
		}

		@Override
		public OutT next() {
                    return this.f.apply(this.input.next());
		}
}
