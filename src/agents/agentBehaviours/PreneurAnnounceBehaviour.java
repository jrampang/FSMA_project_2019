package agents.agentBehaviours;

import agents.PreneurAgent;
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
	private ACLMessage query;
	private PreneurAgent owner;
	
	public PreneurAnnounceBehaviour(PreneurAgent agent) {
		this.owner = agent;
	}
	
	@Override
	public void action() {
		//System.out.println("The buyer: I'm going to check the market if there is any offer available.");
		if(asked == false) {
			query = new ACLMessage(ACLMessage.QUERY_IF);
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
			//System.out.println(owner.getMyName() + ": I received an answer from the market.");
			try {
				if(msgReceived.getContentObject() != null) {
					Enchere e = (Enchere) msgReceived.getContentObject();
					if(!owner.getChoixController().getList().contains(e)) {
						if(owner.getChoixController() != null) {
							if(!e.getObjet().contains("OVER")) {
								owner.getChoixController().addEnchere(e);
								owner.getChoixController().updateEnchere(e);
								owner.updateEnchereList(e);
							}
							else {
								System.out.println(owner.getMyName() + ": I received an offer terminated.");
							}
						}
					}
					else {
						owner.getChoixController().updateEnchere(e);
						owner.updateEnchereList(e);
						if(owner.getMode().contains("auto")) {
							if(owner.getAutoController() != null) {
								//System.out.println(owner.getMyName() + ": I am on auto mode.");
								owner.getAutoController().updateEnchere(e);
							}
						}
						else if(owner.getMode().contains("manuel")) {
							if(owner.getManuelController() != null) {
								//System.out.println(owner.getMyName() + ": I am on manuel mode.");
								owner.getManuelController().updateEnchere(e);
							}
						}
					}
				}
				else {
					//System.out.println(owner.getMyName() + ": the market has send me all his offers.");
				}
			} catch (UnreadableException e) {
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
		return finish;
	}
}