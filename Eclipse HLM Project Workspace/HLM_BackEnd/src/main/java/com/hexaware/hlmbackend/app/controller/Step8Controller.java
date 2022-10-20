package com.hexaware.hlmbackend.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.hlmbackend.app.model.Customer;
import com.hexaware.hlmbackend.app.model.LoanAgreement;
import com.hexaware.hlmbackend.app.serviceinterface.HomeLoanServiceInterface;

@RestController
@CrossOrigin("*")
@RequestMapping("/step8Api")
public class Step8Controller {

	@Autowired
	private HomeLoanServiceInterface hlsi;
	
	@PostMapping(value = "/postStep8")
	public String InsertStep7Data(@RequestPart String customerApplication,@PathVariable Integer savedCustomerId) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper om = new ObjectMapper(); 
		Customer cla = om.readValue(customerApplication, Customer.class);
		
		Customer c = new Customer();
		
		//Fetching customer from Database
		Customer savedCustomer = hlsi.getSavedCustomer(savedCustomerId);
		
		//****************************
		//Cut pasted from step7(now it is step5Point5)
		c.setCustomerAcceptanceStatus(cla.getCustomerAcceptanceStatus());
		//*****************************
		
		c.setLoanAgreementStatus(cla.getLoanAgreementStatus());
		
		LoanAgreement la = savedCustomer.getLoanAgreement();
		
		la.setLoanAgreementName(cla.getLoanAgreement().getApplicantName());
		la.setApplicantName(cla.getLoanAgreement().getApplicantName());
		la.setContactDetails(cla.getLoanAgreement().getContactDetails());
		la.setLoanAmountSanctioned(cla.getLoanAgreement().getLoanAmountSanctioned());
		la.setInterestType(cla.getLoanAgreement().getInterestType());
		la.setRateOfInterest(cla.getLoanAgreement().getRateOfInterest());
		la.setLoanTenure(cla.getLoanAgreement().getLoanTenure());
		la.setMonthlyEmiAmount(cla.getLoanAgreement().getMonthlyEmiAmount());
		la.setModeOfPayment(cla.getLoanAgreement().getModeOfPayment());
		la.setRemarks(cla.getLoanAgreement().getRemarks());
		la.setStatus(cla.getLoanAgreement().getStatus());
		
		c.setLoanAgreement(la);
		
		hlsi.insertStep8Data(c);
		
		return "Step8 Saved";
	}
}
