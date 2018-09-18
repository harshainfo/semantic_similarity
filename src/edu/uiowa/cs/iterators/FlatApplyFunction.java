package edu.uiowa.cs.iterators;


import java.util.List;

// Interface for classes to define a function from InT to List<OutT>
public interface FlatApplyFunction<InT, OutT> {
		public List<OutT> apply(InT x);
}
