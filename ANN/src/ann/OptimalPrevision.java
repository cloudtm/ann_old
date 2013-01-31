package ann;

public class OptimalPrevision {
	private double serverThroughput;
	private double serverResponse;
	private double replThroughput;
	private double replResponse;
	public OptimalPrevision(){
		this.serverThroughput=-1;
		this.serverResponse=-1;
		this.replThroughput = -1;
		this.replResponse = -1;
	}
	public OptimalPrevision(double a, double b, double c, double d){
		this.serverThroughput= a;
		this.serverResponse= b;
		this.replThroughput = c;
		this.replResponse = d;
	}
	public void setServerThroughput(double a){
		this.serverThroughput = a;
	}
	public void setServerResponseTime(double a){
		this.serverResponse = a;
	}
	public void setReplicationResponseTime(double a){
		this.replResponse = a;
	}
	public void setReplicationThroughput(double a){
		this.replThroughput = a;
	}
	public double getServerThroughput(){
		return this.serverThroughput;
	}
	public double getServerResponseTime(){
		return this.serverResponse;
	}
	public double getReplicationResponseTime(){
		return this.replResponse;
	}
	public double getReplicationThroughput(){
		return this.replThroughput;
	}
	public boolean equals(Object o){
		if(o.getClass().equals(this)){
			OptimalPrevision op = (OptimalPrevision) o;
			return (this.replResponse == op.replResponse && this.replThroughput == op.replThroughput && 
					this.serverResponse == op.serverResponse && this.serverThroughput == this.serverThroughput);
		}else{
			return false;
		}
	}
}
