# The Web Quiz Engine Project
This repo contains my second project in Java. It was created in January 2023 for the 'Java Backend Developer' track on Hyperskill (JetBrains Academy).

**About the course**: [https://hyperskill.org/tracks/12](https://hyperskill.org/tracks/12)<br>
**About the project**: [https://hyperskill.org/projects/91](https://hyperskill.org/projects/91)<br>
**Hyperskill project difficulty rating**: Challenging


In this project, I have developed a multi-users web service for creating and solving quizzes. Some key features include:

+ An API with a Spring Boot REST controller offering endpoints for creating users and adding/requesting/completing/deleting quizzes
+ Pagination for responses returning more than 10 results e.g. all questions or all individual completions
+ Using Sprint Crypto to implement BCrypt encoding for passwords
+ Using Sprint Security to allow only authorised users access (except for user registration)
+ A locally stored H2 database to store quizzes
+ Processing and returning data in JSON format


## Example
![image](https://user-images.githubusercontent.com/120580433/213090276-f549982f-50dd-4647-8d3b-f436b5482f9c.png)

## Available endpoints

#### Register user
POST `api/register`
+ Expects JSON with `email` (valid email) and `password` (5 character minimum) 
+ Example: 
`{
  "email": "test@email.com",
  "password": "password"
}`
+ Returns HTTP status code (`200` or `400`) based on success

#### Create quiz
POST `api/quizzes`
+ Expects JSON with `title`, `text` (i.e. displayed question), `options` (min 2), and `answer` (0 possible) and authorisation
+ Example: 
`{
  "title": "A question with two correct answers",
  "text": "2 + 2 = ?",
  "options": ["2","1 * 4","6 + 2","5 - 1"],
  "answer": [1,3]
}`
+ If successful, returns JSON with all submitted fields plus `id` and HTTP status `200`, otherwise `401` if unauthorised

#### Solve quiz
POST `api/quizzes/{id}/solve`
+ Expects JSON with `answer` (can be empty) and authorisation
+ Example:
`{
  "answer": [1,3]
}`
+ If successful, returns JSON with `success` true/false and `feedback` and HTTP status `200`, otherwise `401` if unauthorised

#### Get quiz
GET `api/quizzes/{id}`
+ Expects authorisation, no body required
+ Returns JSON with `id`, `title`, `text`, `options` and HTTP status `200` if successful, or `401`/`404` if unauthorised/not found

#### Get all quizzes
GET `api/quizzes`
+ Expects authorisation, no body required
+ Accepts `?page=` and `?size=` (number of items per page returned)
+ Returns sorted and paginated JSON with all quizzes and HTTP status `200` if successful, or `401` if unauthorised

#### Get all completions
GET `api/quizzes/completed`
+ Expects authorisation, no body required
+ Accepts `?page=` and `?size=` (number of items per page returned)
+ Returns sorted and paginated JSON with all completions for requestor and HTTP status `200` if successful, or `401` if unauthorised

#### Delete quiz
DELETE `api/quizzes/{id}`
+ Expects authorisation, no body required
+ Returns HTTP status `204` if successful, or `403`/`404` if not the owner/not found
