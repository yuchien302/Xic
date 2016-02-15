package xic;

public class IDAtom extends Expr {
	public String id;
	
	public IDAtom(String identifier){
		id = identifier;
	}
	
	@Override
	public String toString(){
		return id;
	}
}
