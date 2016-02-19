package xic;

public class AtomSExp extends SExp {
	public String value;
	
	public AtomSExp(String v){
		value = v;
	}
	public AtomSExp(int v){
		value = Integer.toString(v);
	}
	public AtomSExp(SExp v){
		value = v.toString();
	}
	
	@Override
	public String toString(){
		return value;
	}
}
