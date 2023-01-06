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
			sh "docker build -t alejandroqo/calculador:${BUILD_TIMESTAMP} ."

			}
		}
		stage("Docker push") {
			steps {
				sh "docker push alejandroqo/calculador:${BUILD_TIMESTAMP}"
			}
		}
		stage("Update version") {
               		steps {
                    		sh "sed  -i 's/{{VERSION}}/${BUILD_TIMESTAMP}/g' deployment.yaml"
               		}
          	}
		stage("Deploy to staging") {
			steps {
				//sh "docker run -d --rm -p 8765:8080 --name calculador alejandroqo/calculador:${BUILD_TIMESTAMP}
			sh "kubectl config use-context kind-staging"
			sh "kubectl apply -f hazelcast.yaml"
			sh "kubectl apply -f deployment.yaml"
			sh "kubectl apply -f service.yaml"
			}
		}
		stage("Acceptance test") {
			steps {
				sleep 60
				//sh "./gradlew acceptanceTest -D calculador.url=http://localhost:8765"
				sh "chmod +x acceptance-test.sh && ./acceptance-test.sh"

			}
		}
		stage("Release") {
               		steps {
                    		sh "kubectl config use-context kind-production"
                    		sh "kubectl apply -f hazelcast.yaml"
                    		sh "kubectl apply -f deployment.yaml"
				sh "kubectl apply -f service.yaml"
               		}
          	}
		stage("Smoke test") {
              		steps {
                  		sleep 60
                  		sh "chmod +x smoke-test.sh && ./smoke-test.sh"
              		}
          	}
	}
		
		post {
			always {
			    mail to: 'ppaabblloo4283@gmail.com',
			    	subject:"Completed Pipeline: ${currentBuild.fullDisplayName}",
			    	body:"Your build completed, please check: ${env.BUILD_URL}"
			    sh "docker stop calculador"
			}
			failure {
			    slackSend channel: '#sprint',
			    color: 'danger',
			    message:"The pipeline ${currentBuild.fullDisplayName} failed."
			}
		
		}
	}
