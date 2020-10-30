import java.util.ArrayList;

public abstract class Post {

	private String id;
	private String title;
	private String description;
	private String creator_id;
	private String status;
	private ArrayList<Reply> repliesList = new ArrayList<Reply>();

	public String getPostDetails() {
		return "\tID: " + getId() + "\n\tTitle: " + getTitle() + "\n\tDescription: " + getDescription()
				+ "\n\tCreator Id: " + getCreator_id() + "\n\tStatus: " + getStatus();
	}

	public Post(String id, String title, String description, String creator_id, String status) {
		setId(id);
		setTitle(title);
		setDescription(description);
		setCreator_id(creator_id);
		setStatus(status);
	}

	public abstract boolean handleReply(Reply reply);

	public abstract String getReplyDetails();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Reply> getReplies() {
		return repliesList;
	}

	public void setReplies(Reply replies) {
		this.repliesList.add(replies);

	}
}
