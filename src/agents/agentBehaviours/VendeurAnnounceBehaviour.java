package agents.agentBehaviours;

import java.util.HashMap;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VendeurAnnounceBehaviour extends Behaviour{
	boolean finish = false;
	
	private String prix;

	private int timer;

	private int increment;

	private int decrement;
	
	private HashMap<String, String> acheteurs;

	
	public VendeurAnnounceBehaviour(String prix, int timer, int increment, int decrement) {
		this.prix = prix;
		this.timer = timer;
		this.increment = increment;
		this.decrement = decrement;
		acheteurs = new HashMap<String, String>();
	}

	@Override
	public void action() {
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.addReceiver(new AID("Marche", AID.ISLOCALNAME));
		msg.setContent(prix);
		myAgent.send(msg);
		int compteur = 0;
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < timer) {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
			ACLMessage receive = myAgent.receive(mt);
			
			if (receive != null) {
				compteur++;
				acheteurs.put(receive.getSender().toString(), prix);
			}
			
			if(compteur == 0) {
				int prixInt = Integer.parseInt(prix)-decrement;
				
				if (prixInt<=0) {
					prixInt = 0;
				}
				
				myAgent.addBehaviour(new VendeurAnnounceBehaviour(Integer.toString(prixInt), timer, increment, decrement));
			}
			
			else if (compteur == 1) {
				
				for (String i : acheteurs.keySet()) {
					ACLMessage rep_bid = new ACLMessage(ACLMessage.INFORM);
					rep_bid.addReceiver(new AID(i, AID.ISLOCALNAME));
					rep_bid.setContent("OK");
					myAgent.send(rep_bid);
				}
				
				myAgent.addBehaviour(new VendeurAttributeBehaviour(acheteurs));
			}
			
			else {
				
				for (String i : acheteurs.keySet()) {
					ACLMessage rep_bid = new ACLMessage(ACLMessage.INFORM);
					rep_bid.addReceiver(new AID(i, AID.ISLOCALNAME));
					rep_bid.setContent("NOK");
					myAgent.send(rep_bid);
				}
				
				int prixInt = Integer.parseInt(prix)+increment;
				myAgent.addBehaviour(new VendeurAnnounceBehaviour(Integer.toString(prixInt), timer, increment, decrement));
			}
		}
		finish = true;
	}

	@Override
	public boolean done() {
		return finish;
	}
	

}
