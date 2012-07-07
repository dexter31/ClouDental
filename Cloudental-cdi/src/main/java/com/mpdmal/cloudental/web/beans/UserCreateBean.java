package com.mpdmal.cloudental.web.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.mpdmal.cloudental.beans.DentistBean;
import com.mpdmal.cloudental.entities.Dentist;
import com.mpdmal.cloudental.util.exception.DentistExistsException;
import com.mpdmal.cloudental.util.exception.ValidationException;
import com.mpdmal.cloudental.web.beans.base.BaseBean;

@Named("createBean")
@RequestScoped
public class UserCreateBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	//MODEL
	private Dentist _d;
	private String _passwordConfirm;

	//CDI BEANS
	@Inject DentistBean _dbn;
	@Override
	public void init() {
		_d = new Dentist();
		_passwordConfirm = "";
	}
	
	//GETTERS/SETTERS
	public Dentist getDentist() {	;return _d;	}
	public String getPasswordConfirm() {	return _passwordConfirm;	}

	public void setDentist(Dentist d) {	_d = d;	}
	public void setPasswordConfirm(String passwordConfirm) {	_passwordConfirm = passwordConfirm;	}
	
	//INTERFACE
	public void createDentist() {
        RequestContext context = RequestContext.getCurrentInstance();  
        FacesMessage msg = null;  
        boolean created = false;  

		try {
			if (!_d.getPassword().equals(_passwordConfirm))
				throw new ValidationException("Passwords do not match");
			_dbn.createDentist(_d.getName(), _d.getSurname(), _d.getUsername(), _d.getPassword());
		} catch (DentistExistsException e) {
			 msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", e.getMessage());
	            created = false; 
		} catch (ValidationException e) {
			 msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", e.getMessage());
	            created = false; 
		}
		created = true;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "User Created ", _d.getUsername());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        context.addCallbackParam("created", created);  
	}
}
