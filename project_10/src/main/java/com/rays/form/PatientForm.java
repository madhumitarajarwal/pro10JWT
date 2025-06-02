package com.rays.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.PatientDTO;
import com.rays.validation.ValidDate;

public class PatientForm extends BaseForm {

	@Pattern(regexp = "^[A-Za-z]+([ '-][A-Za-z]+)*$", message = "Name contains alphabets only")
	@NotEmpty(message = "Name is required")
	private String name;

	@NotNull(message = "Please Enter dateOfVisit")
	@ValidDate(message = "Invalid date format or value")
	private String dateOfVisit;

	@NotEmpty(message = "mobile is Required")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile No is Invalid")
	private String mobile = null;

	
	//@Pattern(regexp = "^[A-Za-z]+([ '-][A-Za-z]+)*$", message = "Decease contains alphabets only")
	@NotEmpty(message = "Decease is required")
	private String decease = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfVisit() {
		return dateOfVisit;
	}

	public void setDateOfVisit(String dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDecease() {
		return decease;
	}

	public void setDecease(String decease) {
		this.decease = decease;
	}

	@Override
	public BaseDTO getDto() {
		PatientDTO dto = initDTO(new PatientDTO());

		dto.setName(name);
		if (dateOfVisit != null && !dateOfVisit.isEmpty()) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDate = dateFormat.parse(dateOfVisit);
				dto.setDateOfVisit(parsedDate);
			} catch (ParseException e) {
				// Handle parse exception if needed
				e.printStackTrace();
			}
		}
		if (mobile != null && !mobile.isEmpty()) {
			dto.setMobile(Long.valueOf(mobile));
		}
		dto.setDecease(decease);
		return dto;
	}
}