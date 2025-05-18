pipeline {
    agent any
    stages {
        stage('Build & Test') {
            steps {
                // Для Maven
                sh 'mvn clean test -Dtest=UserTest'

                // ИЛИ для Gradle
                // sh './gradlew test --tests UserTest'
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml' // Путь к отчетам
        }
    }
}