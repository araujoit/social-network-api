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
      stage('Package') {
         steps {
            // Generate runnable Jar
            dir('api') {
               sh "./gradlew clean shadowJar"
            }
         }
      }
   }
   post {
        always { 
            // Archive jar
            archiveArtifacts artifacts: 'api/build/libs/**/*.jar', fingerprint: true

            //Send an email to the person that broke the build
            step([$class                  : 'Mailer',
                  notifyEveryUnstableBuild: true,
                  recipients              : [emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])].join(' ')])
        }
    }
}