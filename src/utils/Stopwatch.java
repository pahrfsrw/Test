package utils;

import java.util.ArrayList;

public class Stopwatch implements Runnable{
	
	private static ArrayList<Stopwatch> stopwatches = new ArrayList<Stopwatch>();
	private ArrayList<Long> laps = new ArrayList<Long>();
	private long intermediateTime;
	private String name;
	
	private boolean autoPrintRunning = false;
	private long autoPrintInterval = 0;
	private Thread autoPrintThread = null;
	private boolean suppressAutoPrintStopMessages = false;
	
	private final double SECONDS_MULT = 0.001; 
	private final Convert SECONDS = new Convert(this, SECONDS_MULT, "s"); 
	private final double MINUTES_MULT = 0.001/60;
	private final Convert MINUTES = new Convert(this, MINUTES_MULT, "m");
	private final double HOURS_MULT = 0.001/3600;
	private final Convert HOURS = new Convert(this, HOURS_MULT , "h");
	
	public Stopwatch(){
		stopwatches.add(this);
		this.name = "Stopwatch_" + Integer.toString(stopwatches.size());
	}
	
	public Stopwatch(String stopwatchName){
		stopwatches.add(this);
		this.name = stopwatchName;
	}
	
	public void start(){
		this.laps.add(System.nanoTime());
	}
	
	public long lap(){
		long temp = System.nanoTime();
		this.laps.add(temp);
		return temp-this.laps.get(this.laps.size()-2);
	}
	
	public long circuit(){
		long temp = System.nanoTime();
		this.laps.add(temp);
		return temp-this.laps.get(0);
	}
	
	public long lapAverage(){
		int size = this.laps.size();
		long temp = this.laps.get(size-1)-this.laps.get(0);
		long average = (long)Math.round((double)temp/(double)(size-1));
		return convertToMillis(average);
	}
	
	public long elapsedTime(){
		return this.convertToMillis(this.getNanoseconds());
	}
	
	public void printElapsedTime(){
		System.out.println(this.name + " time (ms): " + Long.toString(this.elapsedTime()));
	}
	
	public void autoPrint(long interval){
		if(this.autoPrintRunning){
			return;
		}
		this.autoPrintRunning = true;
		this.autoPrintInterval = interval;
		this.autoPrintThread = new Thread(this, "Stopwatch thread: " + this.name);
		this.autoPrintThread.start();
	}
	
	public void stopAutoPrint(boolean suppressStopMessages){
		this.suppressAutoPrintStopMessages = suppressStopMessages;
		this.autoPrintRunning = false;
		this.autoPrintThread.interrupt();
	}
	
	public void stopAllAutoPrints(boolean suppressStopMessages){
		Stopwatch sw;
		for(int i = 0; i < stopwatches.size(); i++){
			sw = stopwatches.get(i);
			if(sw != null)
				sw.stopAutoPrint(suppressStopMessages);
		}
	}
	
	public void run(){
		while(this.autoPrintRunning){
			System.out.println(this.name + ", time (ms): " + elapsedTime());
			try{
				Thread.sleep(this.autoPrintInterval);
			} catch (InterruptedException e){
				if(!this.suppressAutoPrintStopMessages){
					System.out.println("Auto-print of stopwatch " + this.name + " interrupted.");
				}
			}
		}
		if(!this.suppressAutoPrintStopMessages){
			System.out.println("Auto-print of stopwatch " + this.name + " has been stopped.");
		}
	}
	
	private long convertToMillis(long nanoseconds){
		return (long) Math.round((double)nanoseconds/1000000.0);
	}
	
	private long getNanoseconds(){
		this.intermediateTime = System.nanoTime();
		return this.intermediateTime-this.laps.get(0);
	}
	
	public long getStartTime(){
		return this.laps.get(0);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static void main(String[] args){
		Stopwatch s = new Stopwatch();
		s.start();
		s.HOURS.autoPrint(1000);
		try{
			Thread.sleep(5000);
		} catch(InterruptedException e){
			System.out.println("interrupted...");
		}
		s.stopAutoPrint(true);
	}
	
	private static class Convert implements Runnable{
		private final double MULT;
		private final String NAME;
		private Stopwatch sw;
		private Convert(Stopwatch sw, double mult, String name){
			this.sw = sw;
			this.MULT = mult;
			this.NAME = name;
		}
		@SuppressWarnings("unused")
		public double getStartTime(){
			return (double)sw.getStartTime()*MULT;
		}
		@SuppressWarnings("unused")
		public double circuit(){
			return (double)sw.circuit()*MULT;
		}
		@SuppressWarnings("unused")
		public double lap(){
			return (double)sw.lap()*MULT;
		}
		@SuppressWarnings("unused")
		public double lapAverage(){
			return (double)sw.lapAverage()*MULT;
		}
		public double elapsedTime(){
			return (double)sw.elapsedTime()*MULT;
		}
		@SuppressWarnings("unused")
		public void printElapsedTime(){
			System.out.println(sw.name + " time (" + this.NAME + "): " + Double.toString(this.elapsedTime()));
		}
		@SuppressWarnings("unused")
		public void autoPrint(long interval){
			if(sw.autoPrintRunning){
				return;
			}
			sw.autoPrintRunning = true;
			sw.autoPrintInterval = interval;
			sw.autoPrintThread = new Thread(this, "Stopwatch thread: " + sw.name);
			sw.autoPrintThread.start();
		}
		public void run(){
			while(sw.autoPrintRunning){
				System.out.println(sw.name + ", time (" + this.NAME + "): " + this.elapsedTime());
				try{
					Thread.sleep(sw.autoPrintInterval);
				} catch (InterruptedException e){
					if(!sw.suppressAutoPrintStopMessages){
						System.out.println("Auto-print of stopwatch " + sw.name + " interrupted.");
					}
				}
			}
			if(!sw.suppressAutoPrintStopMessages){
				System.out.println("Auto-print of stopwatch " + sw.name + " has been stopped.");
			}
		}
	}
	

}
