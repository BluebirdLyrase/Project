package stackoverflow.DataClass;

public class Answer {
	
	public Answer(String body, String score, boolean is_accepted, Comment[] comment, boolean haveComment,String owner,String ownerImage) {
		this.body = body;
		this.score = score;
		this.is_accepted = is_accepted;
		this.comment = comment;
		this.haveComment = haveComment;
		this.owner = owner;
		this.ownerImage = ownerImage;
	}

	private String body;
	private String score;
	private String owner;
	private String ownerImage;
	private boolean is_accepted;
	private Comment[] comment;
	private boolean haveComment;

	public String getOwnerImage() {
		return ownerImage;
	}
	
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

	public boolean isAccepted() {
		return is_accepted;
	}

	public Comment[] getComment() {
		return comment;
	}

}
