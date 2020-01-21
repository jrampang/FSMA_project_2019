package agents.agentBehaviours;

import java.io.IOException;

import agents.MarcheAgent;
import controller.MarcheController;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import model.Enchere;

public class MarcheReceiveBehaviour extends Behaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4066769460731071811L;
	private boolean finish = false;
	private ACLMessage msg;
	private ACLMessage answer = new ACLMessage(ACLMessage.INFORM);
	private MarcheAgent marche;
	
	private boolean test = true;
	
	public MarcheReceiveBehaviour(MarcheAgent agent) {
		this.marche = agent;
	}
	
	@Override
	public void action() {
		System.out.println("The market: I'm going to check if there is any seller available.");
		
		// if i don't have any functional seller(s)
		// i create them
		// T_T
		if(test) {
			String V1 = "V1";
			String V2 = "V2";
			String V3 = "V3";
			
			marche.addVendeur(V1, new Enchere("1 lot de poisson", Integer.toString(20), V1));
			MarcheController.addEnchere(new Enchere("1 lot de poisson", Integer.toString(20), V1));
			
			marche.addVendeur(V2, new Enchere("1 lot de poisson", Integer.toString(15), V2));
			MarcheController.addEnchere(new Enchere("1 lot de poisson", Integer.toString(15), V2));
			
			marche.addVendeur(V3, new Enchere("1 lot de poisson", Integer.toString(25), V3));
			MarcheController.addEnchere(new Enchere("1 lot de poisson", Integer.toString(25), V3));
			
			test = false;
		}
		
		msg = myAgent.receive();
		
		// if i received a message
		if(msg != null) {
			String agentName = msg.getSender().getName().substring(0, 2);
			// if i received a to_announce
			// if it's a new seller
			// i add him and his offer
			// or update his offer
			if(msg.getPerformative() == ACLMessage.CFP) {
				System.out.println("Market behaviour: i received a to_announce.");
				if(!marche.getVendeurs().containsKey(agentName)) {
					System.out.println("Marche behaviour: i add a new seller / offer.");
					marche.addVendeur(agentName, new Enchere("1 lot de poisson", msg.getContent(), agentName));
					MarcheController.addEnchere(new Enchere("1 lot de poisson", msg.getContent(), agentName));
				}
				else {
					System.out.println("Market behaviour: i update a seller / offer.");
					marche.updateVendeur(agentName, new Enchere("1 lot de poisson", msg.getContent(), agentName));
					MarcheController.updateEnchere(new Enchere("1 lot de poisson", msg.getContent(), agentName));
				}
			}
			// if i received a to_rep_bid
			// i remove the seller
			else if(msg.getPerformative() == ACLMessage.INFORM) {
				System.out.println("Market behaviour: i received a to_rep_bid.");
				if(marche.getVendeurs().containsKey(agentName)) {
					Enchere e = marche.getVendeurs().get(agentName);
					marche.deleteVendeur(agentName);
					MarcheController.deleteEnchere(e);
				}
			}
			// if i received a query_if
			// i send to the buyer the available offers
			else if(msg.getPerformative() == ACLMessage.QUERY_IF) {
				System.out.println("Market behaviour: i received a request from a buyer.");
				System.out.println("Buyer name: " + agentName);
				
				if(marche.getVendeurs().size() > 0) {
					answer.addReceiver(new AID(agentName, AID.ISLOCALNAME));
					//try {
					for(int i = 0; i < marche.getVendeurs().size(); i++) {
						marche.getVendeurs().forEach((k,v)->{
							try {
								System.out.println("Market test 2: " + v);
								answer.setContentObject(v);
								myAgent.send(answer);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
					}
					try {
						answer.setContentObject(null);
						myAgent.send(answer);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			block();
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return finish;
	}

}