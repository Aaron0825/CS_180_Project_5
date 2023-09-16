# **Test Cases**

**Provided Account Information:**
```
Teacher Username: john
Teacher Password: 123

Student Username: mary
Student PUID(password): 0987654321
Student emal: mary@purdue.edu
```

### Test 1 (Teacher Log In):
> 1. User launches application
> 2. User selects teacher
> 3. User selects log in
> 4. User enters the given teacher username
> 5. User hits the ok button
> 6. User enters the given teacher password
> 7. User hits ok
```
Expected Result: Application verifies the user's username and password which takes the user to the teacher homepage
Test Status: Passed ✅
```

### Test 2 (Student Log In):
> 1. User launches application
> 2. User selects Student
> 3. User selects log in
> 4. User enters the give student username
> 5. User hits the ok button
> 6. User enters the given student password
> 7. User hits ok
```
Expected Result: Application verifies the user's username and password which takes the user to the student homepage
Test Status: Passed ✅
```

### Test 3 (Teacher Create Account):
> 1. User launches application
> 2. User selects Teacher
> 3. User selects create account
> 4. User types in username and hits ok
> 5. If it's a valid username user types in password and hits ok (if not a valid username error message is given and application returns to start)
```
Expected Result: Application gives a success message and account info is added to Teacher.txt and a new file is made for the teacher 
(formatted: username_Courses.txt) where courses and quizzes will be added
Test Status: Passed ✅
```

### Test 4 (Student Create Account):
> 1. User launches application
> 2. User selects Student
> 3. User selects create account
> 4. User chooses username and hits ok
> 5. Once the username has been verified to be the correct format, user should choose password
> 6. Once password has been verified to be the correct format user should hit ok
```
Expected Result: Application verifies the user's desired username and password and creates a file in the directory
Test Status: Passed ✅
```
### Test 5 (Student Take Quiz):
````
.txt file for attaching a file as a response: A.txt
````
1. User selects Take Quiz option
2. User is prompted to the Quiz GUI
3. User manually enters a response in the text box, they can enter anything they want including a .txt file to attach a file as a response
   1. if user decides to type a .txt file, they must include the file in the src directory. 
4. After entering their response, they press enter.
5. User is taken to a message screen that shows time and date submitted. 
6. If user types a file that doesn't exist, they are shown an error message in which the user has to take the quiz again with a valid .txt file that exists. 
```
Expected Result: Submission GUI that shows time and date, and writes a file storing the user's answers.
                 (i.e.mary_Spongebob_Math.txt)
Test Status: Passed ✅
```

### Test 6 (Teacher Create Course):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 1 to enter course menu
> 4. User enters 1 to select create course
> 5. User inputs course name to be added
```
Expected Result: Application shows a message whether the course was added or couldn't be added due to the course already existing. 
If added the course is added to teacher's file (ex. john_Courses.txt)
Test Status: Passed ✅
```

### Test 7 (Teacher Remove Course):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 1 to enter course menu
> 4. User enters 2 to select remove course
> 5. User inputs course name to be removed
```
Expected Result: Application shows a message whether the course was removed or couldn't be removed due to the course not existing. 
If removed the course is removed from the teacher's file (ex. john_Courses.txt) and removed from all the students' files who are in the course (ex. mary-student.txt).
Test Status: Passed ✅
```

### Test 8 (Teacher View Course):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 1 to enter course menu
> 4. User enters 3 to select view course
```
Expected Result: Application gives a pop up showing all the teachers current courses and the current quizzes in those courses.
Test Status: Passed ✅
```

