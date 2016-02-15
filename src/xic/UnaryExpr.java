package xic;

public class UnaryExpr extends Expr {
	public Expr exp;
	public String op;
	
	public UnaryExpr(String type, Object e){
		exp = (Expr) e;
		op = type;
	}
	@Override
	public String toString(){
		return String.format("(%s %s)", op, exp);
	}
}
