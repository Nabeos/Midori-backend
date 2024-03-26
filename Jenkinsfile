pipeline{

    agent any

    tools{
        maven 'my-maven'
    }

    stages{
        stage('run docker-compose'){
            steps{
                sh 'docker compose up -d --build'
            }
        }
    }
}