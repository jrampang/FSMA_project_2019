package agents.agentBehaviours;

import agents.PreneurAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PreneurAutoBehaviour extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4995334630600882125L;
	
	private boolean finish = false;
	private boolean biding = true;
	private ACLMessage msgReceived;
	private PreneurAgent owner;
	
	private ACLMessage to_bid;
	private ACLMessage to_pay;
	
	private MessageTemplate informMT = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	private MessageTemplate apMT = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
	private MessageTemplate agreeMT = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
	private MessageTemplate rejectMT = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
	
	public PreneurAutoBehaviour(PreneurAgent agent) {
		owner = agent;
	}

	@Override
	public void action() {
		//System.out.println("Auto behaviour: start");
		if(biding) {
			to_bid = new ACLMessage(ACLMessage.PROPOSE);
			for(int i = 0; i < owner.getEnchereList().size(); i++) {
				//System.out.println("i have: " + owner.getEnchereList().get(i));
				if(!owner.getEnchereList().get(i).getPrix().contains("NOK")) {
					owner.getAutoController().updateEnchere(owner.getEnchereList().get(i));
					String name = owner.getEnchereList().get(i).getVendeur();
					int price = Integer.parseInt(owner.getEnchereList().get(i).getPrix());
					System.out.println(owner.getMyName() + ": price " + price);
					System.out.println(owner.getMyName() + ": budget " + owner.getBudget());
					if(price <= owner.getBudget()) {
						System.out.println(owner.getMyName() + ": i send a to_bid to: " + name);
						to_bid.addReceiver(new AID(name, AID.ISLOCALNAME));
						biding = false;
					}
					else {
						System.out.println(owner.getMyName() + ": i can't send a to_bid to: " + name);
					}
				}
			}
			owner.send(to_bid);
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
					owner.getAutoController().updateEnchere(owner.getEnchereList().get(i));
				}
				biding = true;
			}
			else {
				System.out.println(owner.getMyName() + ": to_bid accepted");
				biding = false;
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
			to_pay = new ACLMessage(ACLMessage.CONFIRM);
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
			owner.getAutoController().updateState(name, "WIN");
			finish = true;
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
			owner.getAutoController().updateState(name, "OVER / LOST");
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
