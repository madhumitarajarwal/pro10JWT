package com.rays.ctl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.DropdownList;
import com.rays.common.ORSResponse;
import com.rays.dto.PatientDTO;
import com.rays.form.PatientForm;
import com.rays.service.PatientServiceInt;

@RestController
@RequestMapping(value = "Patient")
public class PatientCtl extends BaseCtl<PatientForm, PatientDTO, PatientServiceInt> {

	@Autowired
	PatientServiceInt pService;

	@GetMapping("/preload")
	public ORSResponse preload() {
		ORSResponse res = new ORSResponse(true); 
		/*
		 * HashMap<Integer,String> map=new HashMap<Integer,String>(); map.put(1,
		 * "heart"); map.put(2, "liver"); map.put(3, "brain");
		 * 
		 * 
		 * res.addResult("pList", map); return res;
		 */
		
		
		PatientDTO dto = new PatientDTO();

		List<DropdownList> list = pService.search(dto,userContext);		
		res.addResult("pList", list);
		return res;
	}
}