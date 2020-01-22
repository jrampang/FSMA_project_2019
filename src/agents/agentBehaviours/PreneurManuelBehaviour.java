package agents.agentBehaviours;

import java.io.IOException;

import agents.PreneurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.Enchere;

public class PreneurManuelBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056054096804068926L;
	
	private int step = 0;
	
	private boolean finish = false;
	private ACLMessage msgReceived;
	private PreneurAgent owner;
	
	private ACLMessage to_pay = new ACLMessage(ACLMessage.CONFIRM);
	
	private MessageTemplate informMT = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	private MessageTemplate apMT = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
	private MessageTemplate agreeMT = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
	private MessageTemplate rejectMT = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
	
	public PreneurManuelBehaviour(PreneurAgent agent) {
		owner = agent;
	}
	@Override
	public void action() {
		//System.out.println("Manuel behaviour: start");
		if(owner.isBiding()) {
			for(int i = 0; i < owner.getEnchereList().size(); i++) {
				//System.out.println(owner.getMyName() + ": i have now " + owner.getEnchereList());
				owner.getManuelController().updateEnchere(owner.getEnchereList().get(i));
			}
		}
		
		msgReceived = owner.receive(informMT);
		
		if(msgReceived != null) {
			String name = msgReceived.getSender().getName().substring(0, 2);
			System.out.println(owner.getMyName() + ": rep_bid received from " + name);
			String content = msgReceived.getContent();
			System.out.println(owner.getMyName() + ": content " + content);
			if(content.contains("NOK")) {
				System.out.println(owner.getMyName() + ": to_bid refused");
				
				for(int i = 0; i < owner.getEnchereList().size(); i++) {
					owner.getManuelController().updateEnchere(owner.getEnchereList().get(i));
				}
			}
			else {
				owner.setBiding(false);
			}
		}
		else {
			block();
		}

		msgReceived = owner.receive(apMT);
		
		if(msgReceived != null) {
			String name = msgReceived.getSender().getName().substring(0, 2);
			System.out.println(owner.getMyName() + ": msgReceived from " + name);
			System.out.println(owner.getMyName() + ": it's a to_attribute.");
			to_pay.addReceiver(new AID(name, AID.ISLOCALNAME));
			owner.send(to_pay);
		}
		else {
			block();
		}
		
		msgReceived = owner.receive(agreeMT);
		
		if(msgReceived != null) {
			String name = msgReceived.getSender().getName().substring(0, 2);
			System.out.println(owner.getMyName() + ": msgReceived from " + name);
			System.out.println(owner.getMyName() + ": it's a to_give.");
			System.out.println(owner.getMyName() + ": i received my fish.");
			owner.getManuelController().updateState(name);
		}
		else {
			block();
		}
		
		msgReceived = owner.receive(rejectMT);
		
		if(msgReceived != null) {
			String name = msgReceived.getSender().getName().substring(0, 2);
			System.out.println(owner.getMyName() + ": msgReceived from " + name);
			System.out.println(owner.getMyName() + ": it's a reject_proposal.");
			System.out.println(owner.getMyName() + ": i lost the offer.");
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
