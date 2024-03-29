Some notes:
* Groovy is incuded solely for a custom logging converter to make it easier to experiment with and explore the OWM api library.  It's not used in any of the "real" code.

* Main class is AcmeSunnyAndMild.  API Key and city are hard-coded for ease of a test run.

* THe excercise description didn't give too full information about selecting
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
        * if multiple time slices in a day are "equal" we reach out during the
          earliest one just so there's a consistent behavior in case we modify later
     * We do not care about time of day.  We'll call you at 2AM if that's the best
          weather available for that day.
    * I didn't make the target city dynamic because I wanted to make the app easier to 
      run for whomever is testing and that eliminates typing a city name.
    * Since I did not see the email Monday, I assumed Genghis wasn't working and had meant Tuesday.  Since I was working with one day instead of two, I opted for the console app instead of a web UI.  I can do a basic web UI, but it's honestly not where my strengths lie, so it would have been pretty basic anyway.
  

It's a scrum team so the details are open to refinement on the next pass if the
  customer wants something else, right?


---
Excercise Description
___
Welcome!  You are a brand new developer starting out at Acme Software Incorporated!  You have just completed your first week, including some excellent onboarding sessions to learn about the history of the company, the current technology architecture, and the future roadmap for the platform.  You have had some great team lunches and tasty welcome donuts.

You have a basic understanding of Acme’s business, which is to work with smaller, independent restaurants to  engage with their customers and potential customers to come in for specials, happy hours and events. Acme does this through various channels including automated phone calls, texts, email and others.  Acme prides itself on the ability to determine both the best message to use to engage food lovers and when to most effectively engage them.

Now you are ready to get down to business!  In talking with your Scrum team and your technical lead, you have determined that the first task you are going to take on is to implement a new feature that determines when to best contact individuals based on the weather.  Acme’s product team has determined the following:
* The best time to engage a customer via a text message is when it is sunny and warmer than 75 degrees Fahrenheit
* The best time to engage a customer via email is when it is between 55 and 75 degrees Fahrenheit
* The best time to engage a customer via a phone call is when it is less than 55 degrees or when it is raining.

You will need to build a function that uses the API provided by openweathermap.org at the following url: http://api.openweathermap.org/data/2.5/forecast?q=minneapolis,us&units=imperial&APPID=09110e603c1d5c272f94f64305c09436.

The documentation for this API can be found here: https://openweathermap.org/forecast5.

Your function should determine what outreach method is best for Minneapolis, MN over the next 5 days.  The forecast will give you data for multiple points during a given day, you may choose to use that data how you wish.  In general Acme uses technologies from the Kotlin/Java family to implement services and has a preference for single page JavaScript applications for the frontend using Angular or React.  However, you may use whatever language you would prefer.  The function can be run as either a web application or a command line application.  If it is a web application it should display a 5 day calendar with the best outreach method listed for that date, if it is a command line application it should print out each date and the best outreach method for that day on a new line.

This will be your first work in your new role, so please put your best foot forward and build this to your professional best.  You may use any online resources or open source libraries to implement the solution, but all the code developed must be your own.