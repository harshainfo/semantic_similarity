package edu.uiowa.cs.iterators;

// Interface for classes to define a function from InT to OutT
public interface ApplyFunction<InT, OutT> {
		public OutT apply(InT x);
}
