# GamePoint Devops Engineer test task

Please setup Building / Deployment pipeline for the given project

# Source Project

The project sources are located at: https://github.com/GamePoint-LG/devops_test.git
Project itself is a web application generated with start.spring.io.
There are two branches in repository - **master** and **develop**.

## Build prerequisites 
* maven
* java

## Build instructions
`mvn clean install` 

Result artifact will be stored in `./target` folder after successful build process.

## Run instructions 
`java -jar application_name.jar`

## Expected behavior
Web app is up and listening on **8080** port. It is available at http://localhost:8080/up
If service was built from master branch it will respond with **200 HTTP** code and **UP** text in response body.
In case of develop branch it will respond with **500 HTTP** code and error message in response body.

# Building setup requirements

Building / Deployment pipeline must be set up using Jenkins or TeamCity.
Building must be triggered on each commit event. Please ensure the ability to start parallel builds in case of concurrent commits and available workers on build .

Build artifacts must be stored using Artifactory repository manager.
Artifacts shouldn’t overwrite each other in case of building from different branches.

# Deployment setup requirements

Deployment must be implemented in Jenkins or TeamCity with an option to choose available built artifact version.
In case of server start failure the ability of rollback to previous successful version of server must be foreseen.

## Possible upgrade scenario
* Build and Deploy app **master** branch
* Ensure service is working (**200 HTTP** code is being returned)
* Build and Deploy app from **develop** branch
* Ensure service is working - if service returns **500 HTTP** error on health check then return master version back.

# Additional task (bonus)
Implement Deployment to 2 servers with load balancer in front of them to avoid downtime during servers update process.
