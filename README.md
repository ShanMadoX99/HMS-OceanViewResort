# HMS Ocean View Resort

## Overview
A hotel management system built in Java with MySQL integration.  
Features include:
- Add New Reservation
- Display Reservation Details
- List All Reservations
- Calculate and Print Bill
- Help Section
- Exit System

## Technologies
- Java (JDK 17)
- IntelliJ IDEA
- MySQL (Connector/J)

## How to Run
1. Clone the repository. 
(bash
   git clone https://github.com/ShanMadoX99/HMS-OceanViewResort.git
)
2. Open in IntelliJ IDEA.
3. Configure MySQL database (`hotel_db`).
4. Run `HMSOceanViewResort.java`.

## Author
Madusha Priyashan



---

## 📂 UML Class Diagram

``text
+-------------------+
| HMSOceanViewResort|
+-------------------+
| - sc: Scanner     |
| + main()          |
| + addReservation()|
| + displayReservation()|
| + listAllReservations()|
| + calculateBill() |
| + help()          |
+-------------------+

+-------------------+
| DBConnection      |
+-------------------+
| - conn: Connection|
| + getConnection() |
+-------------------+

+-------------------+
| Reservation       |
+-------------------+
| - reservation_id  |
| - guest_id        |
| - room_type       |
| - check_in        |
| - check_out       |
+-------------------+

+-------------------+
| Billing           |
+-------------------+
| - bill_id         |
| - reservation_id  |
| - amount          |
| - payment_method  |
| - payment_date    |
+-------------------+
