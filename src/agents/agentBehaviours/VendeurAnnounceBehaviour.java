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

	private HashMap<String, String> acheteursPrecedents;
	
	private VendeurAgent agent;

	
	public VendeurAnnounceBehaviour(String prix, int timer, int increment, int decrement, VendeurAgent agent, HashMap<String, String> acheteursPrecedents) {
		this.prix = prix;
		this.timer = timer;
		this.increment = increment;
		this.decrement = decrement;
		this.agent = agent;
		acheteurs = new HashMap<String, String>();
		this.acheteursPrecedents = acheteursPrecedents;
	}
	
	public VendeurAnnounceBehaviour(String prix, int timer, int increment, int decrement, VendeurAgent agent) {
		this.prix = prix;
		this.timer = timer;
		this.increment = increment;
		this.decrement = decrement;
		this.agent = agent;
		acheteurs = new HashMap<String, String>();
		this.acheteursPrecedents = new HashMap<String, String>();
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
				Encherisseur e = new Encherisseur(receive.getSender().getName().toString().substring(0,2), prix, agent.getEnchere());
				agent.getController().addEncherisseur(e);
			}
		}
		acheteursPrecedents.putAll(acheteurs);
			if(compteur == 0) {
				int prixInt = Integer.parseInt(prix)-decrement;
				
				if (prixInt<=0) {
					prixInt = 0;
				}
				
				myAgent.addBehaviour(new VendeurAnnounceBehaviour(Integer.toString(prixInt), timer, increment, decrement, agent, acheteursPrecedents));
			}
			
			else if (compteur == 1) {
				for (String i : acheteurs.keySet()) {
					acheteursPrecedents.remove(i);
					//System.out.println("VendeurAnnounceBehaviour : " + i + ".");
					ACLMessage rep_bid = new ACLMessage(ACLMessage.INFORM);
					ACLMessage rep_bid2 = new ACLMessage(ACLMessage.INFORM);
					rep_bid.addReceiver(new AID(i, AID.ISLOCALNAME));
					rep_bid2.addReceiver(new AID("Marche", AID.ISLOCALNAME));
					rep_bid.setContent("OK");
					myAgent.send(rep_bid);
					myAgent.send(rep_bid2);
				}
				ACLMessage rep_bid = new ACLMessage(ACLMessage.REJECT_PROPOSAL);;
				for (String i : acheteursPrecedents.keySet()) {
					//System.out.println("VendeurAnnounceBehaviour : " + i + ".");
					rep_bid.addReceiver(new AID(i, AID.ISLOCALNAME));
				}
				myAgent.send(rep_bid);
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
				myAgent.addBehaviour(new VendeurAnnounceBehaviour(Integer.toString(prixInt), timer, increment, decrement, agent, acheteursPrecedents));
			}
		finish = true;
	}

	@Override
	public boolean done() {
		return finish;
	}
	

}
