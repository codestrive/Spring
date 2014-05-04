package codestrive.annotation.applogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class FixedAccount implements Account {
	/*
	 * Fixed account
	 */
	private String rateOfInterest;
	
	@Autowired
	public FixedAccount(@Value("10%") String s){
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
