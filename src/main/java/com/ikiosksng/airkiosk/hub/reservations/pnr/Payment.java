package com.ikiosksng.airkiosk.hub.reservations.pnr;

//<PAY FOP="CCTYPE=INVOICE" CC_type="INVOICE" Auth_code="INVOICE"
//Currency="NGN" Amount="32759.3" AGT_REF="" CC_fee="410.29" SERVICEFEE="789.01" 
//Agent_paid="BOSRCAJ.4104.7201078856624851.12080412024515403" />

public class Payment {

	private String fop;
	private String ccType;
	private String authCode;
	private String currency;
	private String amount;
	private String agtRef;
	private String ccFee;
	private String serviceFee;
	private String agent;
	public String getFop() {
		return fop;
	}
	public String getCcType() {
		return ccType;
	}
	public String getAuthCode() {
		return authCode;
	}
	public String getCurrency() {
		return currency;
	}
	public String getAmount() {
		return amount;
	}
	public String getAgtRef() {
		return agtRef;
	}
	public String getCcFee() {
		return ccFee;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public String getAgent() {
		return agent;
	}
	public void setFop(String fop) {
		this.fop = fop;
	}
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setAgtRef(String agtRef) {
		this.agtRef = agtRef;
	}
	public void setCcFee(String ccFee) {
		this.ccFee = ccFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	
	
}
