pipeline{

    agent any

    tools{
        maven my-maven
    }

    stages{
        stage('Build with maven'){
            step{
                sh 'mvn --version'
                sh 'java --version'
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('run docker-compose'){
            sh 'cd /var/masamune/src'
            sh 'docker-compose build'
            sh 'docker-compose up -d'
        }
    }
    post{
        always{
            cleanWs()
        }
    }
}