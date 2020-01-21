package agents.agentBehaviours;

import agents.PreneurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class PreneurAutoBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4995334630600882125L;
	
	private boolean finish = false;
	private ACLMessage msgReceived;
	private PreneurAgent owner;
	
	public PreneurAutoBehaviour(PreneurAgent agent) {
		owner = agent;
	}

	@Override
	public void action() {
		//System.out.println("Auto behaviour: start");
		for(int i = 0; i < owner.getEnchereList().size(); i++) {
			//System.out.println("i have: " + owner.getEnchereList().get(i));
			owner.getAutoController().updateEnchere(owner.getEnchereList().get(i));
			String name = owner.getEnchereList().get(i).getVendeur();
			System.out.println("Auto behaviour: i send a to_bid to: " + name);
			owner.getTo_bid().addReceiver(new AID(name, AID.ISLOCALNAME));
		}
		owner.send(owner.getTo_bid());
		
		msgReceived = owner.receive();
		
		if(msgReceived != null) {
			String agentName = msgReceived.getSender().getName().substring(0, 2);
			System.out.println("Auto behaviour: msgReceived from " + agentName);
			if(msgReceived.getPerformative() == ACLMessage.INFORM) {
				String content = msgReceived.getContent();
				System.out.println("Auto behaviour: i received " + content);
			}
			else {
				System.out.println("Auto behaviour: i received as performative " + msgReceived.getPerformative());
			}
			finish = true;
		}
		else {
			block();
		}
	}

	@Override
	public boolean done() {
		return finish;
	}

}
