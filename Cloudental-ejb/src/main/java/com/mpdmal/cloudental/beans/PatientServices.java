package com.mpdmal.cloudental.beans;

import java.util.Date;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.WebService;

import com.mpdmal.cloudental.dao.ActivityDAO;
import com.mpdmal.cloudental.dao.DentistDAO;
import com.mpdmal.cloudental.dao.MedicalhistoryentryDAO;
import com.mpdmal.cloudental.dao.PatientDAO;
import com.mpdmal.cloudental.dao.VisitDAO;
import com.mpdmal.cloudental.entities.Activity;
import com.mpdmal.cloudental.entities.Address;
import com.mpdmal.cloudental.entities.Contactinfo;
import com.mpdmal.cloudental.entities.ContactinfoPK;
import com.mpdmal.cloudental.entities.Dentist;
import com.mpdmal.cloudental.entities.Medicalhistoryentry;
import com.mpdmal.cloudental.entities.MedicalhistoryentryPK;
import com.mpdmal.cloudental.entities.Patient;
import com.mpdmal.cloudental.entities.Patienthistory;
import com.mpdmal.cloudental.entities.PricelistItem;
import com.mpdmal.cloudental.util.CloudentUtils;
import com.mpdmal.cloudental.util.exception.InvalidContactInfoTypeException;
import com.mpdmal.cloudental.util.exception.InvalidMedEntryAlertException;
import com.mpdmal.cloudental.util.exception.PatientNotFoundException;

@Named
@Stateless
@LocalBean
@WebService
public class PatientServices {
	@EJB
	private MedicalhistoryentryDAO _medentrydao;
	@EJB
	private PatientDAO _pdao;
	@EJB
	private ActivityDAO _acvdao;
	@EJB
	private DentistDAO _ddao;
	@EJB
	private VisitDAO _vdao;
    
    public PatientServices() {}
    
    //for OOC testing
    public void setVisitDao (VisitDAO dao) { _vdao = dao; }
    public void setPatientDao (PatientDAO dao) { _pdao = dao;}
    public void setMedhistentryDao(MedicalhistoryentryDAO medentrydao) {_medentrydao = medentrydao; }
    public void setActivityDao(ActivityDAO dao) { _acvdao = dao;}
    public void setDentistDao(DentistDAO dao) {_ddao = dao; }

    //SERVICES
	public Vector<Patient> getPatients (String dentistid) {
		Dentist dentist = _ddao.getDentist(dentistid);
		if (dentist == null) {
			CloudentUtils.logError("Dentist does not exist:"+dentistid);
			return null;
		}
		return _pdao.getPatients(dentistid);
	}

    public void addMedicalHistoryEntry(int patientID, String comment, int alert) throws 
    															PatientNotFoundException,
    															InvalidMedEntryAlertException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException(patientID, "No such Patient/MedHistory cannot add entry");

    	MedicalhistoryentryPK id = new MedicalhistoryentryPK();
    	id.setAdded(new Date());
    	id.setId(p.getId());
    	Medicalhistoryentry entry = new Medicalhistoryentry();
    	entry.setAlert(alert);
    	entry.setComments(comment);
    	entry.setId(id);
    	_medentrydao.updateCreate(entry, false);
    }
    public void addContactinfo(int patientID, String info, int type) throws 
    											PatientNotFoundException, 
    											InvalidContactInfoTypeException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException(patientID, "Cannot add Contact info "+type+"|"+info);
    	
    	ContactinfoPK id = new ContactinfoPK(); 
		id.setId(p.getId());
		id.setInfotype(type);

		Contactinfo cnt = new Contactinfo();
		cnt.setInfo(info);
		cnt.setId(id);
		p.addContactInfo(cnt);
		_pdao.updateCreate(cnt, true);
    }
    //no need for Addrs obj to contain a patient object, just supply patient id 
    public void addAddress(int patientID, Address adr) throws Exception {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException(patientID, "Cannot add Address");

    	if (adr.getId()== null) {
    		CloudentUtils.logError("cannot add Address with no id ..."+adr.getXML());
    		throw new Exception ("cannot add Address with no id for patient:"+patientID);
    	}
    	//Address object enforces valid id.addressType , no need to check...
    	adr.getId().setId(patientID);
    	adr.setPatient(p);
    	p.addAddress(adr);
    	_pdao.updateCreate(p, true);    	
    }
    
        //ACTIVITIES
    public Vector<Activity> getActivities (int patientID) throws PatientNotFoundException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException(patientID, "Cannot get Activities:");
    	return _acvdao.getActivities(p.getId());
    }

    public Vector<Activity> getActivitiesByDate (int patientID, Date from , Date to) throws PatientNotFoundException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException(patientID, "Cannot get Activities:");
    	return _acvdao.getActivities(p.getId(), from , to);
    }

    public void createActivity (int patientID, String description, Date start, Date end, PricelistItem price) 
    																		throws PatientNotFoundException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException(patientID, "Cannot create Activity:");
    	
    	Patienthistory ph = p.getDentalHistory();
    	Activity ac = new Activity();
    	ac.setDescription(description);
    	ac.setStartdate(start);
    	ac.setEnddate(end);
    	ac.setPriceable(price);
    	ph.addActivity(ac);
    	
    	_acvdao.updateCreate(ac, false);
    }
    
    public void deleteActivities (int patientID) throws PatientNotFoundException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException (patientID, "Cannot delete Activity:");
    	
    	for (Activity acv : _acvdao.getActivities(p.getId())) {
			_acvdao.delete(acv);
		}
    }    
    
    public void deleteActivitiesByDate (int patientID, Date from , Date to) throws PatientNotFoundException {
    	Patient p = _pdao.getPatient(patientID);
    	if (p == null) 
    		throw new PatientNotFoundException (patientID, "Cannot delete Activities:");
    	
    	for (Activity acv : _acvdao.getActivities(p.getId(),from , to)) {
			_acvdao.delete(acv);
		}
    }

    //VISITS
    public void createVisit (int activityID, String description, Date start, Date end, PricelistItem price)
    																	throws PatientNotFoundException {
//    	Patient p = _pdao.getPatient(patientID);
//    	if (p == null) 
//    		throw new PatientNotFoundException(patientID, "Cannot create Activity:");
//    	
//		Visit v = new Visit();
//		v.setComments("a visit for acitvity "+activity.getDescription());
//		v.setVisitdate(start);
//		v.setEnddate(end);
//		activity.addVisit(v);
//
//		_v.updateCreate(ac, false);
    }

}