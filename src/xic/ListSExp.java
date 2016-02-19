package xic;

import java.util.ArrayList;

public class ListSExp extends SExp {
	public ArrayList<SExp> sexps;
	public ListSExp(){
		sexps = new ArrayList<SExp>();
	}
	public ListSExp(Object ... exps){
		sexps = new ArrayList<SExp>();
		for(Object exp: exps) {
			if(exp == null) {
				continue;
			} else if(exp instanceof String) {
				sexps.add(new AtomSExp((String) exp));
			} else {
				sexps.add((SExp) exp);
			}
			
		}
	}
	public Boolean isEmpty(){
		return sexps.isEmpty();
	}
	public int size(){
		return sexps.size();
	}	
//	public ListSExp(){
//		sexps = new ArrayList<SExp>();
//	}
//	
//	public ListSExp(SExp exp1){
//		sexps = new ArrayList<SExp>();
//		sexps.add(exp1);
//	}
//
//	public ListSExp(String op, SExp exp1){
//		sexps = new ArrayList<SExp>();
//		sexps.add(new AtomSExp(op));
//		sexps.add(exp1);
//	}	
//	
//	public ListSExp(SExp exp1, SExp exp2){
//		sexps = new ArrayList<SExp>();
//		sexps.add(exp1);
//		sexps.add(exp2);
//	}
//	
//
//	
//	public ListSExp(String op, SExp exp1, SExp exp2){
//		sexps = new ArrayList<SExp>();
//		sexps.add(new AtomSExp(op));
//		sexps.add(exp1);
//		sexps.add(exp2);
//	}
//	
//	public ListSExp(SExp exp1, SExp exp2, SExp exp3){
//		sexps = new ArrayList<SExp>();
//		sexps.add(exp1);
//		sexps.add(exp2);
//		sexps.add(exp3);
//	}
//	public ListSExp(String op, SExp exp1, SExp exp2, SExp exp3){
//		sexps = new ArrayList<SExp>();
//		sexps.add(new AtomSExp(op));
//		sexps.add(exp1);
//		sexps.add(exp2);
//		sexps.add(exp3);
//	}
//	public ListSExp(SExp exp1, SExp exp2, SExp exp3, SExp exp4){
//		sexps = new ArrayList<SExp>();
//		sexps.add(exp1);
//		sexps.add(exp2);
//		sexps.add(exp3);
//		sexps.add(exp4);
//	}	
	public void add(SExp exp){
		sexps.add(exp);
	}
	public void prepend(SExp exp){
		sexps.add(0, exp);
	}
	
	@Override
	public String toString(){

		ArrayList<String> strings = new ArrayList<String>();
		for (SExp sexp : sexps) {
		    strings.add(sexp.toString());
		}
		
		return String.format("(%s)", String.join(" ", strings));
	}
}
