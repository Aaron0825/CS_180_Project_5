# CS 180 Project 💻

## Submissions ✅
1. Margaret Haydock- submitted code on Vocaruem
2. Chun-Yang Lee - submitted report on Brightspace
3. Joshua Ho - submitted presentation on Brightspace

## Instructions 📝
When you open up the project, run client.java. 
Next, pull up the Test.md to test out the project. 

## Classes 
| Class Names | Description |
| ----------- | ------------- |
| Choice      | Create a choice for a multiple choices question; contains getter and setter utilized to add, remove, and edit choice in Question class |
| Question    | Create a multiple choices question; contains add, remove, edit choice method and a ramdomize choices method utilized in Teacher class |
| Quiz        | create a quiz containing one or more multiple choices questions with answers to the questions; contains add, remove, edit question method and a ramdomize questions method utilized in Teacher class, answers to the questions are not shown to Student class but can be used by Teacher class for grading |
| Course      | reate a course containing one or more quizzes; contains add, remove, edit quiz method and write quiz file method that can store a quiz content in a file so data won't be lost when a user disconnects, the methods are utilized in Teacher class |
| Teacher     | Creates a new teacher object that have a username, an array of courses, and a password. Each teacher can add courses to their array of courses and then they can add quizzes to those courses. The teacher can then remove quizzes or edit quizzes in specific courses. Any edits or additions of quizzes are rewritten in a quiz file using Course.java's method writeQuizFile. Teachers can also see a student's submission after a student takes a quiz. They can then grade a student's submission by saying how many total points are in a quiz and then assigning a score to each individual question in a quiz based off the students submission. This then writes a file using Student.java in order for students to see their grades. |
| Student     | Allows the user to log in to their student account or create a student account. Once the user logs in, the user has two options: course selection or delete account. The delete Account is self explanatory; however, course selection shows you the courses that the user is enrolled in. The user then can select a course and the course will pull up a list of quizzes pertaining to the course. When the student takes the course, they take the quiz and after the user is finished, the program will show the time stamp, if they successfully submitted or not, and the the program will end after that. |
| Client      | The client class is the client side of the program where it would send information to the server.  |
| Server      | The server class is the server part of the program it would process information sent from the client.  |
