package agents.agentBehaviours;

import jade.core.behaviours.Behaviour;

public class PreneurAutoBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4995334630600882125L;
	
	private boolean finish = false;

	@Override
	public void action() {
		System.out.println("Auto behaviour.");
	}

	@Override
	public boolean done() {
		return finish;
	}

}
