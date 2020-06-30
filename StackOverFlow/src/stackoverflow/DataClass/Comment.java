package stackoverflow.DataClass;

public class Comment {
	private String body;
	private String score;
	private String owner;

	public String getBody() {
		return body;
	}

	public String getScore() {
		return score;
	}

	public String getOwner() {
		return owner;
	}

	public Comment(String body, String score, String owner) {
		super();
		this.body = body;
		this.score = score;
		this.owner = owner;
	}
}
