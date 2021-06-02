package net.urover.acme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TimeSliceSortingHat {
	List<TimeSlice> slices = new ArrayList<>();

	public TimeSliceSortingHat(List<TimeSlice> slices) {
		this.slices = slices;
		Collections.sort(slices);
	}

	/*	There's got to be a better way of scoring these, but we're going to get everything working first and come back later if I'm still concerned with that
		Might extract an interface to play with different implementations

		Basically, check the conditions in the bulleted criteria (plus a case not covered) and splitting the third criteria into its sub-criteria
	 */
	public TimeSlice chooseBestTimeSlice(){

		//sunny and warm
		Optional<TimeSlice> optionalWinner = slices.stream().filter(slice -> slice.getCondition() == TimeSlice.Condition.SUNNY && slice.getTemp() == TimeSlice.TempDescription.WARM).findFirst();
			if(optionalWinner.isPresent()) return optionalWinner.get();
		//warm (and not raining)
		//  yeah, this overlaps with the prior state, but since the description isn't clear on whether it should be a text or email,
		//		I'm leaving it as a separate check for easier changing
		optionalWinner = slices.stream().filter(slice -> slice.getCondition() != TimeSlice.Condition.RAINING && slice.getTemp() == TimeSlice.TempDescription.WARM).findFirst();
			if(optionalWinner.isPresent()) return optionalWinner.get();
		//temperate (and not raining)
		optionalWinner = slices.stream().filter(slice -> slice.getCondition() != TimeSlice.Condition.RAINING && slice.getTemp() == TimeSlice.TempDescription.TEMPERATE).findFirst();
			if(optionalWinner.isPresent()) return optionalWinner.get();
		//raining (any temperature)
		optionalWinner = slices.stream().filter(slice -> slice.getCondition() == TimeSlice.Condition.RAINING).findFirst();
			if(optionalWinner.isPresent()) return optionalWinner.get();
		//cool
		//	could combine with the stream check for raining, but it's more legible as a separate step
		optionalWinner = slices.stream().filter(slice -> slice.getTemp() == TimeSlice.TempDescription.COOL).findFirst();
			if(optionalWinner.isPresent()) return optionalWinner.get();
		//none of the above
			return slices.get(0);
	}



}
