package com.ikiosksng.airkiosk.hub.aero;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.dom4j.io.DocumentResult;
import org.springframework.stereotype.Component;
import org.springframework.xml.transform.StringSource;

import com.ikiosksng.airkiosk.hub.aero.AeroClient.Flight;
import com.mycila.xmltool.CallBack;
import com.mycila.xmltool.XMLDoc;
import com.mycila.xmltool.XMLTag;

@Component
public class AeroRadixxClient extends BaseRadixxClient {

	public static class AirportRoutesReq extends GenericSecurityToken {
		private String languageCode;

		public String getLanguageCode() {
			return languageCode;
		}

		public void setLanguageCode(String languageCode) {
			this.languageCode = languageCode;
		}

	}

	public static class SeatAvailablityReq extends GenericSecurityToken {

		private String logicalFlightID;
		private String departureDate;
		private String utcOffset;
		private String cabinName;
		private String currency;

		public String getLogicalFlightID() {
			return logicalFlightID;
		}

		public void setLogicalFlightID(String logicalFlightID) {
			this.logicalFlightID = logicalFlightID;
		}

		public String getDepartureDate() {
			return departureDate;
		}

		public void setDepartureDate(String departureDate) {
			this.departureDate = departureDate;
		}

		public String getUtcOffset() {
			return utcOffset;
		}

		public void setUtcOffset(String utcOffset) {
			this.utcOffset = utcOffset;
		}

		public String getCabinName() {
			return cabinName;
		}

		public void setCabinName(String cabinName) {
			this.cabinName = cabinName;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

	}

	public static class PNRReq extends GenericSecurityToken {
		private ReservationInfo reservationInfo;
		private String actionType;

		public ReservationInfo getReservationInfo() {
			return reservationInfo;
		}

		public void setReservationInfo(ReservationInfo reservationInfo) {
			this.reservationInfo = reservationInfo;
		}

		public String getActionType() {
			return actionType;
		}

		public void setActionType(String actionType) {
			this.actionType = actionType;
		}

	}

	public static class TravelAgentReq extends GenericSecurityToken {
		private String iATANumber;
		private String username;
		private String password;

		public String getiATANumber() {
			return iATANumber;
		}

