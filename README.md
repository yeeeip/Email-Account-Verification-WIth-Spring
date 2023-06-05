# Email-Account-Verification-WIth-Spring
This Spring Boot app demonstrates the process of registering to the system and confirming your account via the email message

In order to start this app you need to have PostgreSQL server listening on port 5432.
After starting the project open Postman and send POST request with data to the following address: http://localhost:8080/api/v1/registration

![2023-06-05_23-24-04](https://github.com/yeeeip/Email-Account-Verification-WIth-Spring/assets/81825828/78335b2d-e086-441d-9a54-c7452e065341)

Response should be as following:

![2023-06-05_23-35-13](https://github.com/yeeeip/Email-Account-Verification-WIth-Spring/assets/81825828/4c9925c8-fed4-474a-8b2d-44db410cd082)

After that you should receive the account confirmation letter on your email (in case you registered with existing email)

![2023-06-05_23-57-03](https://github.com/yeeeip/Email-Account-Verification-WIth-Spring/assets/81825828/920de8cc-59aa-4f7b-bee3-cb83a8af2dee)
Click on link from the letter and confirm your account

If you registered with wrong or unexisting email - don't worry. Just copy the token from the response and follow th link: http://localhost:8080/api/v1/registration/confirm?token=<paste_your_token>

After account confirmation you can go to the http://localhost:8080/login and login with your email and password
