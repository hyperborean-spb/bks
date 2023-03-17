**The database structure**

| Key type | Column | Type | Unique | Comment |
| --- | --- | --- | --- | --- |
| Table: USER |  |  |  |  |
| PK | ID | BIGINT | True |  |
|  | NAME | VARCHAR(500) |  |  |
|  | DATE_OF_BIRTH |  |  | Format: 01.05.1993 |
|  | PASSWORD | VARCHAR(500) |  | Min length: 8, max 500 |
| Table: ACCOUNT |  |  |  |  |
| PK | ID | BIGINT | True |  |
| FK | USER_ID | BIGINT | True | Link to USER.ID |
|  | BALANCE | DECIMAL |  | рубли + копейки: в коде – BigDecimal |
| Table: EMAIL_DATA |  |  |  |  |
| PK | ID | BIGINT | True |  |
| FK | USER_ID | BIGINT |  | Link to USER.ID |
|  | EMAIL | VARCHAR(200) | True |  |
| Table: PHONE_DATA |  |  |  |  |
| PK | ID | BIGINT | True |  |
| FK | USER_ID | BIGINT |  | Link to USER.ID |
|  | PHONE | VARCHAR(13) | True | format: 79207865432 |


**General system requirement:**

1. 3 layers - API, service, DAO
2. Assume that only ordinary users (not admins, etc.) are in the system.
3. Assumethat users are created by migrations (we will not complicate things). We simply assume that there is no create operation for ordinary users. For tests, create directly in DAO.
4. The user can have more than one PHONE_DATA (must be at least 1).
5. The user can have more than one EMAIL_DATA (must be at least 1).
6. The user must have exactly one ACCOUNT.
7. The initial BALANCE in ACCOUNT is specified when creating a user.
8. BALANCE in ACCOUNT cannot go negative in any operation.
9. Validation of input API data.

**Required features:**

1. CREATE (only for user-defined data), UPDATE operations for the user. The user can only change his own data:
2. can delete / change / add email if it does not belong by other users
3. can delete / change / add phone if it does not belong by other users
4. cannot change the rest
5. Implement READ operation for users. Implement user search with the result presented with pagination (size, page / offset). Use following search criteria:
6. If "dateOfBirth" is passed, then filter records where "date_of_birth" is greater than the one passed in the request.
7. If "phone" is passed, then filter by 100% similarity.
8. If “name” is passed, then filter by like with the format ‘{text-from-request-param}%’
9. If "email" is passed, then filter by 100% similarity.
10. Add JWT token (required by Claim only USER_ID), the mechanism for obtaining a token is up to you. Implementation is as simple as possible, do not complicate. Authentication can be by email+password or by phone+password.
11. Once every 30 seconds, the BALANCE of each client is increased by 10% but not more than 207% of the initial deposit.

*For example: It was: 100, it became: 110. It was: 110, it became: 121.…Final value: 194.87*

1. Make the functionality of transferring money from one user to another.

Input: USER_ID (transfer from) - we take from the token authorized from Claim, USER_ID (transfer to) from the request, VALUE (transfer amount) from the request.

That is, we write off this amount from the one who transfers, and add this amount to the one to whom we transfer. Consider this operation "banking" (highly significant), 
make it with all the necessary validations (you need to think about what) and stream-protected.

1. The creation of a new user must occur by accepting a message from RabbitMQ

**Not mandatory features (but which I would really like to see, at least in a minimal version):**

1. Add swagger.
2. Add meaningful logging.
3. Add correct caching (the API and the DAO layer). Implementation is up to you.

**Testing:**

Unit test coverage. No need to cover all code with tests. You need to make tests to cover the money transfer functionality through MockMvc and cover the user creation operation through testcontainers.

