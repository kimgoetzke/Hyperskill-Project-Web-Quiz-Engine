# The Web Quiz Engine Project
This repo contains my second project in Java. It was created in January 2023 for the 'Java Backend Developer' track on Hyperskill (JetBrains Academy).

**About the course**: [https://hyperskill.org/tracks/12](https://hyperskill.org/tracks/12)<br>
**About the project**: [https://hyperskill.org/projects/91](https://hyperskill.org/projects/91)<br>
**Difficulty**: Challenging


In this project, I have developed a multi-users web service for creating and solving quizzes. Some key features include:

+ An API with a Spring Boot REST controller offering endpoints for creating users and adding/requesting/completing/deleting quizzes
+ Pagination for responses returning more than 10 results e.g. all questions or all individual completions
+ Using Sprint Crypto to implement BCrypt encoding for passwords
+ Using Sprint Security to allow only authorised users access (except for user registration)
+ A locally stored H2 database to store quizzes
+ Processing and returning data in JSON format


## Example
![image](https://user-images.githubusercontent.com/120580433/213090276-f549982f-50dd-4647-8d3b-f436b5482f9c.png)


## License

[MIT](https://choosealicense.com/licenses/mit/)
