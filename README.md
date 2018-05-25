"# SRMU_BUS" 
The project is based on geolocation, in which a vehicle’s live location is shared to multiple users,the project will be made using android studio (version 3.0.1 latest) along with google Firebase and google API’s (Application Program Interface).

#Main highlights: -

“Bus Tracking Application” is an application for Smart phones that works on Android Operating system.
This application uses the GPS for tracking the bus.
This application will send the current(live) location of the bus to students when they request.
This app generates predictions on bus arrival time.
This application uses a variety of technologies to track the locations of buses in real time
Problems tackled and solution provided by app: - Problem:

In the daily operation of bus transport systems, mainly that of buses, the movement of vehicles is affected by different uncertain conditions as the day progresses, such as: •	Traffic congestion •	unexpected delays •	Irregular vehicle-dispatching times •	other incidents.
Many students are late for classes because they decide to wait for the bus instead of just simply using an alternate transportation. Solution provided by app:
Focus on providing more convince to bus schedules.
It provides real time bus information so the students may prepare accordingly
Also provide integrated google map
Firebase: - firebase is mobile and web application development platform developed by Firebase, Inc. in 2011, then acquired by google in 2014. (for further details https://en.wikipedia.org/wiki/Firebase). The firebase will contain all the records of student, admin, driver and will also authenticate them during login.

#API’s used for tracking: -

Map API
Distance Matrix API
Directions API
Geolocation API
Geocoding API

#The project is divided into 3 modules: - 
1)driver module 
2)client module
3)admin module

Driver module: The authorized bus drivers are provided with their unique log in credentials. They need to log in and then have to start their location service before driving. The current location of the bus will be updated from driver’s mobile to the server every moment in the form of latitude and longitude.

Client module: - The users of this module need to log in with their unique ID provided by the college management. They can get access to the details of all the buses of college through their phones. Here they will get all bus and driver related information. Students can track the location of their bus from any location. Student and staff must make sure that their location service is active. They can also get the estimated time of arrival of bus at their respective stops.

Admin module: - This module is for the bus administrator for updating the information that is there in the server when required. The admin can see the location of all buses and can also inform the students about any change in bus schedule or in case of any other scenario the bus may reach late or unable to reach the location.

Requirements of Project: -

An android for all parties using the app.
An active internet connection.
An active GPS connection only for driver module.


