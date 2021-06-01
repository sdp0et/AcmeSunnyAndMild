package net.urover.acme;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayCommunicationSelection {
	public static final Duration TIME_SLICE_DURATION = Duration.ofHours(3);

	enum CommunicationMethod{
		TEXT,
		EMAIL,
		PHONE,
		TELEGRAM; //unsupported
	}

	private CommunicationMethod chosenMethod;
	private TimeSlice chosenTimeSlice;

	public DayCommunicationSelection(CommunicationMethod chosenMethod, TimeSlice chosenTimeSlice) {
		this.chosenMethod = chosenMethod;
		this.chosenTimeSlice = chosenTimeSlice;
	}

	//access
	public LocalDate getDate() {
		return chosenTimeSlice.getTime().toLocalDate();
	}

	public String getDateString() {
		return chosenTimeSlice.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	public String getTimeRange() {
		String start = chosenTimeSlice.getTime().toLocalTime().toString();
		String end =	chosenTimeSlice.getTime().toLocalTime().plus(TIME_SLICE_DURATION).toString();
		return start + " - " +end;
	}

	public CommunicationMethod getChosenMethod() {
		return chosenMethod;
	}

	public TimeSlice getChosenTimeSlice() {
		return chosenTimeSlice;
	}



}
