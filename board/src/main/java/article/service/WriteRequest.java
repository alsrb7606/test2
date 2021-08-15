package article.service;

import java.util.Map;

import article.model.Writer;

public class WriteRequest {
	private Writer writer;
	private String title;
	private String content;
	public WriteRequest(Writer writer, String title, String content) {
		super();
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
	public Writer getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
		
	public void validate(Map<String, Boolean> errors) {		//유효성 검증 그 객체안에서 메소드 정의해준다.
		if(title==null || title.trim().isEmpty()) {
			System.out.println("title비어있다.");
			errors.put("title", Boolean.TRUE);
		}
	}
}
