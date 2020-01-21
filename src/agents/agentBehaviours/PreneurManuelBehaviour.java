package agents.agentBehaviours;

import jade.core.behaviours.Behaviour;

public class PreneurManuelBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056054096804068926L;
	
	private boolean finish = false;

	@Override
	public void action() {
		System.out.println("Manuel behaviour.");
	}

	@Override
	public boolean done() {
		return finish;
	}

}
