package stackoverflow.DataClass;

public class Question {
	public Question(String title, String body, String[] comment, Answer[] answer,boolean haveComment,boolean haveAnswer) {
		this.title = title;
		this.body = body;
		this.comment = comment;
		this.answer = answer;
		this.haveComment = haveComment;
		this.haveAnswer = haveAnswer;
	}
	private String title;
	private String body;
	private String[] comment;
	private Answer[] answer;
	boolean haveComment;
	boolean haveAnswer;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String[] getComment() {
		return comment;
	}
	public void setComment(String[] comment) {
		this.comment = comment;
	}
	public Answer[] getAnswer() {
		return answer;
	}
	public void setAnswer(Answer[] answer) {
		this.answer = answer;
	}
	
	
	
}
