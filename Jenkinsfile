pipeline {
   agent any

   stages {
      stage('Checkout') {
         steps {
            // Get some code from a GitHub repository
            git 'https://github.com/araujoit/social-network-api.git'
         }
      }
      stage('Build') {
         steps {
            // Run Tests
            dir('api') {
                sh "./gradlew build"
            }
         }
      }
      stage('Test') {
         steps {

            // Run Tests.
            dir('api') {
                sh "./gradlew test"
            }
         }
      }
   }
}