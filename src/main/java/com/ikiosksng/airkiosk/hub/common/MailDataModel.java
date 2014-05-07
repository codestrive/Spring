package com.ikiosksng.airkiosk.hub.common;

public class MailDataModel extends DataModel {

	public static class Journey{
		private String fcode;
		private String from;
		private String to;
		private String date;
		public String getFcode() {
			return fcode;
		}
		public void setFcode(String fcode) {
			this.fcode = fcode;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
	}
	public String atmReference,name,pnr;
	public double baseFare;
	public double bohFees;
	public int adultCount;
	public int childCount;
	public int infantCount;
	
	public Journey outbound;
	public Journey inbound;
	public String getAtmReference() {
		return atmReference;
	}
	public void setAtmReference(String atmReference) {
		this.atmReference = atmReference;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public double getBaseFare() {
		return baseFare;
	}
	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}
	public double getBohFees() {
		return bohFees;
	}
	public void setBohFees(double bohFees) {
		this.bohFees = bohFees;
	}
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	public int getInfantCount() {
		return infantCount;
	}
	public void setInfantCount(int infantCount) {
		this.infantCount = infantCount;
	}
	public Journey getOutbound() {
		return outbound;
	}
	public void setOutbound(Journey outbound) {
		this.outbound = outbound;
	}
	public Journey getInbound() {
		return inbound;
	}
	public void setInbound(Journey inbound) {
		this.inbound = inbound;
	}
	
	
	
}
