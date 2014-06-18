package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import next.support.db.ConnectionManager;

public class JdbcTemplate {
	Connection conn;
	
	public JdbcTemplate(){
		this.conn = ConnectionManager.getConnection();
	}
	
	public Object execute (String query, PreparedStatementSetter pss, RowMapper rm) throws SQLException {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Object result = null;
		
		try {
			psmt = this.conn.prepareStatement(query);
			pss.setValues(psmt);
			
			if (rm == null) {
				result = false;
				psmt.execute();
				result = true;
			} else {
				rs = psmt.executeQuery();
				result = rm.mapRow(rs);
			}
			
		} catch (SQLException sqle) {
			System.out.println("Exception " +sqle.getMessage());
			
		} finally {
			rs.close();
			psmt.close();
			conn.close();
		}
		return result;
	}
	
}
