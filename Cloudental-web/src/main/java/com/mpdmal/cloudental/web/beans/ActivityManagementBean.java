package com.mpdmal.cloudental.web.beans;

import java.util.Map;
import java.util.Vector;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mpdmal.cloudental.beans.DentistServices;
import com.mpdmal.cloudental.beans.PatientServices;
import com.mpdmal.cloudental.entities.Activity;
import com.mpdmal.cloudental.entities.Discount;
import com.mpdmal.cloudental.entities.Patient;
import com.mpdmal.cloudental.entities.PricelistItem;
import com.mpdmal.cloudental.util.exception.PricelistItemNotFoundException;

public class ActivityManagementBean extends Activity{

	private static final long serialVersionUID = 1L;
	


	UserHolder user = (UserHolder)FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().get("userHolder");
	
	@EJB
	DentistServices dentistService;
	@EJB
	PatientServices patientServices;
	
	private PricelistItem selectedPricelistItem;
	private Discount selectedDiscount;
	private Patient selectedPatient;
	
	private Activity selectedActivity;
	private Vector<Activity> activityListOfPatient;
	
	

	public Vector<Activity> getActivityListOfPatient() throws Exception {
		Map<String,String> params =       FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String selectedPatientId =  params.get("activityListForm:patient_hinput");
		System.out.println("selectedPatientId: "+selectedPatientId);
		if(selectedPatient!=null){
			if(activityListOfPatient==null){
				System.out.println("getActivities from patientID: "+selectedPatient.getId());
				activityListOfPatient  = patientServices.getActivities(selectedPatient.getId());
			}
		}else if(selectedPatientId!=null){
			try{
				Integer selectedPatientIdInt = Integer.parseInt(selectedPatientId);
				if(activityListOfPatient==null){
					System.out.println("getActivities from service");
					activityListOfPatient  = patientServices.getActivities(selectedPatientIdInt);
				}
			}catch (Exception e) {

			}
		}

		
		return  activityListOfPatient;
	}

	public String createActivity() throws Exception{
		patientServices.createActivity(selectedPatient.getId(), getDescription(), getStartdate(), getEnddate(), selectedPricelistItem.getId(), selectedDiscount.getId());
		return null;
	}

	public String updateActivity(){
		System.out.println("update activity: "+getId());
//		patientServices.updateActivity(this);
		return null;
	}

	public void setCurrentActivity(ActivityManagementBean  a){
//		System.out.println("setCurrentActivity: "+a.getActivityID());
		
	}

	public PricelistItem getSelectedPricelistItem() {
		return selectedPricelistItem;
	}

	public void setSelectedPricelistItem(PricelistItem selectedPricelistItem) {
		this.selectedPricelistItem = selectedPricelistItem;
	}

	public Discount getSelectedDiscount() {
		return selectedDiscount;
	}

	public void setSelectedDiscount(Discount selectedDiscount) {
		this.selectedDiscount = selectedDiscount;
	}

	public Patient getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(Patient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}
	
	
	
	public Activity getSelectedActivity() {
		return selectedActivity;
	}

	public void setSelectedActivity(Activity selectedActivity) {
		this.selectedActivity = selectedActivity;
	}
	
	public String deleteActivity(){
		System.out.println("deleteActivity: "+getSelectedActivity().getId());
		try {
			//patientServices.deleteActivity(getSelectedActivity().getId());
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Service error",""));
		}
		return null;
	}

	public void rowEditListener(org.primefaces.event.RowEditEvent ev){
		System.out.println("ActivityManagementBean rowEditListener()");
        try {
        	Activity activity = (Activity)ev.getObject();
            if(activity == null) {
                System.out.println("activity to update is null...");
            } else {
                System.out.println("update activity" + activity.getId());
                patientServices.updateActivity(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Service error", e.getMessage()));
        }        
    }
	
//Test TAG 2
	

}
