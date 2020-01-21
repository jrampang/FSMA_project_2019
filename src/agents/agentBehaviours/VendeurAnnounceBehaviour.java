package agents.agentBehaviours;

import java.util.HashMap;

import agents.VendeurAgent;
import controller.VendeurController;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.Encherisseur;

public class VendeurAnnounceBehaviour extends Behaviour{
	boolean finish = false;
	
	private String prix;

	private int timer;

	private int increment;

	private int decrement;
	
	private HashMap<String, String> acheteurs;
	
	private VendeurAgent agent;

	
	public VendeurAnnounceBehaviour(String prix, int timer, int increment, int decrement, VendeurAgent agent) {
		this.prix = prix;
		this.timer = timer;
		this.increment = increment;
		this.decrement = decrement;
		this.agent = agent;
		acheteurs = new HashMap<String, String>();
	}

	@Override
	public void action() {
		agent.getController().removeAllEncherisseurs();
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.addReceiver(new AID("Marche", AID.ISLOCALNAME));
		msg.setContent(prix);
		myAgent.send(msg);
		int compteur = 0;
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < timer*1000) {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
			ACLMessage receive = myAgent.receive(mt);
			
			if (receive != null) {
				compteur++;
				acheteurs.put(receive.getSender().getName().toString().substring(0,2), prix);
				Encherisseur e = new Encherisseur(receive.getSender().toString(), prix, agent.getEnchere());
				agent.getController().addEncherisseur(e);
			}
		}
			if(compteur == 0) {
				int prixInt = Integer.parseInt(prix)-decrement;
				
				if (prixInt<=0) {
					prixInt = 0;
				}
				
				myAgent.addBehaviour(new VendeurAnnounceBehaviour(Integer.toString(prixInt), timer, increment, decrement, agent));
			}
			
			else if (compteur == 1) {
				
				for (String i : acheteurs.keySet()) {
					// i say to the market my offer is going to be attribut
					// so he can delete it from his list
					// he tell me he did it
					// and so i can send the OK and attribut to the buyer
					System.out.println("VendeurAnnounceBehaviour : " + i);
					ACLMessage rep_bid = new ACLMessage(ACLMessage.INFORM);
					rep_bid.addReceiver(new AID(i, AID.ISLOCALNAME));
					rep_bid.addReceiver(new AID("Marche", AID.ISLOCALNAME));
					rep_bid.setContent("OK");
					myAgent.send(rep_bid);
				}
				
				myAgent.addBehaviour(new VendeurAttributeBehaviour(acheteurs, agent));
			}
			
			else {
				
				for (String i : acheteurs.keySet()) {
					ACLMessage rep_bid = new ACLMessage(ACLMessage.INFORM);
					rep_bid.addReceiver(new AID(i, AID.ISLOCALNAME));
					rep_bid.addReceiver(new AID("Marche", AID.ISLOCALNAME));
					rep_bid.setContent("NOK");
					myAgent.send(rep_bid);
				}
				
				int prixInt = Integer.parseInt(prix)+increment;
				myAgent.addBehaviour(new VendeurAnnounceBehaviour(Integer.toString(prixInt), timer, increment, decrement, agent));
			}
		finish = true;
	}

	@Override
	public boolean done() {
		return finish;
	}
	

}
