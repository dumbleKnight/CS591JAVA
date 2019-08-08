package backend;
//import java.time.Instant;

public class Investment {	
	protected String id;
	String type;
	Investment(String ID, String type ) {
		id  = ID;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}
	
	/*static public void main(String[] args) {
		Instant t = Instant.now();
		Instant t1 = t.plus(Duration.ofDays(7));
		System.out.println(Duration.between(t, t1).getSeconds());
		System.out.println(Duration.between(t1, t).getSeconds());
	}*/
}
