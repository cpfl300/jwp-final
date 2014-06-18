package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import next.model.Question;
import next.support.db.ConnectionManager;

public class QuestionDao {

	public void insert(Question question) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, question.getWriter());
			pstmt.setString(2, question.getTitle());
			pstmt.setString(3, question.getContents());
			pstmt.setTimestamp(4,
					new Timestamp(question.getTimeFromCreateDate()));
			pstmt.setInt(5, question.getCountOfComment());

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	public void updateComCount(Question question) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "update QUESTIONS set countOfComment = ? where questionId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, question.getCountOfComment() + 1);
			pstmt.setInt(2, (int) question.getQuestionId());
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	public List<Question> findAll() throws SQLException {
		JdbcTemplate t = new JdbcTemplate();
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement psmt) throws SQLException {
			}
		};

		RowMapper mapper = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				List<Question> questions = new ArrayList<Question>();
				while (rs.next()) {
					Question question = new Question(rs.getLong("questionId"),
							rs.getString("writer"), 
							rs.getString("title"),
							rs.getString("contents"),
							rs.getTimestamp("createdDate"),
							rs.getInt("countOfComment"));
					questions.add(question);
				}
				return questions;
			}
		};
		
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
					+ "order by questionId desc";
		
		return (List<Question>)t.execute(sql, pss, mapper);
	}

	public Question findById(final long questionId) throws SQLException {
		JdbcTemplate t = new JdbcTemplate();
		PreparedStatementSetter pss = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement psmt) throws SQLException {
				psmt.setLong(1, questionId);

			}
		};

		RowMapper mapper = new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				Question question = null;
				while (rs.next()) {
					question = new Question(rs.getLong("questionId"),
							rs.getString("writer"), rs.getString("title"),
							rs.getString("contents"),
							rs.getTimestamp("createdDate"),
							rs.getInt("countOfComment"));
				}
				return question;
			}
		};

		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";
		
		return (Question)t.execute(sql, pss, mapper);
	}

}
