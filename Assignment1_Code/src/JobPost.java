import java.util.*;

public class JobPost extends Post {
	private double proposedPrice;
	private double LowestOffer;

	public JobPost(String id, String title, String description, String creator_id, String status,
			double proposedPrice) {
		super(id, title, description, creator_id, status);
		setProposedPrice(proposedPrice);
	}

	public double getProposedPrice() {
		return proposedPrice;
	}

	public void setProposedPrice(double proposedPrice) {
		this.proposedPrice = proposedPrice;
	}

	public double getLowestOffer() {
		return LowestOffer;
	}

	public void setLowestOffer(double lowestOffer) {
		LowestOffer = lowestOffer;
	}

	@Override
	public boolean handleReply(Reply reply) {
		if (getStatus().equals("OPEN")) {
			if (reply.getValue() > getProposedPrice()) {
				return false;
			} else if (getLowestOffer() == 0 && reply.getValue() < getProposedPrice()) {
				setLowestOffer(reply.getValue());
				super.setReplies(reply);
				return true;
			} else if (reply.getValue() < getLowestOffer() && reply.getValue() > 0) {
				setLowestOffer(reply.getValue());
				super.setReplies(reply);
				return true;
			}
			// System.out.println(reply.getValue() + "value" + getLowestOffer() + "lowest");
		} else {
			System.out.println("The Job is closed.");
		}
		return false;
	}

	@Override
	public String getReplyDetails() {
		Collections.sort(super.getReplies(), new Comparator<Reply>() {
			@Override
			public int compare(Reply r1, Reply r2) {
				if (r1.getValue() == r2.getValue())
					return 0;
				else if (r1.getValue() > r2.getValue())
					return 1;
				else
					return -1;
			}
		});
		System.out.println(getPostDetails());
		System.out.print("\tOffer List: ");
		for (int i = 0; i < getReplies().size(); i++) {
			System.out.println(getReplies().get(i).getResponder_id() + " " + getReplies().get(i).getValue());
		}
		return " ";
	}

	public String getPostDetails() {
		return super.getPostDetails() + "\n\tProposed Price: " + getProposedPrice() + "\n\tLowest Offer: "
				+ getLowestOffer();

	}
}
