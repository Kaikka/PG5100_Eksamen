#PG5100_Exam

###How to
You can access the server at `http://localhost:8080` after starting it with `LocalApplicationRunner`, located in `PG5100_Eksamen\frontend\src\test\java\no\kristiania\frontend\`

###Solution
The backend consists of 3 entities and one embeddable ID-class for connecting reviews with both user and movie. There are also services for each of the entities, allowing for wanted features, such as creating movies. In a larger project with intent to publish, or with more time, entities would have more info, but it said in requirement R1 that this was up to me. 

The frontend consists of wanted web pages such as homepage and login/signup-page, but also a page to create movies. *Stars* is mostly referred to as *rating*, except where you can write review.


###Requirements
All requirements have been finished with one extra feature, see below.

###Extra
- Possible to add new movies through frontend, but only when logged in. Also includes selenium test for it.

###Use of code from tsdes
In general, a lot of the code is adapted in some way from Andrea's course repository;
- Pom files 
- Application, LocalApplicationRunner, RedirectForwardHandler
- Entities
- Controllers
- DefaultDataInitializerService

Many things such as `deleteMovie()` are inspired by code from the course, but also from my own exercise solutions and attempted mock-exam.


###Tests
All testing was done with embedded db, no docker.
- Over 90% coverage with JaCoCo on both backend and frontend.
- Uses PageObjects
- No tests for getters and setters, as this is redundant. (Source: Anders during lecture)

I'm unhappy with the Selenium-tests. I had some troubles when trying to re-use the setup from the course's repo, and had to go with a bit different code instead. Which seems to not be best practice, but at least they run, both in intelliJ and mvn cmd. Hopefully there's no difference on other systems. 