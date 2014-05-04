package codestrive.annotation.applogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SavingAccount implements Account {
	/*
	 * Saving account
	 */
	
	private String rateOfInterest;
	
	/*@Autowired
	public SavingAccount(@Value("5%") String s){
		System.out.println("SavingAccount Constructor Called");
		this.rateOfInterest =s;
	}*/

	public String getRateOfInterest() {
		return rateOfInterest;
	}

	@Autowired
	@Value("${saving.InterestRate}")
	public void setRateOfInterest(String rateOfInterest) {
		System.out.println("saving setter called");
		this.rateOfInterest = rateOfInterest;
	}

	@Override
	public String getInterestRate() {
		System.out.println("In saving account class: getInterestRate");
		String message = "Saving Account: "+ rateOfInterest +" interset per annum";
		return message;
	}

}
