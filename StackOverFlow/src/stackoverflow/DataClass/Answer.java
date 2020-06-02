package stackoverflow.DataClass;

public class Answer {
	
	public Answer(String body, String score, boolean is_accepted, String[] comment,boolean haveComment) {
		this.body = body;
		this.score = score;
		this.is_accepted = is_accepted;
		this.comment = comment;
		this.haveComment = haveComment;
	}
	
	private String body;
	private String score;
	private boolean is_accepted;
	private String[] comment;
	private boolean haveComment;
 
	public boolean isHaveComment() {
		return haveComment;
	}
	public void setHaveComment(boolean haveComment) {
		this.haveComment = haveComment;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public boolean isIs_accepted() {
		return is_accepted;
	}
	public void setIs_accepted(boolean is_accepted) {
		this.is_accepted = is_accepted;
	}
	public String[] getComment() {
		return comment;
	}
	public void setComment(String[] comment) {
		this.comment = comment;
	}
	
	
}
