# Project: Social media blog API


## Background 

This project is backend for a hypothetical social media app, where we must manage our users’ accounts as well as any messages that they submit to the application. The application will function as a micro-blogging or messaging app. In our hypothetical application, any user is able to see all of the messages posted to the site, and they can see the messages posted by a particular user. It isbackend which is able to deliver the data needed to display this information as well as process actions like logins, registrations, message creations, message updates, and message deletions. This project uses Java's Javilin framwork.



## Database Tables 

These are provided in a sql script, and a ConnectionUtil class that will run the sql script is provided:

### Account
```
account_id integer primary key auto_increment,
username varchar(255) not null unique,
password varchar(255)
```

### Message
```
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```

# Setup

The folowing steps are how to get started running this project  

**Step 1:** Fork and/or download this project    

**Step 2:** Download and install something like postman to make Http requests with. You can install postman from here https://www.postman.com . See the section where it says **Download the Desktop App for**. The lightweight version should be enough (no need to sign up for an account). For a tutorial on postman see here https://www.youtube.com/watch?v=wmz1sGZp814

**Step 3:** Open up this project in your editor of choice, though I recommend using Intellij https://www.jetbrains.com/idea/. Go to src --> main --> java and run the Main.java file  

## EndPoints
The following are the endpoints exposed in this project. Port 8080 is used.  

### GET Endpoints
/messages  
/messages/{message_id}   
/accounts/{account_id}/messages    

### Post Endpoints
/register  
/login   
/messages    

### Patch Endpoints
/messages/{message_id}  

### Delete Endpoins
/messages/{message_id}

# Contibutors
Olspe

# License
MIT License
