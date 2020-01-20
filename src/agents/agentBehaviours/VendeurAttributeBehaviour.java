package agents.agentBehaviours;

import java.util.HashMap;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VendeurAttributeBehaviour extends Behaviour{
	private HashMap<String, String> acheteurs;
	private boolean finish = false;

	public VendeurAttributeBehaviour(HashMap<String, String> acheteurs) {
		this.acheteurs = acheteurs;
	}

	@Override
	public void action() {
		for (String i : acheteurs.keySet()) {
			ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			msg.addReceiver(new AID(i, AID.ISLOCALNAME));
			myAgent.send(msg);
		}
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null && msg.getAllReceiver() == myAgent) {
			
		}
		else {
			block();
		}
		
		ACLMessage response = new ACLMessage(ACLMessage.AGREE);
		response.addReceiver(msg.getSender());
		response.setContent("Poisson");
		myAgent.send(response);
		finish = true;
	}

	@Override
	public boolean done() {
		return finish;
	}

}
