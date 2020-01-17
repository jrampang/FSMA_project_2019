package agents.agentBehaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SendBehaviour extends Behaviour {
	
	private boolean finish = false;
	private ACLMessage requestMsg = new ACLMessage(ACLMessage.REQUEST);
	private ACLMessage informMsg = new ACLMessage(ACLMessage.INFORM);
	private String destinataire;
	
	public SendBehaviour(String destinataire) {
		this.destinataire = destinataire;
	}
	
	@Override
	public void action() {
		requestMsg.addReceiver(new AID(destinataire, AID.ISLOCALNAME));
		informMsg.addReceiver(new AID(destinataire, AID.ISLOCALNAME));
	}

	@Override
	public boolean done() {
		return finish;
	}

}
