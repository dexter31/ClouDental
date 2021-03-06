package com.mpdmal.cloudental.util.exception;

import com.mpdmal.cloudental.util.CloudentUtils;
import com.mpdmal.cloudental.util.exception.base.CloudentException;

public class DentistExistsException  extends CloudentException {
	private static final long serialVersionUID = 1L;

	public DentistExistsException(String dentistid, String message, Throwable x) {
		super("Dentist already Exists:"+dentistid+"\n"+message,x);
		CloudentUtils.logError("Dentist already exists:"+dentistid+" ["+message+"]");
	}

	public DentistExistsException(String dentistid, String message) {
		super("Dentist already Exists:"+dentistid+"\n"+message);
		CloudentUtils.logError("Dentist already exists:"+dentistid+" ["+message+"]");
	}
	
	public DentistExistsException(String dentistid) {
		this(dentistid, "");
	}

	public DentistExistsException(String dentistid, Throwable x) {
		this(dentistid,"", x);
	}
}