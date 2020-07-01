package stackoverflow.DataClass;

public class Question {
	public Question(String title, String body, Comment[] comment, Answer[] answer,boolean haveComment,boolean haveAnswer,String owner,String ownerImage) {
		this.title = title;
		this.body = body;
		this.comment = comment;
		this.answer = answer;
		this.haveComment = haveComment;
		this.haveAnswer = haveAnswer;
		this.owner = owner;
		this.ownerImage = ownerImage;
	}
	
	private String title;
	private String body;
	private Comment[] comment;
	private Answer[] answer;
	private boolean haveComment;
	private boolean haveAnswer;
	private String owner;
	private String ownerImage;
	
	public String getOwner() {
		return owner;
	}
	public String getOwnerImage() {
		return ownerImage;
	}
	
	public boolean isHaveComment() {
		return haveComment;
	}
	public boolean isHaveAnswer() {
		return haveAnswer;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public Comment[] getComment() {
		return comment;
	}
	public Answer[] getAnswer() {
		return answer;
	}
	
	
	
}
