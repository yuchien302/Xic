package xic;

import java.util.ArrayList;

public class ListSExp extends SExp {
	public ArrayList<SExp> sexps;
	
	public ListSExp(){
		sexps = new ArrayList<SExp>();
	}
	
	public ListSExp(SExp exp1){
		sexps = new ArrayList<SExp>();
		sexps.add(exp1);
	}

	public ListSExp(String op, SExp exp1){
		sexps = new ArrayList<SExp>();
		sexps.add(new AtomSExp(op));
		sexps.add(exp1);
	}	
	
	public ListSExp(SExp exp1, SExp exp2){
		sexps = new ArrayList<SExp>();
		sexps.add(exp1);
		sexps.add(exp2);
	}
	

	
	public ListSExp(String op, SExp exp1, SExp exp2){
		sexps = new ArrayList<SExp>();
		sexps.add(new AtomSExp(op));
		sexps.add(exp1);
		sexps.add(exp2);
	}
	
	public ListSExp(SExp exp1, SExp exp2, SExp exp3){
		sexps = new ArrayList<SExp>();
		sexps.add(exp1);
		sexps.add(exp2);
		sexps.add(exp3);
	}
	public ListSExp(String op, SExp exp1, SExp exp2, SExp exp3){
		sexps = new ArrayList<SExp>();
		sexps.add(new AtomSExp(op));
		sexps.add(exp1);
		sexps.add(exp2);
		sexps.add(exp3);
	}	
	public void add(SExp exp){
		sexps.add(exp);
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
