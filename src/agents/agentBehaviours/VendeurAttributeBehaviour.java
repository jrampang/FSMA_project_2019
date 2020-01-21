package agents.agentBehaviours;

import java.util.HashMap;

import agents.VendeurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VendeurAttributeBehaviour extends Behaviour{
	private HashMap<String, String> acheteurs;
	private boolean finish = false;
	private VendeurAgent agent;

	public VendeurAttributeBehaviour(HashMap<String, String> acheteurs, VendeurAgent agent) {
		this.acheteurs = acheteurs;
		this.agent = agent;
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
			ACLMessage response = new ACLMessage(ACLMessage.AGREE);
			response.addReceiver(msg.getSender());
			System.out.println(msg);
			response.setContent("Poisson");
			myAgent.send(response);
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
