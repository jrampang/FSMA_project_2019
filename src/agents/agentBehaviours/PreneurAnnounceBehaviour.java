package agents.agentBehaviours;

import agents.PreneurAgent;
import controller.PreneurAutoController;
import controller.PreneurChoixController;
import controller.PreneurManuelController;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import model.Enchere;

public class PreneurAnnounceBehaviour extends Behaviour{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9069270292752485443L;
	private boolean finish = false;
	private boolean asked = false;
	private ACLMessage msgReceived;
	private ACLMessage query = new ACLMessage(ACLMessage.QUERY_IF);
	private PreneurAgent owner;
	
	public PreneurAnnounceBehaviour(PreneurAgent agent) {
		this.owner = agent;
	}
	
	@Override
	public void action() {
		//System.out.println("The buyer: I'm going to check the market if there is any offer available.");
		if(asked == false) {
			query.addReceiver(new AID("Marche", AID.ISLOCALNAME));
			owner.send(query);
		}
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.QUERY_REF);
		msgReceived = owner.receive(mt);
		
		// if i received a message
		if(msgReceived != null) {
			// if i received a update
			// from the market
			// i update the my list of offers
			//System.out.println("The buyer: I received an answer from the market.");
			try {
				if(msgReceived.getContentObject() != null) {
					Enchere e = (Enchere) msgReceived.getContentObject();
					if(!owner.getChoixController().getList().contains(e)) {
						//owner.getEnchereList().add(e);
						//System.out.println("The buyer: " + owner.getChoixController().getList());
						if(owner.getChoixController() != null) {
							//System.out.println("choixController isn't null");
							owner.getChoixController().addEnchere(e);
							owner.getChoixController().updateEnchere(e);
						}
						else {
							//System.out.println("choixController is null");
						}
					}
					else {
						owner.getChoixController().updateEnchere(e);
						owner.updateEnchereList(e);
					}
				}
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			asked = true;
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