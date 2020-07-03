package stackoverflow.DataClass;

public class Comment {
	private String body;
	private int score;
	private String owner;

	public String getBody() {
		return body;
	}

	public int getScore() {
		return score;
	}

	public String getOwner() {
		return owner;
	}

	public Comment(String body, int score, String owner) {
		super();
		this.body = body;
		this.score = score;
		this.owner = owner;
	}
}
