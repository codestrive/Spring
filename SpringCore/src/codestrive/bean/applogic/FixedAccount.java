package codestrive.bean.applogic;

public class FixedAccount implements Account {
	/*
	 * Fixed account
	 */
	private String rateOfInterest;
	
	public FixedAccount(String s){
		System.out.println("FixedAccount Constructor Called");
		this.rateOfInterest =s;
	}
	
	@Override
	public String getInterestRate() {
		System.out.println("In fixed account class: getInterestRate");
		String message = "Fixed Account: "+ rateOfInterest +" interset per annum";
		return message;
	}

}
