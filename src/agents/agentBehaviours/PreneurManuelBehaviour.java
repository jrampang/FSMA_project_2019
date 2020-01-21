package agents.agentBehaviours;

import agents.PreneurAgent;
import jade.core.behaviours.Behaviour;

public class PreneurManuelBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056054096804068926L;
	
	private boolean finish = false;
	private PreneurAgent owner;
	
	public PreneurManuelBehaviour(PreneurAgent agent) {
		owner = agent;
	}
	@Override
	public void action() {
		System.out.println("Manuel behaviour: Start");
		for(int i = 0; i < owner.getEnchereList().size(); i++) {
			System.out.println("i have: " + owner.getEnchereList().get(i));
		}
		finish = true;
	}

	@Override
	public boolean done() {
		return finish;
	}

}
