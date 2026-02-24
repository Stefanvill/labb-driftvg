pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Bygg') {
            steps {
                sh './mvnw clean package'
            }
        }
    }
    post {
        success {
            echo '✅ Bygg OK!'
            archiveArtifacts 'target/*.jar'
        }
        failure {
            echo '❌ Bygg misslyckades'
        }
    }
}
