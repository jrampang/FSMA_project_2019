package agents.agentBehaviours;

import agents.PreneurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.Enchere;

public class PreneurAnnounceBehaviour extends Behaviour{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9069270292752485443L;
	private boolean finish = false;
	private boolean asked = false;
	private boolean auto = true;
	private ACLMessage msgReceived;
	private ACLMessage query = new ACLMessage(ACLMessage.QUERY_IF);
	private PreneurAgent owner;
	
	public PreneurAnnounceBehaviour(PreneurAgent a) {
		this.owner = a;
	}
	
	@Override
	public void action() {
		if(asked == false) {
			System.out.println("The buyer: I'm going to check the market if there is any offer available.");
			query.addReceiver(new AID("Marche", AID.ISLOCALNAME));
			myAgent.send(query);
			asked = true;
		}
		
		msgReceived = myAgent.receive();
		
		// if i received a message
		if(msgReceived != null) {
			// if i received a to_announce
			// if it's a new seller
			// i add him and his offer
			// or update his offer
			if(msgReceived.getPerformative() == ACLMessage.INFORM) {
				System.out.println("The buyer: I received an answer from the market.");
				try {
					Enchere e = (Enchere) msgReceived.getContentObject();
					owner.getEnchereList().add(e);
					System.out.println("The buyer: " + owner.getEnchereList());
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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