package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import core.mvc.Controller;

public class CommentController implements Controller {

	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao(); 
		
		String questionId = request.getParameter("questionId");
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		
		Answer answer = new Answer(writer, contents, Integer.parseInt(questionId));
		answerDao.insert(answer);
		
		Question question = questionDao.findById(Integer.parseInt(questionId));
		questionDao.updateComCount(question);
		
		return "api";
	}

}
