# Qantas-Coding-Challenge
Business Requirements
As an airline company, there is a new business requirement as part of the customer
notification program to notify passengers via push notifications on the airline app whenever
disruptions occur for the flights in which the passengers have booked.
The flight schedules are done in an internal system called ‘Fight Scheduler’ and the system is
responsible for generating the appropriate disruption events. This is an existing system and
can support any type of integration requested for providing the events to downstream
system

Build Requirements
A restful API needs to be built which can receive the appropriate disruption events from
‘‘Fight Scheduler’ and send the corresponding push notifications to passengers

Please find the key capabilities which needs to be built into the restful service

1. The Rest Interface should have attributes to get the flight number, departure port,
arrival port, departure date, booking and passenger information
2. The processing is synchronous, and the appropriate response must be returned back
to ‘flight scheduler’ system once the push notification is sent to the passenger
devices
3. All the device related details corresponding to a booking will be available in the
below endpoint
GET https://api.airline.com/subscriptions/{ bookingid}/devicedetails

Assumptions
1. The subscription is expected to be received from the airline app anytime a booking is
added into the app
2. A passenger can use multiple devices to check the booking and hence multiple
subscriptions is possible for a booking
