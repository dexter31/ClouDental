package com.mpdmal.cloudental.web.beans;

import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.mpdmal.cloudental.beans.DentistBean;
import com.mpdmal.cloudental.beans.DentistServices;
import com.mpdmal.cloudental.beans.PatientServices;
import com.mpdmal.cloudental.entities.Dentist;
import com.mpdmal.cloudental.entities.Discount;
import com.mpdmal.cloudental.util.exception.DentistNotFoundException;
import com.mpdmal.cloudental.util.exception.DiscountNotFoundException;
import com.mpdmal.cloudental.util.exception.ValidationException;

public class DiscountManagementBean extends Discount {
	
	UserHolder user = (UserHolder)FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().get("userHolder");
	
	private static final long serialVersionUID = 1L;
	@Inject
	DentistServices dentistService;
	@Inject
	PatientServices patientServices;

	@Inject
	DentistBean dentistBean;
	
	private Vector<Discount> discounts;
	
	public Vector<Discount> getDiscounts() {
		Dentist d = user.getCurrentUser();
		if(discounts==null){
			discounts =(Vector<Discount>)dentistService.getDiscounts(d.getId());
		}
		return discounts;
	}

	public String createDiscount() throws DentistNotFoundException, ValidationException{
		dentistService.createDiscount(user.getCurrentUser().getId(), getTitle(),getDescription(), getDiscount().doubleValue() );
		return null;
	}

	public String updateDiscount() throws DiscountNotFoundException{
		if (getId() != null)
			dentistService.updateDiscount(getId(), getDescription(), getTitle());
		return null;
	}

	public void setCurrentDiscount(Discount d){
		System.out.println("setDiscount: "+d.getId());
		setId(d.getId());
		setTitle(d.getTitle());
		setDiscount(d.getDiscount());
		setDescription(d.getDescription());
	}


	public void delete(int id) throws DiscountNotFoundException {
		dentistService.deleteDiscount(id);
	}
}
