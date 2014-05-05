package com.codestrive.model;

public class State {
	
	private int stateId;
	private String stateName;
	
	public State(int stateId, String stateName){
		setStateId(stateId);
		setStateName(stateName);
	}
	public State(){	
	}
	
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
