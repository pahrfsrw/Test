package main;

import evolution.TournamentManager;
import generation.Generation;
import generation.GenerationFactory;
import generation.Individual;
import sim.GenerationSimulator;
import utils.Stopwatch;

public class Main {

	public static void main(String[] args){
		System.out.println("Starting Main.java");
		Generation newGen = null;
		GenerationSimulator genSim;
		int numberOfGenerations = 500;
		Stopwatch s = new Stopwatch();
		s.autoPrint(5000);
		s.start();
		for(int i = 0; i < numberOfGenerations; i++){
			if(i == 0){
				genSim = new GenerationSimulator(50);
				genSim.init();
			} else {
				genSim = new GenerationSimulator(newGen);
			}
			genSim.run();
			//System.out.println(genSim.getGeneration().getFittest().toString());
			TournamentManager tm = new TournamentManager();
			newGen = tm.holdTournament(genSim.getGeneration());
		}
		genSim = new GenerationSimulator(newGen);
		genSim.run();
		GenerationSimulator.shutdownThreadPool();
		TournamentManager.shutdownThreadPool();
		s.printElapsedTime();
		System.out.println(genSim.getGeneration().getFittest().toString());
		s.stopAutoPrint(false);
	}
}
