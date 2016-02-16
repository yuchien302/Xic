package xic;

public class AtomSExp extends SExp {
	public String value;
	
	public AtomSExp(String v){
		value = v;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
