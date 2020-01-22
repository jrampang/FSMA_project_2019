package agents.agentBehaviours;

import java.io.IOException;
import java.util.ArrayList;

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
	private ACLMessage answer;
	private ACLMessage confirm;
	private MarcheAgent marche;
	
	private boolean test = false;
	
	private ArrayList<String> waitingBuyer;
	
	public MarcheReceiveBehaviour(MarcheAgent agent) {
		this.marche = agent;
		this.waitingBuyer = new ArrayList<>();
	}
	
	@Override
	public void action() {
		//System.out.println("The market: I'm going to check if there is any seller available.");
		
		msg = myAgent.receive();
		
		// if i received a message
		if(msg != null) {
			String agentName = msg.getSender().getName().substring(0, 2);
			// if i received a to_announce
			// if it's a new seller
			// i add him and his offer
			// or update his offer
			if(msg.getPerformative() == ACLMessage.CFP) {
				//System.out.println("Market behaviour: i received a to_announce.");
				if(!marche.getVendeurs().containsKey(agentName)) {
					System.out.println(marche.getMyName() + ": i add a new seller / offer.");
					marche.addVendeur(agentName, new Enchere("1 lot de poisson", msg.getContent(), agentName));
					MarcheController.addEnchere(new Enchere("1 lot de poisson", msg.getContent(), agentName));
				}
				else {
					//System.out.println("Market behaviour: i update a seller / offer.");
					marche.updateVendeur(agentName, new Enchere("1 lot de poisson", msg.getContent(), agentName));
					MarcheController.updateEnchere(new Enchere("1 lot de poisson", msg.getContent(), agentName));
				}
				if(this.waitingBuyer.size() > 0) {
					this.answer = new ACLMessage(ACLMessage.QUERY_REF);
					for(int i = 0; i < this.waitingBuyer.size(); i++) {
						answer.addReceiver(new AID(this.waitingBuyer.get(i), AID.ISLOCALNAME));
					}
					marche.getVendeurs().forEach((k,v)->{
						try {
							//System.out.println("Market test 2: " + v);
							answer.setContentObject(v);
							myAgent.send(answer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					try {
						answer.setContentObject(null);
						myAgent.send(answer);
					} catch (IOException e) {
						e.printStackTrace();
					}
					//this.waitingBuyer.clear();
					//System.out.println("Market: waiting buyers cleared.");
				}
			}
			// if i received a to_rep_bid
			// i remove the seller
			else if(msg.getPerformative() == ACLMessage.INFORM) {
				System.out.println(marche.getMyName() + ": i received a rep_bid from " + agentName);
				System.out.println(marche.getMyName() + ": i cancel his offer.");
				if(marche.getVendeurs().containsKey(agentName)) {
					MarcheController.updateEnchere(new Enchere("OVER", msg.getContent(), agentName));
					confirm = new ACLMessage(ACLMessage.INFORM);
					confirm.addReceiver(new AID(agentName, AID.ISLOCALNAME));
					marche.send(confirm);
					
					// I want to notify the buyer(s)
					// that an offer is over
					
					System.out.println(marche.getMyName() + ": I want to notify the buyer(s) that an offer is over from" + agentName);
					this.answer = new ACLMessage(ACLMessage.QUERY_REF);
					for(int i = 0; i < this.waitingBuyer.size(); i++) {
						answer.addReceiver(new AID(this.waitingBuyer.get(i), AID.ISLOCALNAME));
					}
					marche.getVendeurs().forEach((k,v)->{
						try {
							answer.setContentObject(new Enchere("OVER", msg.getContent(), agentName));
							myAgent.send(answer);
						} catch (IOException err) {
							err.printStackTrace();
						}
					});
					marche.deleteVendeur(agentName);
				}
				
			}
			// if i received a query_if
			// i send to the buyer(s) the available offers
			else if(msg.getPerformative() == ACLMessage.QUERY_IF) {
				System.out.println(marche.getMyName() + ": i received a request from a buyer.");
				System.out.println("Buyer name: " + agentName);
				if(!this.waitingBuyer.contains(agentName)) {
					this.waitingBuyer.add(agentName);
				}
				System.out.println(marche.getMyName() + ": waiting buyers: " + this.waitingBuyer);
				/*if(marche.getVendeurs().size() > 0) {
					System.out.println("Market behaviour: i'm going to send the offers i have.");
					answer.addReceiver(new AID(agentName, AID.ISLOCALNAME));
					marche.getVendeurs().forEach((k,v)->{
						try {
							//System.out.println("Market test 2: " + v);
							answer.setContentObject(v);
							myAgent.send(answer);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					try {
						answer.setContentObject(null);
						myAgent.send(answer);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					this.waitingBuyer.add(agentName);
					System.out.println("Market: waiting buyers: " + this.waitingBuyer);
				}*/
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