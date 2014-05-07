package com.ikiosksng.airkiosk.hub.ws;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ikioskng.airkiosks.ws.types.Airline;
import com.ikioskng.airkiosks.ws.types.AirlinesListRequest;
import com.ikioskng.airkiosks.ws.types.AirlinesListResponse;
import com.ikioskng.airkiosks.ws.types.Airport;
import com.ikioskng.airkiosks.ws.types.AirportsListRequest;
import com.ikioskng.airkiosks.ws.types.AirportsListResponse;


@Endpoint
public class LookupDataService {

	
	@PayloadRoot(namespace=ServiceConstants.NAME_SPACE_URI,localPart="airports-list-request")
	public @ResponsePayload AirportsListResponse getAirPortsList(@RequestPayload AirportsListRequest airportsListRequest) {
		AirportsListResponse result = new AirportsListResponse();
		
		result.getAirport().add(createAirport("ACC","Accra"));
		result.getAirport().add(createAirport("BNI","Benin City"));
		result.getAirport().add(createAirport("CBQ","Calabar"));
		result.getAirport().add(createAirport("ENU","Enugu"));
		result.getAirport().add(createAirport("LOS","Lagos"));
		result.getAirport().add(createAirport("PHC","Port Harcourt (Omagwa Int)"));
		result.getAirport().add(createAirport("PHG","Port Harcourt Military"));
		result.getAirport().add(createAirport("QOW","Owerri"));
		result.getAirport().add(createAirport("ABV","Abuja"));
		result.getAirport().add(createAirport("QUO","Uyo"));
		result.getAirport().add(createAirport("KAN","Kano"));

		return result;
	}
	
	@PayloadRoot(namespace=ServiceConstants.NAME_SPACE_URI,localPart="airlines-list-request")
	public  @ResponsePayload AirlinesListResponse getAirLines(@RequestPayload AirlinesListRequest airlinesListRequest) {
		AirlinesListResponse response = new AirlinesListResponse();
		
		response.getAirline().add(createAirline("JW","Jet Airways"));
		response.getAirline().add(createAirline("KF","King fisher"));
		
		return response;
	}

	private Airline createAirline(String code, String name) {
		Airline airline = new Airline();
		airline.setCode(code);
		airline.setName(name);
		return airline;
	}

	private Airport createAirport(String code, String name) {
		Airport airport = new Airport();
		airport.setCode(code);
		airport.setName(name);
		return airport;
	}
	
}
