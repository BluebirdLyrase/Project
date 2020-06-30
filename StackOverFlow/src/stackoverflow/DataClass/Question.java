package stackoverflow.DataClass;

public class Question {
	public Question(String title, String body, String[] comment, Answer[] answer,boolean haveComment,boolean haveAnswer,String owner) {
		this.title = title;
		this.body = body;
		this.comment = comment;
		this.answer = answer;
		this.haveComment = haveComment;
		this.haveAnswer = haveAnswer;
		this.owner = owner;
	}
	
	private String title;
	private String body;
	private String[] comment;
	private Answer[] answer;
	private boolean haveComment;
	private boolean haveAnswer;
	private String owner;
	
	public String getOwner() {
		return owner;
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
	public String[] getComment() {
		return comment;
	}
	public Answer[] getAnswer() {
		return answer;
	}
	
	
	
}
