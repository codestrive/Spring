package codestrive.annotation.applogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component(value="ppfAccount")
@Lazy
@Configuration
@PropertySource("classpath:config.properties")
public class PPFAccount implements Account {

	/*
	 * PPF account
	 */
	@Autowired
	Environment env;

	private String rateOfInterest;
	
	@Autowired
	public PPFAccount(@Value("5%") String s){
		System.out.println("PPFAccount Constructor Called");
		//System.out.println("test :"+env.getProperty("ppf.InterestRate"));
		this.rateOfInterest =s;
	}
	
	@Override
	public String getInterestRate() {
		System.out.println("In ppf account class: getInterestRate");
		String message = "PPF Account: "+ rateOfInterest +" interset per annum";
		return message;
	}

}
