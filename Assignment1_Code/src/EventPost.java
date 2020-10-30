
public class EventPost extends Post {

	private String venue;
	private String title;
	private String date;
	private int capacity;
	private int attendee_count = 0;
	private String status;
	private String attendee_list = "";

	public EventPost(String id, String title, String description, String creator_id, String status, String venue,
			String date, int capacity, int attendee_count) {
		super(id, title, description, creator_id, status);
		setTitle(title);
		setStatus(status);
		setVenue(venue);
		setDate(date);
		setCapacity(capacity);
	}

	public String getEventDetail() {
		return "Title: " + getTitle() + "\nVenue: " + getVenue() + "\nStatus: " + getStatus();
	}

	public String getPostDetails() {
		return super.getPostDetails() + "\n\tVenue: " + getVenue() + "\n\tDate: " + getDate() + "\n\tCapacity: "
				+ getCapacity() + "\n\tAttendees: " + getAttendee_count();
	}

	public boolean handleReply(Reply reply) {
		if (getStatus().equals("OPEN")) {
			if (attendee_count < getCapacity()) {
				super.setReplies(reply);
				if (!(getAttendee_list().contains(reply.getResponder_id()))) {
					setAttendee_list(reply.getResponder_id());
					setAttendee_count();
					if (attendee_count == getCapacity()) {
						setStatus("CLOSED");
					}
				} else {
					System.out.println("You have alraedy registered for this Event.");
				}
				return true;
			}
			System.out.println("Event Full. Cannot Register!");
		}
		System.out.println("Event Closed. Cannot Register!");
		return false;
	}

	// returning string with the replied details
	public String getReplyDetails() {
		return super.getPostDetails() + "\n\tVenue: " + getVenue() + "\n\tDate: " + getDate() + "\n\tCapacity: "
				+ getCapacity() + "\n\tAttendees: " + getAttendee_count() + "\n\tAttendee List: " + getAttendee_list();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getAttendee_count() {
		return attendee_count;
	}

	public void setAttendee_count() {
		this.attendee_count++;
	}

	public String getAttendee_list() {
		return attendee_list;
	}

	public void setAttendee_list(String attendee_list) {
		if (this.attendee_list == "")
			this.attendee_list += attendee_list;
		else
			this.attendee_list += "," + attendee_list;
	}

}
