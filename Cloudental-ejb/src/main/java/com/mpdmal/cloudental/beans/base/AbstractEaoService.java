package com.mpdmal.cloudental.beans.base;

import javax.ejb.EJB;

import com.mpdmal.cloudental.EaoManager;
import com.mpdmal.cloudental.entities.Activity;
import com.mpdmal.cloudental.entities.Dentist;
import com.mpdmal.cloudental.entities.Discount;
import com.mpdmal.cloudental.entities.Patient;
import com.mpdmal.cloudental.entities.PricelistItem;
import com.mpdmal.cloudental.util.exception.ActivityNotFoundException;
import com.mpdmal.cloudental.util.exception.DentistNotFoundException;
import com.mpdmal.cloudental.util.exception.DiscountNotFoundException;
import com.mpdmal.cloudental.util.exception.PatientNotFoundException;
import com.mpdmal.cloudental.util.exception.PricelistItemNotFoundException;

public class AbstractEaoService {
	@EJB
	protected EaoManager emgr;
	
    public Dentist findDentist(int id) throws DentistNotFoundException {
    	Dentist d = emgr.findOrFail(Dentist.class, id);
		if (d == null) 
			throw new DentistNotFoundException(id);
		return d;     
    }
    
    public PricelistItem findPricable(int id) throws PricelistItemNotFoundException {
    	PricelistItem item = emgr.findOrFail(PricelistItem.class, id);
    	if (item == null)
    		throw new PricelistItemNotFoundException(id);
    	return item;
    }
    public Discount findDiscount(int id) throws DiscountNotFoundException {
    	Discount d = emgr.findOrFail(Discount.class, id);
    	if (d == null)
    		throw new DiscountNotFoundException(id);
    	return d;
    }
    public Patient findPatient(int id) throws PatientNotFoundException {
    	Patient p = emgr.findOrFail(Patient.class, id);
    	if (p == null)
    		throw new PatientNotFoundException(id);
    	return p;
    }
    
    public Activity findActivity(int id) throws ActivityNotFoundException {
    	Activity ac = emgr.findOrFail(Activity.class, id);
    	if (ac == null)
    		throw new ActivityNotFoundException(id);
    	return ac;
    }

}