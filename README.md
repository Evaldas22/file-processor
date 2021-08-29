# file-processor
An application capable of parsing any amount of text files and categorizing words into predefined categories

Project is written using Java 11, but can be ran with Java 8, by changing version in pom.xml to 1.8

![image](https://user-images.githubusercontent.com/16511316/131259796-20937ec5-46ca-4f4a-be93-03f34a4dcc64.png)


Spring boot application has a swagger configured, which can be accessed via:
http://localhost:8080/swagger.html

## Instructions to run the app
1. Clone the repo
2. Navigate to client directory and run `npm install`
3. After install is done, run `npm start` to launch the web app
4. Open server directory as a project in your favorite IDE
5. Run `FileProcessorApplication` main class to launch the back end

Be aware that the result files will be saved in your operating systems temporary directory.

Also while testing on Windows 10 I got firewall prompt, so don't be surpised. I got this both for Node and Java ![image](https://user-images.githubusercontent.com/16511316/131259635-941c0842-f086-4eda-bf6f-344c824a6a5b.png)
