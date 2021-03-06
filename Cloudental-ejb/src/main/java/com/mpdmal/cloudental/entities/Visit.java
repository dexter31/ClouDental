package com.mpdmal.cloudental.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Entity
public class Visit extends com.mpdmal.cloudental.entities.base.DBEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String comments;
	
	@NotNull
	private int color;
	
	@NotNull
	private String title;
	
	@NotNull
	@Temporal( TemporalType.TIMESTAMP)
	private Date visitdate;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date enddate;
	
    @ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="activityid")
	private Activity activity;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="visit", fetch=FetchType.LAZY)
	private Collection<Toothhistory> toothhistory;
    
    @NotNull
    private BigDecimal deposit;

    public Visit() {    }

	public Date getVisitdate() {	return this.visitdate;	}
	public Date getEnddate() 	{	return this.enddate;	}
	public String getComments() 	{	return this.comments;	}
	public Integer getId() 			{	return this.id;	}
	public Activity getActivity() 	{	return this.activity;	}
	public Collection<Toothhistory> getToothhistory() {	return this.toothhistory;	}
    public BigDecimal getDeposit() 	{	return this.deposit;	}
	public String getTitle() 	{	return this.title;	}
	public Integer getColor() 			{	return this.color;	}
	
    public void setToothhistory(Collection<Toothhistory> toothhistory) {	this.toothhistory= toothhistory;	}
	public void setActivity(Activity activity) 	{	this.activity = activity;	}
	public void setComments(String comments) 	{	this.comments = comments;	}
	public void setEnddate(Date enddate) 	{	this.enddate = enddate;	}
	public void setVisitdate(Date visitdate) {		this.visitdate = visitdate;	}
	public void setId(Integer id) {		this.id = id;	}
    public void setDeposit(BigDecimal deposit) 			{	this.deposit= deposit;	}
	public void setTitle(String title) 	{	this.title = title;	}
	public void setColor(Integer color) {		this.color = color;	}


	@Override
	public String getXML() {
		StringBuilder ans= new StringBuilder("<visit></visit>");
		ans.insert(ans.indexOf("</visit"), "<start>"+visitdate+"</start>");
		ans.insert(ans.indexOf("</visit"), "<end>"+enddate+"</end>");
		ans.insert(ans.indexOf("</visit"), "<title>"+title+"</title>");
		ans.insert(ans.indexOf("</visit"), "<color>"+color+"</color>");
		ans.insert(ans.indexOf("</visit"), "<comments>"+comments+"</comments>");
		
		ans.insert(ans.indexOf("</visit"), "<ToothOperations>");
		for (Toothhistory history : toothhistory) {
			ans.insert(ans.indexOf("</visit"), history.getXML());
		}		
		ans.insert(ans.indexOf("</visit"), "</ToothOperations>");
		
		return ans.toString();
	}	
	
	@Override
	public String toString() {
		String ans = title+" ["+activity.getPatienthistory().getPatient().getSurname()+"]\n ";
		
		SimpleDateFormat dftime = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat dfdate = new SimpleDateFormat("d/MMM");
		Calendar c = Calendar.getInstance();
		
		c.setTime(visitdate);
		int startday = c.get(Calendar.DAY_OF_MONTH);
		String starttime = dftime.format(visitdate);
		String startdate = dfdate.format(visitdate);
		
		c.setTime(enddate);
		int endday = c.get(Calendar.DAY_OF_MONTH);
		String endtime = dftime.format(enddate);
		String enddatestr = dfdate.format(enddate);
		
		if (startday == endday)
			return ans+ startdate +" "+starttime+" - "+endtime;

		return ans+ startdate +" "+starttime+" - "+enddatestr +" "+endtime;
	}
}