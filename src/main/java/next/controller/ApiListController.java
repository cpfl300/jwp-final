package next.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;

public class ApiListController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions;
		
		questions = questionDao.findAll();
		
		PrintWriter out = null;
		out = response.getWriter();
		Gson gson = new Gson();
		out.println(gson.toJson(questions));
		return "api";
	}

}
