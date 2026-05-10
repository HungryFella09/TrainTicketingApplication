# Train Ticketing Application
Java application for buying tickets to trains.

## Main Functionalities

### Book tickets
Ability to book one or more tickets. A check is first made to make sure that there is enough space left on the train for the ammount of tickets bought at a specified date. An appropriate message is shown in the case that the train is full.
After that, a booking is made with the associated tickets and user. Afterwards, an email is sent to the user's email address. The sender's email address and password is taken from a properties file. 


### Find routes in between stations
The user selects two stations and receives the trains that need to be taken in between the stations. First the algorithm checks for any direct routes in between stations. If it doesn't find any, it takes all the routes that can reach the departure station and the routes that can reach the arrival station and checks if any of them overlap. Then, it finds any trains that follow the found routes.
If none is found, then an appropriate message is shown.


### Administrator mode
If a user has administator rights, they get an additional tab. Here the admin can perform CRUD operations on routes with stations and trains. They can also see what bookings were made for a selected train. The admin can select a train and a delay ammount and automated emails are sent to the users with tickets on those trains on the current day.


## Non functional specifications
A database was created for storing the following entities:
Users - with username, password, email and role (user or admin)
Stations - with name
Routes - with name
Station-Routes - the table linking stations and routes, with the departure and arrival times 
Trains - with name and max capacity
Bookings - with an associated user and a price 
Tickets - with an associated train, booking and number of tickets

Passwords are encrypted before being saved in the database




## Examples of inputs and outputs

### Signing In
<img width="342" height="327" alt="image" src="https://github.com/user-attachments/assets/40a70c40-4f7b-4f09-a9cd-ae1cf2421d7b" />


### Booking tickets
<img width="1022" height="730" alt="image" src="https://github.com/user-attachments/assets/47ca955a-154f-487e-b647-3a385d0f1c9c" />

#### Confirmation message
<img width="1016" height="728" alt="image" src="https://github.com/user-attachments/assets/8afaea6e-1a5e-4d0a-9261-b382325baafb" />

### Finding Routes
<img width="1030" height="731" alt="image" src="https://github.com/user-attachments/assets/aed0cfe9-b7fe-4d2f-8539-7817c838394f" />

### Administrator mode
<img width="1015" height="735" alt="image" src="https://github.com/user-attachments/assets/6549611d-2169-4644-ae17-79baa42b0915" />

#### Delaying a train
<img width="361" height="61" alt="image" src="https://github.com/user-attachments/assets/b921cd9c-c2c6-48a6-8327-8c443e3020bd" />

#### Adding stations to routes
<img width="515" height="354" alt="image" src="https://github.com/user-attachments/assets/c52c3eba-2532-4eac-a6a9-484a0dd56bf4" />


