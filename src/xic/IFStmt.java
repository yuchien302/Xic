package xic;

public class IFStmt extends Expr {
	public Expr predicate;
	public Expr consequent;
	public Expr alternative;
	
	public IFStmt(Object pred, Object cons, Object alter){
		predicate = (Expr) pred;
		consequent = (Expr) cons;
		alternative = (Expr) alter;
	}
	
	public IFStmt(Object pred, Object cons){
		predicate = (Expr) pred;
		consequent = (Expr) cons;
	}
	@Override
	public String toString(){
		return String.format("(if %s (%s) (%s))", predicate, consequent, alternative);
	}
}
