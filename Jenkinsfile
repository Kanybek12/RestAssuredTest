pipeline {
    agent any

    tools {
        // Используйте точные названия, которые вы настроили в Jenkins
        jdk 'JDK'          // Обычно называется именно так (регистр важен!)
        maven 'Maven'      // Стандартное название
    }

     stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                url: 'https://github.com/Kanybek12/RestAssuredTest.git'
            }
        }
stage('Check Versions') {
    steps {
        bat 'java -version'
        bat 'javac -version'
        bat 'mvn --version'
    }
}
         stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

         stage('Test') {
            steps {
                powershell 'mvn clean test -Dtest=UserTest'
            }
            post {
                always {
                    junit 'target/surefire-reports *//*  *//*.xml'
                }
            }
        }
    }
}