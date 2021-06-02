package net.urover.acme;

import net.urover.acme.DayCommunicationSelection.CommunicationMethod;

/*  * THe excercise description didn't give too much information about selecting
		the best time/method.
	* Assumption that "sunny" and "clear" are equivalent.
	* I made assumptions about the preference hierarchy and on resolving cases that
			overlap or weren't specified (temperature is over 75 but no sunny)
		* Assumption that the order of condition/method criteria in the description
			is also preferred order of contact (i.e. we'd rather text people when
			they're happy than call them when they're bummed)
		* For cases where the weather is warm (75 degrees+) but not clear or raining,
			we treat that as the middle category (it's warm, but not sunny, so let's
			slide into their inbox rather than risk annoying them with a text)
		* if multiple time slices in a day are "equal" we send reach out during the
			earliest one just so there's a consistent behavior in case we modify later
		* We do not care about time of day.  We'll call you at 2AM if that's the best
			weather available for that day.

	IT's a scrum team to the details are open to refinement on the next pass if the
		customer wants something else, right?
 */
public class CommunicationSelector {


	public DayCommunicationSelection determineBestContactTimeAndMethod(ForecastDay day){
		TimeSliceSortingHat sortingHat = new TimeSliceSortingHat(day.getTimeSlices());
		TimeSlice bestTimeSlice = sortingHat.chooseBestTimeSlice();

		CommunicationMethod method = chooseContactMethod(bestTimeSlice);

		DayCommunicationSelection winner = new DayCommunicationSelection(method, bestTimeSlice);
		return winner;
	}

	private CommunicationMethod chooseContactMethod(TimeSlice bestTimeSlice) {
		CommunicationMethod method = CommunicationMethod.EMAIL;
		if(bestTimeSlice.condition == TimeSlice.Condition.SUNNY && bestTimeSlice.temp == TimeSlice.TempDescription.WARM) {
			method = CommunicationMethod.TEXT;
		}else if(bestTimeSlice.condition == TimeSlice.Condition.RAINING || bestTimeSlice.temp == TimeSlice.TempDescription.COOL){
			method=CommunicationMethod.PHONE;
		}
		return method;
	}


}
