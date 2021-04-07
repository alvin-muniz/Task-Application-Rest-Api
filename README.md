f# ![](https://ga-dash.s3.amazonaws.com/production/assets/logo-9f88ae6c9c3871690e33280fcf557f33.png) Create a Todo App Lab

## Opening

We're going to create a todoApp during this Spring week. You'll continue to add functionality to your app as we learn
more about Spring and its modules.

This will be a fairly open-ended lab. We want you to be able to think through the design decisions and understand the
reason why you choose one thing over the other. There's just one main ground rule to follow: Make sure you only work
with the technologies/tools we covered during the lesson.

##Task Application
This task application is based off of Spring and will utilize a REST principles to provide an API to create, read, update and delete task.

###Defining The Domain
The domain in this application will consist of a Task object to capture bare essentials needed for a simple task manager.

##Setting up environment
I needed to add the Postgres Driver:

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/task
spring.datasource.username=postgres
spring.datasource.password=password

and below dependency:

<dependency>
<groupId>org.postgresql</groupId>
<artifactId>postgresql</artifactId>
<scope>runtime</scope>
</dependency>

##Defining Entity and Table Relationships
@Entity Annotation and @Table(name = "tasks")

    @Id

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private boolean isCompleted;

    @Column
    private String description;

##Spring Boot Magic

The below query found under the Task repo initially gave me an error.
The error stated that the property was not found.
I realized that I needed to change the descriptor, the title to be one of my properties in my file
This change was necessary and suprising as it meant that Spring Boot actively searched my project for those 
property names and queried them accordingly. Magic.

Task findByTitle(String taskName);


##Project Class
The project class is a 'wrapper' to encapsulate the tasks as well as provide further higher level project specific details
The model is simple:


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    @Column
    private Priority priority;


    @OneToMany(mappedBy = "project")
    private List<Task> taskList;

This will have a bi direction one to many relationship with the tasks.

Makes sense! You have a project and tasks related to it!

##Refactor
Because the tasks are within the project, I need to refactor the end points, so that we are navigating down the tree:
localhost:{PORTNUMBER}/projects/{projectId}/tasks/{taskId}

##Infinite Loop caused by bi-directional one to many
I ran into an error where I was receiving an infinite loop in my api tests when adding a new task to a project.

    @OneToMany(mappedBy = "project")
    private List<Task> taskList;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

The models were linked per the attached. Upon researching, I found that adding the @JSON Ignore Annotation helped resolve the issue, specifically on the project model like so:

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;


##Priority Enum
A key point of the application is to also include the Priority Leve of the project. For this I used an Enum class with the appropriate priority levels.

##Time to Refactor 
I now have a Project controller and a Task controller. I am going to refactor the code so that the end points read like this:
localhost:{PORTNUMBER}/projects/{projectId}/tasks/{taskId}
This makes logical sense from a user standpoint.

##Recursion and Return Types
A couple of interesting things happened in my update project method.
I learned that a date object returned from the database via our repository is a different, albeit similarly named class than the data type used in the model.
project End Date
2012-12-17 java.sql.Date
projectObject End Date
Sun Dec 16 18:00:00 CST 2012 java.util.Date

While on the Java end the date is shown as Java util.date, when retrieved via the API it is of java.sql.Date type.

Slightly different but different enough so that the standard comparison methods will not work!


    @PutMapping(path = "/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId, @RequestBody Project projectObject){
        System.out.println("Updating a Project with ID " + projectId);
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isPresent()){
            System.out.println("project End Date");
            System.out.println(project.get().getEndDate() + " " + project.get().getEndDate().getClass().getName());
            System.out.println("projectObject End Date");
            System.out.println(projectObject.getEndDate() + " " + projectObject.getEndDate().getClass().getName());
            if(project.get().getName().equals(projectObject.getName())){
                throw new InformationExistsException("No updates here");
            }else{
                  return projectRepository.save(projectObject);
            }
        }else{
            throw new InformationNotFoundException("This project does not exists");
        }
    }


### Step 3 (Spring Data)

Create `Category` and `Item` model. You'll use PostgreSQL as your database and Spring Data to talk to your database.

This step will be done in two parts.

#### 3(a)

In this step, you'll only create a `Category` model.

- The `Category` should have at least two fields: `name` and `description`.
- You'll create full CRUD endpoints for `Category` controller.

Let's test all the endpoints

| Request Type | URL| |--|--| | GET | /api/categories/ | |POST|/api/categories/| |GET|/api/categories/{categoryId}|
|PUT|/api/categories/{categoryId}| |DELETE|/api/categories/{categoryId}|

#### COMMIT MESSAGE: Step 3A - COMPLETED

----

#### 3(b)

In the next step, you'll add the `Item` model and map it with `Category`.

- The `Item` table should have at least three columns: `name` and `description`, and `dueDate`.
- The `Category` and `Item` tables should be mapped to each other.
- You'll create full CRUD endpoints for `Item` controller.

| Request Type | URL| |--|--| | GET | /api/categories/ | |POST|/api/categories/| |GET|/api/categories/{categoryId}|
|PUT|/api/categories/{categoryId}| |DELETE|/api/categories/{categoryId}| |GET|/api/categories/{categoryId}/items|
|POST|/api/categories/{categoryId}/items| |GET|/api/categories/{categoryId}/items/{itemId}|
|PUT|/api/categories/{categoryId}/items/{itemId}| |DELETE|/api/categories/{categoryId}/items/{itemId}|

#### COMMIT MESSAGE: Step 3B - COMPLETED

----

#### 3(c)

In the next step, you'll add the `User` model and map it with `UserProfile`.

- The `User` table should have at least three columns: `userName` and `emailAddress`, and`password`.
- The `UserProfile` table should have at least three columns: `firstName` and `lastName`, and`profileDescription`.
- The `User` and `UserProfile` tables should be mapped to each other (1:1).
- Under the UserController, you'll create register endpont and block all the other URLs

TEST: http://localhost:8080/auth/users/register

#### COMMIT MESSAGE: Step 3C - COMPLETED

----

### Step 4 (Spring Security)

You'll now add user authentication and authorization to your existing app, using JSON Web Tokens (JWT) to authenticate
your requests. You already have a `User` model for security purposes.

- Again, make sure to use only JWT for authentication.
- Only login API endpoint will only return a JWT in response.
- All other requests will use that token for authentication.

### Functionality(endpoints)

Endpoint | Functionality| Access
------------ | ------------- | ------------- 
POST /auth/users/register | Registers a user | PUBLIC
POST /auth/users/login |Logs a user in | PUBLIC
GET /api/categories | Lists all categories | PRIVATE
GET /api/categories/{categoryId} | Gets a single category with the suppled id | PRIVATE
POST /api/categories | Creates a new category | PRIVATE
PUT /api/categories/{categoryId} | Updates a category with the suppled id | PRIVATE
DELETE /api/categories/{categoryId} | Deletes a category with the suppled id | PRIVATE
POST /api/categories/{categoryId}/items | Creates a new item in the given category | PRIVATE
GET /api/categories/{categoryId}/items | List all item in the given category | PRIVATE
PUT /api/categories/{categoryId}/items/{itemId}| Updates a item in the given category | PRIVATE
DELETE /api/categories/{categoryId}/items/{itemId} | Deletes a item in the given category | PRIVATE

#### COMMIT MESSAGE: Step 4 - COMPLETED