### Test 9 (Teacher Add Student to Course):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 1 to enter course menu
> 4. User enters 4 to select create course
> 5. User inputs course name
> 6. If course exists user inputs student email (if course doesn't exist it gives an error message and goes back to main menu)
```
Expected Result: If student account exists the course and its current quizzes are added to the student file(ex. mary-student.txt) 
and student email is added to teacher file(ex. john-Courses.txt). If student account doesn't exist error message is given and it returns to main menu.
Test Status: Passed ✅
```

### Test 10 (Teacher Create Quiz from a file):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter quiz menu
> 4. User enters 1 to select create quiz
> 5. User enters 1 to select From a File
> 6. User inputs file name
> 7. If file exists user inputs course name (if file doesn't exist it gives an error message and goes back to main menu)
```
Expected Result: If course exists application shows a message whether the quiz was added or couldn't be added due to the quiz 
already existed. If added the quiz is added to teacher's file (ex. john_Courses.txt)
Test Status: Passed ✅
```

### Test 11 (Teacher Create Quiz manually):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter quiz menu
> 4. User enters 1 to select create quiz
> 5. User enters 2 to select Manually
> 6. User inputs course name to add the quiz in
> 7. If course exists user inputs quiz name to be added (if course doesn't exist it gives an error message and goes back to main menu)
> 8. User inputs question to be added
> 9. User inputs first choice to be added
> 10. User inputs second choice to be added
> 11. User inputs third choice to be added
> 12. User inputs fourth choice to be added
```
Expected Result: Application shows a message whether the quiz was added or couldn't be added due to the quiz already existing. 
If added the quiz is added to teacher's file (ex. john_Courses.txt)
Test Status: Passed ✅
```

### Test 12 (Teacher Add Question to Quiz):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter quiz menu
> 4. User enters 2 to select edit quiz
> 5. User enters 1 to select add question
> 6. User inputs course name to edit the quiz in
> 7. If course exists user inputs quiz name to be edited (if course doesn't exist it gives an error message and goes back to main menu)
> 8. If quiz exists user inputs question to be added (if quiz doesn't exist it gives an error message and goes back to main menu)
> 9. User inputs first choice to be added
> 10. User inputs second choice to be added
> 11. User inputs third choice to be added
> 12. User inputs fourth choice to be added
```
Expected Result: Application shows a message whether the question was added or couldn't be added due to the question already existing. 
If added the question is added to quiz's file (ex. Spongebob_Quiz.txt)
Test Status: Passed ✅
```

### Test 13 (Teacher Remove Question from Quiz):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter quiz menu
> 4. User enters 2 to select edit quiz
> 5. User enters 2 to select remove question
> 6. User inputs course name to edit the quiz in
> 7. If course exists user inputs quiz name to be edited (if course doesn't exist it gives an error message and goes back to main menu)
> 8. If quiz exists user inputs question to be removed (if quiz doesn't exist it gives an error message and goes back to main menu)
```
Expected Result: Application shows a message whether the question was removed or couldn't be removed due to the question doesn't exist. 
If removed the question is removed from quiz's file (ex. Spongebob_Quiz.txt)
Test Status: Passed ✅
```

### Test 14 (Teacher Edit Choice of Question):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter quiz menu
> 4. User enters 2 to select edit quiz
> 5. User enters 3 to select edit choices
> 6. User inputs course name to edit the quiz in
> 7. If course exists user inputs quiz name to be edited (if course doesn't exist it gives an error message and goes back to main menu)
> 8. If quiz exists user inputs question to be edited (if quiz doesn't exist it gives an error message and goes back to main menu)
> 9. If question exists user select choice to be edited (if question doesn't exist it gives an error message and goes back to main menu)
> 10. User inputs choice to edit choice
```
Expected Result: Application shows a message of the question is edited, the changes are recorded in quiz's file (ex. Spongebob_Quiz.txt)
Test Status: Passed ✅
```

### Test 15 (Teacher Remove Quiz):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter quiz menu
> 4. User enters 3 to select remove quiz
> 5. User inputs course name in which quiz to be removed
> 6. If course exists user inputs quiz name to be removed
```
Expected Result: Application shows a message whether the quiz was removed or couldn't be removed due to the quiz not existing. 
If removed the quiz is removed from the teacher's file (ex. john_Courses.txt) and removed from all the students' files who are in the course (ex. mary-student.txt).
Test Status: Passed ✅
```

### Test 16 (Teacher View Quiz):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter course menu
> 4. User enters 4 to select view quiz
> 5. User inputs course name in which quiz to be viewed
> 6. If course exists user inputs quiz name to be viewed
```
Expected Result: Application gives a pop up showing the content of the named quiz.
Test Status: Passed ✅
```

### Test 17 (Teacher See Student Submission):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter course menu
> 4. User enters 6 to select see submission
> 5. User inputs student's email whose submission to be viewed
> 6. User inputs student's PUID whose submission to be viewed
> 7. If student exists user input course name in which submission to be viewed
> 8. If course exists user inputs quiz name in which submission to be viewed
```
Expected Result: If student took the quiz application gives a pop up message showing the content of the student's submission, 
else the pop up message shows that the student didn't take the quiz. 
Test Status: Passed ✅
```

### Test 18 (Teacher Grade Quiz):
> 1. User launches application
> 2. User logs in as a teacher (Test 1)
> 3. User enters 2 to enter course menu
> 4. User enters 5 to select grade quiz
> 5. User inputs student's email whose quiz to be graded
> 6. User inputs student's PUID whose quiz to be graded
> 7. If student exists user input course name in which quiz to be graded
> 8. If course exists user inputs quiz name to be graded
> 9. User sees the student's submission (his/her answers)
> 10. User inputs the highest possible grade of the quiz (ex. 100)
> 11. User inputs the student's grade of the quiz
```
Expected Result: Application gives a pop up message showing the quiz is graded. The grade of the student is written to a grade file (ex. mary_Tree_Math_Grades.txt).
Test Status: Passed ✅
```
