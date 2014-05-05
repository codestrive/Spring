package com.codestrive.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.codestrive.model.State;


public class StateMapper implements RowMapper<State>{

	@Override
	public State mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		State state = new State();
		state.setStateId(resultSet.getInt("state_id"));
		state.setStateName(resultSet.getString("state_name"));
		return state;
	}
	
}
