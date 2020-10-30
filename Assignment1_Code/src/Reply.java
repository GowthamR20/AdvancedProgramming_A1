
public class Reply {

	private String postId;
	private double value;
	private String responderId;

	public Reply(String post_id, double value, String responder_id) {
		setPost_id(post_id);
		setValue(value);
		setResponder_id(responder_id);
	}

	public String getPost_id() {
		return postId;
	}

	public void setPost_id(String postId) {
		this.postId = postId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getResponder_id() {
		return responderId;
	}

	public void setResponder_id(String responderId) {
		this.responderId = responderId;
	}

	public String getReplyDeails() {
		return "\n\tPost Id: " + getPost_id() + "\n\tValue: " + getValue() + "\n\tResponder Id: " + getResponder_id();
	}
}
