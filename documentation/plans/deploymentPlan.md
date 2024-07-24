# Deployment Plan 

As our deployment process for “Record Shop”, we have elected to use a Continuous Integration and Continuous Delivery pipeline.

We have identified the following stages:

**Stage 1: Source**

- We have used Github to compile our source code using version control and branching 
- We have used both manual testing and unit testing to create a minimal viable product 
- Once we achieved an MVP and over 70% coverage in our unit tests, we created a Jenkinsfile / pipeline file using Github Actions. 
- This file can be viewed at ".github/workflows/maven.yml"
- This is triggered on each new pull request to main 
- As we have continued developing our project, this pipeline has allowed us to run automated tests/checks to ensure all merged code is up to appropriate standard and quality.
- At this stage, we have also created a draft Jenkinsfile template below - but have not had the resources to integrate it into our application: 

```groovy
pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Deploy') {
      steps {
        sh './deploy.sh'
      }
    }
  }
}
```

**Stage 2: Build**

- The next step is containerisation 
- We have created a JAR file, and used have used this and docker-compose.yml to generate Docker images for "group-project" and "mysql", and a Docker container
- As part of this, we ran further automated tests using maven to check the code is up to standard and correct

**Stage 3: Test**

- We will now perform further testing including: 
1. Integration tests (do all parts of the application work together seamlessly?)
2. End-to-end testing (does it work as expected from an end-users perspective?)
3. Performance / stress testing (can it handle the anticipated load?)
4. Security testing (what are the vulnerabilities and security risks of the application? How can these be addressed?)

**Stage 4: Deploy**

- We will now release the application into a production environment 
- We would consider the following options: 
  1. Hosting it on a Cloud service like Google Cloud 
  2. Deploying our Docker container to a Kubernetes cluster 