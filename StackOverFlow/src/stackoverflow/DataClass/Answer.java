package stackoverflow.DataClass;

public class Answer {
	
	public Answer(String body, String score, boolean is_accepted, Comment[] comment, boolean haveComment,String owner) {
		this.body = body;
		this.score = score;
		this.is_accepted = is_accepted;
		this.comment = comment;
		this.haveComment = haveComment;
		this.owner = owner;
	}

	private String body;
	private String score;
	private String owner;
	private boolean is_accepted;
	private Comment[] comment;
	private boolean haveComment;

	public String getOwner() {
		return owner;
	}

	public boolean isHaveComment() {
		return haveComment;
	}

	public String getBody() {
		return body;
	}

	public String getScore() {
		return score;
	}

	public boolean isIs_accepted() {
		return is_accepted;
	}

	public Comment[] getComment() {
		return comment;
	}

}
