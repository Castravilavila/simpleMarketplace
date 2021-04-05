# simpleMarketplace

Simple Api for market that contains users and Products.

To be able to run this program you should have a postgresql database and a public schema.

You should add 2 rows in the Role table:
ROLE_USER and ROLE_ADMIN

After that you can run the program.

-----------------------------------------------------------------------------
To be able to use the program you should authenticate:

-In postman do a POST request to: http://localhost:8080/api/auth/signup

with the body of a user:
{
            "username": "test",
            "email": "test@test.com",
            "password": "test"
        }

doing this you will create a user in data base with a hashed password with wich you will be able to login

----------------------------------------
To login you use the credentials of user you created to this POST api: http://localhost:8080/api/auth/signin

{
            "usernameOrEmail": "test",
            "password": "test"
}

After login you will get a token that will look like this:

    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE3NjQzMDkxLCJleHAiOjE2MTc2NjQ2OTF9.KhWUKpIRCCtzl0TZIrc0Y1eycl6UPhute6-oKqvuRqsn8s2929vdtpwUVz9fPB6Hlcqcv6r_sGU19KrgEDATAQ",
    "tokeType": "Bearer"
    
Add it to the postman headers like this:
`
KEY: authorization     
VALUE: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjE3NjI3MDI0LCJleHAiOjE2MTc2NDg2MjR9.F8itYvnUpYDSE5zeBcWhEbD9US7gpokdhTZyQ6fDwV5eZOXrN7ElCCWiEi-Yz7vWtOntswF9aRR1dBHPph8Rgg
` 

`http://localhost:8080/v2/api-docs` --> for Accessing api documentation

