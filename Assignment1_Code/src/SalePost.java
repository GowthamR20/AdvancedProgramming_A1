import java.util.Collections;
import java.util.Comparator;

public class SalePost extends Post {
	private double askingPrice;
	private double highestPrice;
	private double minimumRaise;
	private String title;

	public double getAskingPrice() {
		return askingPrice;
	}

	public void setAskingPrice(double askingPrice) {
		this.askingPrice = askingPrice;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getMinimumRaise() {
		return minimumRaise;
	}

	public void setMinimumRaise(double minimumRaise) {
		this.minimumRaise = minimumRaise;
	}

	public SalePost(String id, String title, String description, String creator_id, String status, double askingPrice,
			double minimumRaise, double highestPrice) {
		super(id, title, description, creator_id, status);
		setTitle(title);
		setAskingPrice(askingPrice);
		setMinimumRaise(minimumRaise);
		setHighestPrice(highestPrice);

	}

	public String getSaleDetail() {
		return "Title: " + getTitle() + "\nHighest Price: " + getHighestPrice() + "\nMinimum Raise: "
				+ getMinimumRaise();
	}

	public String getPostDetails() {
		return super.getPostDetails() + "\n\tAsking Price: " + getAskingPrice() + "\n\tMinimum Raise: "
				+ getMinimumRaise() + "\n\tHigest Price: " + getHighestPrice();
	}

	// returning string with the replied details
	@Override
	public String getReplyDetails() {
		Collections.sort(super.getReplies(), new Comparator<Reply>() {
			@Override
			public int compare(Reply r1, Reply r2) {
				if (r1.getValue() == r2.getValue())
					return 0;
				else if (r1.getValue() < r2.getValue())
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

	@Override
	public boolean handleReply(Reply reply) {
		if (getStatus().equalsIgnoreCase("OPEN")) {
			if (reply.getValue() < getMinimumRaise()) {
				System.out.println("Offer not accepted");
				return false;
			} else {
				if ((reply.getValue() >= getMinimumRaise() + getHighestPrice())
						&& reply.getValue() < getAskingPrice()) {
					setHighestPrice(reply.getValue());
					// System.out.println("Offer is accepted. But not sold as it is less then the
					// asking price.");
					super.setReplies(reply);
					return true;
				} else if (reply.getValue() > getAskingPrice()) {
					setHighestPrice(reply.getValue());
					System.out.println("Item is sold to the user: " + reply.getResponder_id());
					setStatus("CLOSED");
					super.setReplies(reply);
					return true;
				} else {
					System.out.println("Offer not accepted - bid higher)");
					return false;
				}
			}
		}
		System.out.println("Post closed");
		return false;

	}
}