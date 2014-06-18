package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {
	public abstract void setValues(PreparedStatement psmt) throws SQLException;
}
