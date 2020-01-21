package agents.agentBehaviours;

import agents.PreneurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;

public class PreneurAutoBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4995334630600882125L;
	
	private boolean finish = false;
	private PreneurAgent owner;
	
	public PreneurAutoBehaviour(PreneurAgent agent) {
		owner = agent;
	}

	@Override
	public void action() {
		System.out.println("Auto behaviour: start");
		for(int i = 0; i < owner.getEnchereList().size(); i++) {
			//System.out.println("i have: " + owner.getEnchereList().get(i));
			String name = owner.getEnchereList().get(i).getVendeur();
			owner.getTo_bid().addReceiver(new AID(name, AID.ISLOCALNAME));
		}
		owner.send(owner.getTo_bid());
		finish = true;
	}

	@Override
	public boolean done() {
		return finish;
	}

}
