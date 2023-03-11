# techman

This project can be used as a starting point to create your own Vaadin application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8085 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different IDEs](https://vaadin.com/docs/latest/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/techman-1.0-SNAPSHOT.jar`

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.


![Diagrama entidade relacionamento](https://user-images.githubusercontent.com/99774507/224511658-c844ba90-af0d-4bcc-bb68-85321cee129d.jpg)
![image](https://user-images.githubusercontent.com/99774507/224511676-fb4d2106-539c-44d6-a80c-e0b2afd270cf.png)
![image](https://user-images.githubusercontent.com/99774507/224511741-7c5a610a-f0de-4d32-beda-4dac8117e307.png)
![image](https://user-images.githubusercontent.com/99774507/224511777-89b74d60-eaff-48bc-a60e-575cd995513d.png)
![image](https://user-images.githubusercontent.com/99774507/224511783-1e7fcac4-8165-476d-89b5-046ba453a054.png)
![image](https://user-images.githubusercontent.com/99774507/224511790-f737d5f0-5cda-44b1-9547-e85cfb7292e4.png)
![image](https://user-images.githubusercontent.com/99774507/224511801-9b6c0c34-98ad-47eb-9315-93b16b1e6384.png)
![image](https://user-images.githubusercontent.com/99774507/224511829-46f7e40e-26b3-4ace-a6b0-a08a17c02726.png)
![image](https://user-images.githubusercontent.com/99774507/224511843-d16b2240-dd8d-44bc-be7d-455faef0e889.png)





