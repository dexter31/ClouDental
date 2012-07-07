package com.mpdmal.cloudental.web.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.ScheduleModel;

import com.mpdmal.cloudental.beans.DentistServices;
import com.mpdmal.cloudental.beans.PatientServices;
import com.mpdmal.cloudental.web.beans.backingbeans.PatientManagerBean;
import com.mpdmal.cloudental.web.beans.backingbeans.SchedulerBean;
import com.mpdmal.cloudental.web.beans.base.BaseBean;

@Named("office")
@SessionScoped
public class Office extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//CDI BEANS
	@Inject
	DentistServices _dsvc;
	@Inject
	PatientServices _psvc;
	
	//MODEL
	private SchedulerBean scheduleControler; //scheduler backing bean
	private PatientManagerBean patientManagment; //patient mngmnt backing bean
	private int ownerID;
	
	@Override
	public void init () {
		scheduleControler = new SchedulerBean(_dsvc);
		patientManagment = new PatientManagerBean(this, _dsvc, _psvc);
		//populateScheduler();
	}

	//GETTERS/SETTERS
	public int getOWnerID() {	return ownerID;	}
	public ScheduleModel getVisitModel() { return scheduleControler.getModel(); }
	public SchedulerBean getScheduleControler() {	return scheduleControler;	}
	public PatientManagerBean getPatientManagment() {	return patientManagment;	}

	public void setPatientManagment(PatientManagerBean patientManagment) {	this.patientManagment = patientManagment;	}
	public void setScheduleControler(SchedulerBean scheduleControler) {	this.scheduleControler = scheduleControler;	}
	public void setOwner(int ownerID) {	this.ownerID = ownerID;	}
	
	//INTERFACE
	public void setOwnerAndPopulate(int ownerID) {	
		this.ownerID = ownerID;
		populateScheduler();
		populatePatientList();
	}
	
	public void populateScheduler() {	
		scheduleControler.populateScheduler(getOWnerID());
	}
	
	public void populatePatientList() {
		patientManagment.populatePatients(getOWnerID());
	}
}
