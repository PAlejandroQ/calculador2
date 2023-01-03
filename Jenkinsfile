pipeline {
	agent any
	triggers {
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
			publishHTML (target: [
			reportDir: 'build/reports/jacoco/test/html',
			reportFiles: 'index.html',
			reportName:"JaCoCo Report"
			])
			sh "./gradlew jacocoTestCoverageVerification"
		    }
        	}
		stage("Static code analysis") {
		    steps {
			sh "./gradlew checkstyleMain"
			publishHTML (target: [
			reportDir: 'build/reports/checkstyle/',
			reportFiles: 'main.html',
			reportName:"Checkstyle Report"
			])
		    }
		}
		stage("Package") {
			steps {
			sh "./gradlew build"
			}
		}
		stage("Docker build") {
			steps {
			sh "docker build -t alejandroqo/calculator ."

			}
		}
		stage("Docker push") {
			steps {
				sh "docker push alejandroqo/calculador"
			}
		}
		stage("Deploy to staging") {
			steps {
				sh "docker run -d --rm -p 8765:8080 --name calculador alejandroqo/calculator"
			}
		}
		stage("Acceptance test") {
			steps {
				sleep 60
				sh "chmod +x acceptance_test.sh && ./acceptance_test.sh"

			}
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
		
			always {
			sh "docker stop calculator"
			}
		}
	}
