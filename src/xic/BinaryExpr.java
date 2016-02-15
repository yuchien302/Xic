package xic;

public class BinaryExpr extends Expr {
	
	public Expr expl;
	public Expr expr;
	public String op;
	
	public BinaryExpr(String type, Object el, Object er){
		expl = (Expr) el;
		expr = (Expr) er;
		op = type;
	}
	@Override
	public String toString(){
		return String.format("(%s %s %s)", op, expl, expr);
	}
}
