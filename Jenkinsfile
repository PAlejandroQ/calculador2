pipeline { 
     agent any 
     
     triggers{
	pollSCM('* * * * *')
}
     stages { 
          stage("Compile") { 
               steps { 
                    sh "./gradlew compileJava" 
               } 
          } 
          stage("Unit test") { 
               steps { 
                    sh "./gradlew test" 
               } 
          }
          stage("Code coverage") { 
               steps { 
                    sh "./gradlew jacocoTestReport" 
                    sh "./gradlew jacocoTestCoverageVerification" 
               } 
          } 
          stage("Static code analysis") { 
               steps { 
                    sh "./gradlew checkstyleMain" 
               } 
          }
	  post {
                always {
                    mail to: 'ppaabblloo4283@gmail.com',
                    subject:"Completed Pipeline: ${currentBuild.fullDisplayName}",
                    body:"Your build completed, please check: ${env.BUILD_URL}"
                }
                failure {
                    slackSend channel: '#sprint',
                    color: 'danger',
                    message:"The pipeline ${currentBuild.fullDisplayName} failed."
                }
           } 
     } 
} 
