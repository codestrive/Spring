package com.codestrive.jdbcDAOimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.codestrive.model.State;

@Component
public class JdbcDAOImpl {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate; 
	
	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/*public State getState(int stateId){
		
		 State state = null;
		 
         Connection conn = null;
         try {        
        	 	
                conn =  dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement("select * from state where state_id=?");
                ps.setInt(1, stateId);
                
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                      state = new State(stateId, rs.getString("state_name"));
                }
                rs.close();
                ps.close();
             }
         catch(Exception e){
                e.printStackTrace();
         }
         finally {
        	 try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
         }
         
         return state;
	}*/
	
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

	
	public void insertState(State state){
		String sql ="Insert into state(state_id,state_name) values(?,?)";
		jdbcTemplate.update(sql, new Object[] {state.getStateId(),state.getStateName()});
	}
	
	
	
	private static final class StateMapper implements RowMapper<State>{

		@Override
		public State mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			State state = new State();
			state.setStateId(resultSet.getInt("state_id"));
			state.setStateName(resultSet.getString("state_name"));
			return state;
		}
		
		
	}
}