		public void setiATANumber(String iATANumber) {
			this.iATANumber = iATANumber;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

	public static class FareQuoteReq extends GenericSecurityToken {
		private String currencyOfFareQuote;
		private String promotionalCode;
		private String iataNumberOfRequestor;
		private String corporationID;
		private String fareFilterMethod;
		private String fareGroupMethod;
		private String inventoryFilterMethod;

		public String getCurrencyOfFareQuote() {
			return currencyOfFareQuote;
		}

		public void setCurrencyOfFareQuote(String currencyOfFareQuote) {
			this.currencyOfFareQuote = currencyOfFareQuote;
		}

		public String getPromotionalCode() {
			return promotionalCode;
		}

		public void setPromotionalCode(String promotionalCode) {
			this.promotionalCode = promotionalCode;
		}

		public String getIataNumberOfRequestor() {
			return iataNumberOfRequestor;
		}

		public void setIataNumberOfRequestor(String iataNumberOfRequestor) {
			this.iataNumberOfRequestor = iataNumberOfRequestor;
		}

		public String getCorporationID() {
			return corporationID;
		}

		public void setCorporationID(String corporationID) {
			this.corporationID = corporationID;
		}

		public String getFareFilterMethod() {
			return fareFilterMethod;
		}

		public void setFareFilterMethod(String fareFilterMethod) {
			this.fareFilterMethod = fareFilterMethod;
		}

		public String getFareGroupMethod() {
			return fareGroupMethod;
		}

		public void setFareGroupMethod(String fareGroupMethod) {
			this.fareGroupMethod = fareGroupMethod;
		}

		public String getInventoryFilterMethod() {
			return inventoryFilterMethod;
		}

		public void setInventoryFilterMethod(String inventoryFilterMethod) {
			this.inventoryFilterMethod = inventoryFilterMethod;
		}

	}

	public static class ReservationInfo {
		private String seriesNumber;
		private String confirmationNumber;

		public String getSeriesNumber() {
			return seriesNumber;
		}

		public void setSeriesNumber(String seriesNumber) {
			this.seriesNumber = seriesNumber;
		}

		public String getConfirmationNumber() {
			return confirmationNumber;
		}

		public void setConfirmationNumber(String confirmationNumber) {
			this.confirmationNumber = confirmationNumber;
		}

	}

	/* Flight enquiry services related request */

	public Source prepareSeatAvailablityReq(SeatAvailablityReq seatAvailablityReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:RetrieveSeatAvailabilityList")
		    .addTag("tem:RetrieveSeatAvailabilityListRequest");
		securityTokenXMLTag(seatAvailablityReq.getSecurityToken(), req);
		req.addNamespace("rad1", flightReqNSURI)
		    .addTag("rad1:LogicalFlightID")
		    .setText(seatAvailablityReq.getLogicalFlightID())
		    .addTag("rad1:DepartureDate")
		    .setText(seatAvailablityReq.getDepartureDate())
		    .addTag("rad1:CabinName")
		    .setText(seatAvailablityReq.getCabinName())
		    .addTag("rad1:Currency")
		    .setText(seatAvailablityReq.getCurrency())
		    .addTag("rad1:UTCOffset")
		    .setText(seatAvailablityReq.getUtcOffset());

		System.out.println(req.toString());
		return req.toSource();
	}

	public Source prepareRetrieveAirportRoutesReq(AirportRoutesReq airportRoutesReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:RetrieveAirportRoutes")
		    .addTag("tem:RetrieveAirportRoutesRequest");
		securityTokenXMLTag(airportRoutesReq.getSecurityToken(), req);
		req.addNamespace("rad1", flightReqNSURI)
		    .addTag("rad1:LanguageCode")
		    .setText(airportRoutesReq.getLanguageCode());

		System.out.println(req.toString());
		return req.toSource();
	}

	/* Travel Agents service related request */

	public Source prepareLoginTravelAgentReq(TravelAgentReq travelAgentReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:LoginTravelAgent")
		    .addTag("tem:LoginTravelAgentRequest");
		securityTokenXMLTag(travelAgentReq.getSecurityToken(), req);
		req.addNamespace("rad1", travelAgentReqNSURI)
		    .addTag("rad1:IATANumber")
		    .setText(travelAgentReq.getiATANumber())
		    .addTag("rad1:UserName")
		    .setText(travelAgentReq.getUsername())
		    .addTag("rad1:Password")
		    .setText(travelAgentReq.getPassword());

		System.out.println(req.toString());
		return req.toSource();
	}

	/* pricing service related request */

	public Source prepareRetrieveFareQuoteReq(FareQuoteReq fareQuoteReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:RetrieveFareQuote")
		    .addTag("tem:RetrieveFareQuoteRequest");
		securityTokenXMLTag(fareQuoteReq.getSecurityToken(), req);
		req.addNamespace("rad1", pricingFareQuoteReqNSURI)
		    .addTag("rad1:CurrencyOfFareQuote")
		    .setText(fareQuoteReq.getCurrencyOfFareQuote())
		    .addTag("rad1:PromotionalCode")
		    .setText(fareQuoteReq.getPromotionalCode())
		    .addTag("rad1:IataNumberOfRequestor")
		    .setText(fareQuoteReq.getIataNumberOfRequestor())
		    .addTag("rad1:CorporationID")
		    .setText(fareQuoteReq.getCorporationID())
		    .addTag("rad1:FareFilterMethod")
		    .setText(fareQuoteReq.getFareFilterMethod())
		    .addTag("rad1:FareGroupMethod")
		    .setText(fareQuoteReq.getFareGroupMethod())
		    .addTag("rad1:InventoryFilterMethod")
		    .setText(fareQuoteReq.getInventoryFilterMethod());

		req = getFareQuoteDetailsTag(req);

		System.out.println(req.toString());
		return req.toSource();
	}

	public static class ServiceQuoteReq extends GenericSecurityToken {

	}

	public Source prepareRetrieveServiceQuoteReq(ServiceQuoteReq serviceQuoteReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:RetrieveServiceQuote")
		    .addTag("tem:RetrieveServiceQuoteRequest")
		    .addNamespace("rad", connectionReqNSURI)
		    .addTag("rad:SecurityGUID")
		    .setText("asasdjk2372382378232")
		    .addTag("rad:CarrierCodes")
		    .addTag("rad:CarrierCode")
		    .addTag("rad:AccessibleCarrierCode")
		    .setText("9J")
		    .gotoParent()
		    .gotoParent()
		    .addTag("rad:ClientIPAddress")
		    .setText("192.168.1.6")
		    .addTag("rad:HistoricUserName")
		    .setText("raj")
		    .addNamespace("rad1", pricingServiceReqNSURI)
		    .addTag("rad1:LogicalFlightID")
		    .setText("Dollar")
		    .addTag("rad1:DepartureDate")
		    .setText("231")
		    .addTag("rad1:AirportCode")
		    .setText("Dollar")
		    .addTag("rad1:ServiceCode")
		    .setText("Dollar")
		    .addTag("rad1:Cabin")
		    .setText("Dollar")
		    .addTag("rad1:Category")
		    .setText("Dollar")
		    .addTag("rad1:Currency")
		    .setText("Dollar")
		    .addTag("rad1:UTCOffset")
		    .setText("Dollar")
		    .addTag("rad1:OperatingCarrierCode")
		    .setText("Dollar")
		    .addTag("rad1:MarketingCarrierCode")
		    .setText("Dollar")
		    .addTag("rad1:FareClass")
		    .setText("Dollar")
		    .addTag("rad1:FareBasisCode")
		    .setText("Dollar")
		    .addTag("rad1:ReservationChannel")
		    .setText("Dollar");

		System.out.println(req.toString());
		return req.toSource();
	}

	public Source prepareCreatePNRReq(PNRReq pNRReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:CreatePNR")
		    .addTag("tem:CreatePnrRequest");
		securityTokenXMLTag(pNRReq.getSecurityToken(), req);
		req.addNamespace("rad1", reservationReqNSURI)
		    .addTag("rad1:ActionType")
		    .setText(pNRReq.getActionType())
		    .addTag("rad1:ReservationInfo")
		    .addTag("rad:SeriesNumber")
		    .setText(pNRReq.getReservationInfo().getSeriesNumber())
		    .addTag("rad:ConfirmationNumber")
		    .setText(pNRReq.getReservationInfo().getConfirmationNumber());

		System.out.println(req.toString());
		return req.toSource();
	}

	public Source prepareRetrievePNRReq(PNRReq pNRReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:RetrievePNR")
		    .addTag("tem:RetrievePnrRequest");
		securityTokenXMLTag(pNRReq.getSecurityToken(), req);
		req.addNamespace("rad1", reservationReqNSURI)
		    .addTag("rad1:ActionType")
		    .setText(pNRReq.getActionType())
		    .addTag("rad1:ReservationInfo")
		    .addTag("rad:SeriesNumber")
		    .setText(pNRReq.getReservationInfo().getSeriesNumber())
		    .addTag("rad:ConfirmationNumber")
		    .setText(pNRReq.getReservationInfo().getConfirmationNumber());

		System.out.println(req.toString());
		return req.toSource();
	}

	public Source prepareCancelPNRReq(PNRReq pNRReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:CancelPNR")
		    .addTag("tem:CancelPnrRequest");
		securityTokenXMLTag(pNRReq.getSecurityToken(), req);
		req.addNamespace("rad1", reservationReqNSURI)
		    .addTag("rad1:ActionType")
		    .setText(pNRReq.getActionType())
		    .addTag("rad1:ReservationInfo")
		    .addTag("rad:SeriesNumber")
		    .setText(pNRReq.getReservationInfo().getSeriesNumber())
		    .addTag("rad:ConfirmationNumber")
		    .setText(pNRReq.getReservationInfo().getConfirmationNumber());

		System.out.println(req.toString());
		return req.toSource();
	}

	public static class ModifyPNRReq extends GenericSecurityToken {

	}

	public Source prepareModifyPNRReq(ModifyPNRReq modifyPNRReq) {
		XMLTag req = XMLDoc
		    .newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:ModifyPNR")
		    .addTag("tem:ModifyPnrRequest");

		securityTokenXMLTag(modifyPNRReq.getSecurityToken(), req);

		req.addNamespace("rad1", reservationReqNSURI)
		    .addTag("rad1:ActionType")
		    .setText("Dollar")
		    .addTag("rad1:ReservationInfo")
		    .addTag("rad:SeriesNumber")
		    .setText("Dollar")
		    .addTag("rad:ConfirmationNumber")
		    .setText("Dollar")
		    .gotoParent()
		    .addTag("rad1:FareInformationID")
		    .setText("Dollar")
		    .addTag("rad1:LogicalFlightKeyToReplace")
		    .setText("Dollar")
		    .addTag("rad1:LogicalFlightKeyToRemove")
		    .setText("Dollar")
		    .addTag("rad1:PersonOrgIdentifierToRemove")
		    .setText("Dollar")
		    .addTag("rad1:PassengersToAdd")

		    /* <!--Zero or more repetitions:--> */
		    .addTag("rad1:ModifyPNR.Passenger")
		    //
		    .addTag("rad2:PersonOrgID")
		    .setText("Dollar")
		    .addTag("rad2:FirstName")
		    .setText("Dollar")
		    .addTag("rad2:LastName")
		    .setText("Dollar")
		    .addTag("rad2:MiddleName")
		    .setText("Dollar")
		    .addTag("rad2:Age")
		    .setText("Dollar")
		    .addTag("rad2:DOB")
		    .setText("Dollar")
		    .addTag("rad2:Gender")
		    .setText("Dollar")
		    .addTag("rad2:Title")
		    .setText("Dollar")
		    .addTag("rad2:NationalityLaguageID")
		    .setText("Dollar")
		    .addTag("rad2:RelationType")
		    .setText("Dollar")
		    .addTag("rad2:WBCID")
		    .setText("Dollar")
		    .addTag("rad2:PTCID")
		    .setText("Dollar").addTag("rad2:PTC")
		    .setText("Dollar")
		    .addTag("rad2:TravelsWithPersonOrgID")
		    .setText("Dollar")
		    .addTag("rad2:RedressNumber")
		    .setText("Dollar")
		    .addTag("rad2:KnownTravelerNumber")
		    .setText("Dollar")
		    .addTag("rad2:MarketingOptIn")
		    .setText("Dollar")
		    .addTag("rad2:UseInventory")
		    .setText("Dollar")
		    .addTag("rad2:Address")
		    .addTag("rad2:Address1")
		    .setText("Dollar")
		    .addTag("rad2:Address2")
		    .setText("Dollar")
		    .addTag("rad2:City")
		    .setText("Dollar")
		    .addTag("rad2:State")
		    .setText("Dollar")
		    .addTag("rad2:Postal")
		    .setText("Dollar")
		    .addTag("rad2:Country")
		    .setText("Dollar")
		    .addTag("rad2:CountryCode")
		    .setText("Dollar")
		    .addTag("rad2:AreaCode")
		    .setText("Dollar")
		    .addTag("rad2:PhoneNumber")
		    .setText("Dollar")
		    .addTag("rad2:Display")
		    .setText("Dollar")
		    .gotoParent()
		    .addTag("rad2:Company")
		    .setText("Dollar")
		    .addTag("rad2:Comments")
		    .setText("Dollar")
		    .addTag("rad2:Passport")
		    .setText("Dollar")
		    .addTag("rad2:Nationality")
		    .setText("Dollar")
		    .addTag("rad2:ProfileId")
		    .setText("Dollar")
		    .addTag("rad2:IsPrimaryPassenger")
		    .setText("Dollar")
		    .addTag("rad2:ContactInfos")

		    // <!--Zero or more repetitions:-->

		    .addTag("rad2:ContactInfo")
		    .addTag("rad2:ContactID")
		    .setText("Dollar")
		    .addTag("rad2:PersonOrgID")
		    .setText("Dollar")
		    .addTag("rad2:ContactField")
		    .setText("Dollar")
		    .addTag("rad2:ContactType")
		    .setText("Dollar")
		    .addTag("rad2:Extension")
		    .setText("Dollar")
		    .addTag("rad2:CountryCode")
		    .setText("Dollar")
		    .addTag("rad2:AreaCode")
		    .setText("Dollar")
		    .addTag("rad2:PhoneNumber")
		    .setText("Dollar")
		    .addTag("rad2:Display")
		    .setText("Dollar")
		    .addTag("rad2:PreferredContactMethod")
		    .setText("Dollar")
		    .gotoParent()
		    .gotoParent()
		    .gotoParent();

		System.out.println(req.toString());
		return req.toSource();
	}

	public static class PaymentReq extends GenericSecurityToken {
		private String seriesNumber, confirmationNumber;
	}

	public Source preparePaymentReq(PaymentReq paymentReq) {
		XMLTag req = XMLDoc.newDocument(false)
		    .addNamespace("tem", "http://tempuri.org/")
		    .addRoot("tem:InsertExternalProcessedPayment")
		    .addTag("tem:ExternalProcessedPaymentRequest")
		    .addNamespace("rad1", fulfillmentNSURI)
		    .addTag("rad1:TransactionInfo");
		securityTokenXMLTag(paymentReq.getSecurityToken(), req);
		req.gotoParent()
		    .addTag("rad1:ReservationInfo")
		    .addTag("rad:SeriesNumber")
		    .setText("Dollar")
		    .addTag("rad:ConfirmationNumber")
		    .setText("Dollar")
		    .gotoParent()
		    .addTag("rad1:ExternalPayments")
		    .addTag("rad1:InsertExternalProcessedPayment")
		    .addTag("rad1:BaseAmount")
		    .setText("Dollar")
		    .addTag("rad1:BaseCurrency")
		    .setText("Dollar")
		    .addTag("rad1:CardHolder")
		    .setText("Dollar")// {InsertExternalProcessedPayment-BaseAmount,BaseCurrency,CardHolder
		                      // ,CardNumber,CheckNumber,CurrencyPaid,CVCode,DatePaid}
		    .addTag("rad1:CardNumber")
		    .setText("Dollar")
		    .addTag("rad1:CheckNumber")
		    .setText("Dollar")
		    .addTag("rad1:CurrencyPaid")
		    .setText("Dollar")
		    .addTag("rad1:CVCode")
		    .setText("Dollar")
		    .addTag("rad1:DatePaid")
		    .setText("Dollar")
		    .addTag("rad1:DocumentReceivedBy")
		    .setText("Dollar")
		    .addTag("rad1:ExpirationDate")
		    .setText("Dollar")
		    .addTag("rad1:ExchangeRate")
		    .setText("Dollar")
		    .addTag("rad1:ExchangeRateDate")
		    .setText("Dollar")
		    .addTag("rad1:FFNumber")
		    .setText("Dollar")
		    .addTag("rad1:PaymentComment")
		    .setText("Dollar")
		    .addTag("rad1:PaymentAmount")
		    .setText("Dollar")
		    .addTag("rad1:PaymentMethod")
		    .setText("Dollar")
		    .addTag("rad1:Reference")
		    .setText("Dollar")
		    .addTag("rad1:TerminalID")
		    .setText("Dollar")
		    .addTag("rad1:UserData")
		    .setText("Dollar")
		    .addTag("rad1:UserID")
		    .setText("Dollar")
		    .addTag("rad1:IataNumber")
		    .setText("Dollar")
		    .addTag("rad1:ValueCode")
		    .setText("Dollar")

		    .addTag("rad1:VoucherNumber")
		    .setText("Dollar")
		    .addTag("rad1:IsTACreditCard")
		    .setText("Dollar")
		    .addTag("rad1:GcxID")
		    .setText("Dollar")

		    .addTag("rad1:GcxOptOption")
		    .setText("Dollar")
		    .addTag("rad1:OriginalCurrency")
		    .setText("Dollar")
		    .addTag("rad1:OriginalAmount")
		    .setText("Dollar")
		    .addTag("rad1:TransactionStatus")
		    .setText("Dollar")
		    .addTag("rad1:AuthorizationCode")
		    .setText("Dollar")
		    .addTag("rad1:PaymentReference")
		    .setText("Dollar")
		    .addTag("rad1:ResponseMessage")
		    .setText("Dollar")
		    .addTag("rad1:CardCurrency")
		    .setText("Dollar")
		    .addTag("rad1:BillingCountry")
		    .setText("Dollar")
		    .addTag("rad1:FingerPrintingSessionID")
		    .setText("Dollar")
		    .addTag("rad1:Payor")// {Person-PersonOrgID,FirstName,LastName,MiddleName,age,dob,gender,title
		                         // NationalityLaguageID,RelationType,RelationType,PTCID,PTC,TravelsWithPersonOrgID,
		                         // RedressNumber,KnownTravelerNumber,MarketingOptIn,userInventory
		                         // }
		    .addNamespace("rad2", reservationReqNSURI)
		    .addTag("rad2:PersonOrgID")
		    .setText("Dollar")
		    .addTag("rad2:FirstName")
		    .setText("Dollar")
		    .addTag("rad2:LastName")
		    .setText("Dollar")
		    .addTag("rad2:MiddleName")
		    .setText("Dollar")
		    .addTag("rad2:Age")
		    .setText("Dollar")
		    .addTag("rad2:DOB")
		    .setText("Dollar")
		    .addTag("rad2:Gender")
		    .setText("Dollar")
		    .addTag("rad2:Title")
		    .setText("Dollar")
		    .addTag("rad2:NationalityLaguageID")
		    .setText("Dollar")
		    .addTag("rad2:RelationType")
		    .setText("Dollar")
		    .addTag("rad2:WBCID")
		    .setText("Dollar")
		    .addTag("rad2:PTCID")
		    .setText("Dollar")
		    .addTag("rad2:PTC")
		    .setText("Dollar")
		    .addTag("rad2:TravelsWithPersonOrgID")
		    .setText("Dollar")
		    .addTag("rad2:RedressNumber")
		    .setText("Dollar")
		    .addTag("rad2:KnownTravelerNumber")
		    .setText("Dollar")
		    .addTag("rad2:MarketingOptIn")
		    .setText("Dollar")
		    .addTag("rad2:UseInventory")
		    .setText("Dollar")
		    .addTag("rad2:Address")// {Address -
		                           // address1,address2,city,state,postal,country,countryCode,areaCode,phoneNumber,display}
		    .addTag("rad2:Address1")
		    .setText("Dollar")
		    .addTag("rad2:Address2")
		    .setText("Dollar")
		    .addTag("rad2:City")
		    .setText("Dollar")
		    .addTag("rad2:State")
		    .setText("Dollar")
		    .addTag("rad2:Postal")
		    .setText("Dollar")
		    .addTag("rad2:Country")
		    .setText("Dollar")
		    .addTag("rad2:CountryCode")
		    .setText("Dollar")
		    .addTag("rad2:AreaCode")
		    .setText("Dollar")
		    .addTag("rad2:PhoneNumber")
		    .setText("Dollar")
		    .addTag("rad2:Display")
		    .setText("Dollar")
		    .gotoParent()
		    .addTag("rad2:Company")
		    .setText("Dollar")
		    .addTag("rad2:Comments")
		    .setText("Dollar")
		    .addTag("rad2:Passport")
		    .setText("Dollar")
		    .addTag("rad2:Nationality")
		    .setText("Dollar")
		    .addTag("rad2:ProfileId")
		    .setText("Dollar")
		    .addTag("rad2:IsPrimaryPassenger")
		    .setText("Dollar")
		    .addTag("rad2:ContactInfos")

		    // <!--Zero or more repetitions:-->

		    .addTag("rad2:ContactInfo")
		    .addTag("rad2:ContactID")
		    .setText("Dollar")
		    .addTag("rad2:PersonOrgID")
		    .setText("Dollar")
		    .addTag("rad2:ContactField")
		    .setText("Dollar")
		    .addTag("rad2:ContactType")
		    .setText("Dollar")
		    .addTag("rad2:Extension")
		    .setText("Dollar")
		    .addTag("rad2:CountryCode")
		    .setText("Dollar")
		    .addTag("rad2:AreaCode")
		    .setText("Dollar")
		    .addTag("rad2:PhoneNumber")
		    .setText("Dollar")
		    .addTag("rad2:Display")
		    .setText("Dollar")
		    .addTag("rad2:PreferredContactMethod")
		    .setText("Dollar")
		    .gotoParent()
		    .gotoParent()
		    .gotoParent()
		    // ---------
		    // optionals- starts
		    .addTag("rad1:Result")
		    .setText("Dollar")
		    .addTag("rad1:TransactionID")
		    .setText("Dollar")
		    .addTag("rad1:ResponseCode")
		    .setText("Dollar")
		    .addTag("rad1:AncillaryData01")
		    .setText("Dollar")
		    .addTag("rad1:AncillaryData02")
		    .setText("Dollar")
		    .addTag("rad1:AncillaryData03")
		    .setText("Dollar")
		    .addTag("rad1:AncillaryData04")
		    .setText("Dollar")
		    .addTag("rad1:AncillaryData05")
		    .setText("Dollar")
		    // optionals- End
		    .addTag("rad1:MerchantID")
		    .setText("Dollar");

		System.out.println(req.toString());
		return req.toSource();
	}

	private XMLTag getFareQuoteDetailsTag(XMLTag req) {
		req.addTag("rad1:FareQuoteDetails")
		    .addTag("rad1:FareQuoteDetail")
		    .addTag("rad1:Origin")
		    .setText("Dollar")
		    .addTag("rad1:Destination")
		    .setText("Dollar")
		    .addTag("rad1:UseAirportsNotMetroGroups")
		    .setText("Dollar")
		    .addTag("rad1:DateOfDeparture")
		    .setText("Dollar")
		    .addTag("rad1:FareTypeCategory")
		    .setText("Dollar")
		    .addTag("rad1:FareClass")
		    .setText("Dollar")
		    .addTag("rad1:FareBasisCode")
		    .setText("Dollar")
		    .addTag("rad1:Cabin")
		    .setText("Dollar")
		    .addTag("rad1:LFID")
		    .setText("Dollar")
		    .addTag("rad1:OperatingCarrierCode")
		    .setText("Dollar")
		    .addTag("rad1:MarketingCarrierCode")
		    .setText("Dollar")
		    .addTag("rad1:NumberOfDaysBefore")
		    .setText("Dollar")
		    .addTag("rad1:NumberOfDaysAfter")
		    .setText("Dollar")
		    .addTag("rad1:LanguageCode")
		    .setText("Dollar")
		    .addTag("rad1:TicketPackageID")
		    .setText("Dollar")
		    .addTag("rad1:FareQuoteRequestInfos")
		    .addTag("rad1:FareQuoteRequestInfo")
		    .addTag("rad1:PassengerTypeID")
		    .setText("Dollar")
		    .addTag("rad1:TotalSeatsRequired")
		    .setText("Dollar");
		return req;
	}

	// TODO webservice requests and retrieve response

	public List<Flight> availabilityRequest(final SeatAvailablityReq req) {
		return execute(new SessionCallBack<List<Flight>>() {

			@Override
			public List<Flight> doInSession(String securityGUID) {
				DocumentResult response = new DocumentResult();
				req.getSecurityToken().setSecurityGUID(securityGUID);
				
				webserviceCall(prepareSeatAvailablityReq(req), response, flightEndpointURL, retrieveSeatAvailabilitySA);
			// TODO Extract response data from response and return making appropriate List<Flight> object.
				System.out.println(response.getDocument().asXML());

				return null;
			}
		});
	}

	public List<AirportRoute> getAirportRoutes(final AirportRoutesReq req) {
		return execute(new SessionCallBack<List<AirportRoute>>() {

			@Override
			public List<AirportRoute> doInSession(String securityGUID) {
				DocumentResult response = new DocumentResult();
				req.getSecurityToken().setSecurityGUID(securityGUID);
				webserviceCall(prepareRetrieveAirportRoutesReq(req), response, flightEndpointURL, retrieveAirportRoutesSA);
				System.out.println(response.getDocument().asXML());
				final List<AirportRoute> result = new ArrayList<AirportRoute>();
				extractAirportRoutes(new StringSource(response.getDocument().asXML()), result);
				return result;
			}

		});
	}

	private void extractAirportRoutes(Source response,
	    final List<AirportRoute> result) {
		XMLDoc.from(response, true)
		    .addNamespace("a", flightResNSURI)
		    .gotoChild("RetrieveAirportRoutesResult")
		    .gotoChild("a:Routes")
		    .forEachChild(new CallBack() {

			    @Override
			    public void execute(XMLTag route) {
				    result.add(extractRoute(route));
			    }

		    });

	}

	private AirportRoute extractRoute(XMLTag route) {
		AirportRoute airportRoute = new AirportRoute();
		airportRoute.setOriginAirportCode(route.gotoChild("a:OriginAirportCode").getText());
		airportRoute.setOriginAirportDescription(route.gotoParent().gotoChild("a:OriginAirportDescription").getText());
		airportRoute.setOriginAirportMetroGroup(route.gotoParent().gotoChild("a:OriginAirportMetroGroup").getText());
		airportRoute.setOriginAirportCurrency(route.gotoParent().gotoChild("a:OriginAirportCurrency").getText());

		airportRoute.setDestinationAirportCode(route.gotoParent().gotoChild("a:DestinationAirportCode").getText());
		airportRoute.setDestinationAirportDescription(route.gotoParent().gotoChild("a:DestinationAirportDescription")
		    .getText());
		airportRoute.setDestinationAirportMetroGroup(route.gotoParent().gotoChild("a:DestinationAirportMetroGroup")
		    .getText());
		airportRoute.setDestinationAirportCurrency(route.gotoParent().gotoChild("a:DestinationAirportCurrency").getText());

		airportRoute.setApproximateRouteDuration(new
		    Long(route.gotoParent().gotoChild("a:ApproximateRouteDuration").getText()));
		airportRoute.setAdvPaxInfoCollectionReq(new
		    Boolean(route.gotoParent().gotoChild("a:AdvPaxInfoCollectionReq").getText()));
		airportRoute.setOriginAirportOffsetFromGMT(new
		    Boolean(route.gotoParent().gotoChild("a:OriginAirportOffsetFromGMT").getText()));

		airportRoute.setOriginAirportISOCountryCode3(route.gotoParent().gotoChild("a:OriginAirportISOCountryCode3")
		    .getText());
		airportRoute.setDestinationAirportISOCountryCode3(route.gotoParent()
		    .gotoChild("a:DestinationAirportISOCountryCode3").getText());
		return airportRoute;
	}

	/* Response POJOs */

	public static class AirportRoute {

		private String originAirportCode;
		private String originAirportDescription;
		private String originAirportMetroGroup;
		private String originAirportCurrency;
		private String destinationAirportCode;
		private String destinationAirportDescription;
		private String destinationAirportMetroGroup;
		private String destinationAirportCurrency;
		private Long approximateRouteDuration;
		private Boolean advPaxInfoCollectionReq;
		private Boolean originAirportOffsetFromGMT;
		private String originAirportISOCountryCode3;
		private String destinationAirportISOCountryCode3;

		public String getOriginAirportCode() {
			return originAirportCode;
		}

		public void setOriginAirportCode(String originAirportCode) {
			this.originAirportCode = originAirportCode;
		}

		public String getOriginAirportDescription() {
			return originAirportDescription;
		}

		public void setOriginAirportDescription(String originAirportDescription) {
			this.originAirportDescription = originAirportDescription;
		}

		public String getOriginAirportMetroGroup() {
			return originAirportMetroGroup;
		}

		public void setOriginAirportMetroGroup(String originAirportMetroGroup) {
			this.originAirportMetroGroup = originAirportMetroGroup;
		}

		public String getOriginAirportCurrency() {
			return originAirportCurrency;
		}

		public void setOriginAirportCurrency(String originAirportCurrency) {
			this.originAirportCurrency = originAirportCurrency;
		}

		public String getDestinationAirportCode() {
			return destinationAirportCode;
		}

		public void setDestinationAirportCode(String destinationAirportCode) {
			this.destinationAirportCode = destinationAirportCode;
		}

		public String getDestinationAirportDescription() {
			return destinationAirportDescription;
		}

		public void setDestinationAirportDescription(String destinationAirportDescription) {
			this.destinationAirportDescription = destinationAirportDescription;
		}

		public String getDestinationAirportMetroGroup() {
			return destinationAirportMetroGroup;
		}

		public void setDestinationAirportMetroGroup(String destinationAirportMetroGroup) {
			this.destinationAirportMetroGroup = destinationAirportMetroGroup;
		}

		public String getDestinationAirportCurrency() {
			return destinationAirportCurrency;
		}

		public void setDestinationAirportCurrency(String destinationAirportCurrency) {
			this.destinationAirportCurrency = destinationAirportCurrency;
		}

		public Long getApproximateRouteDuration() {
			return approximateRouteDuration;
		}

		public void setApproximateRouteDuration(Long approximateRouteDuration) {
			this.approximateRouteDuration = approximateRouteDuration;
		}

		public Boolean getAdvPaxInfoCollectionReq() {
			return advPaxInfoCollectionReq;
		}

		public void setAdvPaxInfoCollectionReq(Boolean advPaxInfoCollectionReq) {
			this.advPaxInfoCollectionReq = advPaxInfoCollectionReq;
		}

		public Boolean getOriginAirportOffsetFromGMT() {
			return originAirportOffsetFromGMT;
		}

		public void setOriginAirportOffsetFromGMT(Boolean originAirportOffsetFromGMT) {
			this.originAirportOffsetFromGMT = originAirportOffsetFromGMT;
		}

		public String getOriginAirportISOCountryCode3() {
			return originAirportISOCountryCode3;
		}

		public void setOriginAirportISOCountryCode3(String originAirportISOCountryCode3) {
			this.originAirportISOCountryCode3 = originAirportISOCountryCode3;
		}

		public String getDestinationAirportISOCountryCode3() {
			return destinationAirportISOCountryCode3;
		}

		public void setDestinationAirportISOCountryCode3(String destinationAirportISOCountryCode3) {
			this.destinationAirportISOCountryCode3 = destinationAirportISOCountryCode3;
		}

		@Override
		public String toString() {
			return "AirportRoute [originAirportCode=" + originAirportCode + ", originAirportDescription="
			    + originAirportDescription + ", originAirportMetroGroup=" + originAirportMetroGroup
			    + ", originAirportCurrency=" + originAirportCurrency + ", destinationAirportCode=" + destinationAirportCode
			    + ", destinationAirportDescription=" + destinationAirportDescription + ", destinationAirportMetroGroup="
			    + destinationAirportMetroGroup + ", destinationAirportCurrency=" + destinationAirportCurrency
			    + ", approximateRouteDuration=" + approximateRouteDuration + ", advPaxInfoCollectionReq="
			    + advPaxInfoCollectionReq + ", originAirportOffsetFromGMT=" + originAirportOffsetFromGMT
			    + ", originAirportISOCountryCode3=" + originAirportISOCountryCode3 + ", destinationAirportISOCountryCode3="
			    + destinationAirportISOCountryCode3 + "]";
		}
	}
}
