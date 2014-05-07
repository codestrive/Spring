package com.codestrive.jdbcDAOimpl;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.codestrive.model.State;
import com.codestrive.model.mapper.StateMapper;

@Component
public class JdbcDAOImpl {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate; 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}	
	
	public int getStateCount(){
		String sql = "SELECT COUNT(*) FROM STATE";
		return jdbcTemplate.queryForInt(sql);
	}
	
	public String getStateName(int stateId){
		String sql ="SELECT STATE_NAME FROM STATE where state_id = ?";
		return jdbcTemplate.queryForObject(sql,new Object[] {stateId}, String.class);
	}
	
	public State getStateforId(int stateId){
		String sql ="SELECT * FROM STATE where state_id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {stateId},new StateMapper());
	}
	
	public List<State> getAllState(){
		String sql ="SELECT * FROM STATE";
		return jdbcTemplate.query(sql, new StateMapper());
	}
	
	public int getStateNameNamedPara(State state){
		String sql ="insert into state values(:id,:name)";
		
		SqlParameterSource namedPara = 
				new MapSqlParameterSource("id", state.getStateId()).
									addValue("name", state.getStateName());

				
		return namedParameterJdbcTemplate.update(sql, namedPara);
	}
	
	public void insertState(State state){
		String sql ="Insert into state(state_id,state_name) values(?,?)";
		System.out.println("insert val:"+jdbcTemplate.update(sql,
				new Object[] {state.getStateId(),state.getStateName()}));
	}
	
	
	
	public void deleteState(int stateId){
		String sql="delete from state where state_id =?";
		jdbcTemplate.update(sql, new Object[] {stateId});
	}
	
	
}
