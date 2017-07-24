package sim;

import plugins.shepherd.sim.Game;
import plugins.gameTest.ByteGame;

public class SimulationFactory {
	
	private SimulationFactory(){}
	
	public static Simulation createSim(){
		return new ByteGame();
	}

}
