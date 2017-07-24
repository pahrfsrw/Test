package utils;

public class Stopwatch {
	
	private static int instances = 0;
	private long startTime;
	private long stopTime;
	private String name;
	
	private StopwatchUnits timeUnit;
	
	public Stopwatch(){
		Stopwatch.instances++;
		this.timeUnit = StopwatchUnits.MILLISECONDS;
		this.name = "Stopwatch_" + Integer.toString(Stopwatch.instances);
	}
	
	public Stopwatch(String stopwatchName){
		Stopwatch.instances++;
		this.timeUnit = StopwatchUnits.MILLISECONDS;
		this.name = stopwatchName;
	}
	
	public Stopwatch(StopwatchUnits timeUnit){
		Stopwatch.instances++;
		this.timeUnit = timeUnit;
		this.name = "Stopwatch" + Integer.toString(Stopwatch.instances);
	}
	
	public Stopwatch(String stopwatchName, StopwatchUnits timeUnit){
		Stopwatch.instances++;
		this.timeUnit = timeUnit;
		this.name = stopwatchName;
	}
	
	public void start(){
		this.startTime = System.nanoTime();
	}
	
	public long stop(){
		this.stopTime = System.nanoTime();
		return this.stopTime-this.startTime;
	}
	
	public long startTime(){
		return this.startTime;
	}
	
	public long elapsedTime(){
		return this.convertToUnit(this.timeUnit);
	}
	
	public void printElapsedTime(){
		this.printElapsedTime(this.timeUnit);
	}
	
	public long elapsedTime(StopwatchUnits timeUnit){
		return this.convertToUnit(timeUnit);
	}
	
	public void printElapsedTime(StopwatchUnits timeUnit){
		System.out.println(this.name + " time: " + Long.toString(this.elapsedTime(timeUnit)));
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	private long convertToUnit(StopwatchUnits timeUnit){
		switch(timeUnit){
			case MICROSECONDS:
				return (long) Math.round((double)this.getNanoseconds()/1000.0);
			case MILLISECONDS:
				return (long) Math.round((double)this.getNanoseconds()/1000000.0);
			case SECONDS:
				return (long) Math.round((double)this.getNanoseconds()/1000000000.0);
			case MINUTES:
				return (long) Math.round((double)this.getNanoseconds()/(1000000000.0*60.0));
			case HOURS:
				return (long) Math.round((double)this.getNanoseconds()/(1000000000.0*60.0*60.0));
			default:
				return getNanoseconds();
		}
	}
	
	private long getNanoseconds(){
		this.stopTime = System.nanoTime();
		return this.stopTime-this.startTime();
	}
	
	public static void main(String[] args){
		Stopwatch s = new Stopwatch();
		s.start();
		try{
			Thread.sleep(2000);
		} catch(InterruptedException e){
			System.out.println("Interrupted.");
		}
		s.stop();
		System.out.println(s.elapsedTime());
	}
}
