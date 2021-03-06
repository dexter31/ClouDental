package com.mpdmal.cloudental.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
public class Patienthistory extends com.mpdmal.cloudental.entities.base.DBEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String comments;
	@Temporal( TemporalType.TIMESTAMP)
	private Date enddate;
	@Temporal( TemporalType.TIMESTAMP)
	private Date startdate;
	@Id
	@OneToOne
	@JoinColumn(name="patientid")
	private Patient patient;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="patienthistory")
	private Collection<Activity> activities;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="patienthistory", fetch=FetchType.LAZY)
	private Collection<Prescription> prescriptions;

    public Patienthistory() {}

	public void setComments(String comments) 	{	this.comments = comments;	}
	public void setEnddate(Date enddate) 	{	this.enddate = enddate;	}
	public void setStartdate(Date startdate) {	this.startdate = startdate;	}
	public void setPatient(Patient patient) 	{	this.patient = patient;	}
	public void setActivities (Collection<Activity> activities) {
		if (activities == null)
			activities = new ArrayList<Activity>();
		else
			activities.clear();
		for (Activity activity : activities) {
			addActivity(activity);
		}
	}
	
	public void addActivity(Activity activity) {
		if (activities == null)
			activities = new ArrayList<Activity>();
		
		activity.setPatienthistory(this);
		activities.add(activity);
	}
	public Collection<Prescription> getPrescriptions() {	return this.prescriptions;	}
	public void setPrescriptions(Collection<Prescription> prescriptions) {	this.prescriptions = prescriptions;	}
	public void removeActivity (Activity a) {
		if (activities.contains(a))
			activities.remove(a);
	}
	public String getComments() 	{	return this.comments;	}
	public Date getEnddate() 	{	return this.enddate;	}
	public Date getStartdate() {	return this.startdate;	}
	public Patient getPatient() 	{	return this.patient;	}
	public Collection<Activity> getActivities() {	return this.activities;	}
	
	@Override
	public String getXML() {
		StringBuilder ans= new StringBuilder("<dentalhistory></dentalhistory>");
		ans.insert(ans.indexOf("</dentalhistory"), "<comments>"+comments+"</comments>");
		ans.insert(ans.indexOf("</dentalhistory"), "<startdate>"+startdate+"</startdate>");
		ans.insert(ans.indexOf("</dentalhistory"), "<enddate>"+enddate+"</enddate>");
		ans.insert(ans.indexOf("</dentalhistory"), "<activities>");
		for (Activity activity : activities) {
			ans.insert(ans.indexOf("</dentalhistory"), activity.getXML());
		}
		ans.insert(ans.indexOf("</dentalhistory"), "</activities>");
		return ans.toString();		
	}
	
}