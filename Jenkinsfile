pipeline {
    environment {
        DOCKER_HOST = 'tcp://192.168.1.11:2375'
    }
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
            reuseNode true
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                archiveArtifacts artifacts: 'target/', fingerprint: true
            }
        }
        stage('Build image') {
            steps {
                sh 'mvn clean package docker:build -DskipTests'
            }
        }
        stage('Test & Sonarqube') {
            steps {
                configFileProvider([configFile(fileId: '568fd3ab-9b40-4b96-803c-9bd2bf3ce12b', variable: 'SonarSettings')]) {
                    sh 'mvn -s $SonarSettings clean package sonar:sonar -B'
                }
            }
        }
        stage('Integration tests') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('Artifactory') {
            steps {
                configFileProvider([configFile(fileId: 'artifactory-settings', variable: 'ArtifactorySettings')]) {
                    sh 'mvn -s $ArtifactorySettings clean package deploy -DskipTests -B'
                }
            }
        }
        stage('Deploy stack test') {
            when {
                branch 'dev'
            }
            steps {
                sh 'docker stack deploy -c config/test-stack.yml kwetter-test'
            }
        }
        stage('Deploy stack master') {
            when {
                branch 'master'
            }
            steps {
                sh 'docker stack deploy -c config/stack.yml kwetter'
            }
        }
        stage('success') {
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                }
            }
        }
    }
    post {
        changed {
            script {
                if (currentBuild.result == 'FAILURE') {
                    step([$class           : 'Mailer',
                          recipients       : emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                                 [$class: 'RequesterRecipientProvider']]),
                          sendToIndividuals: true])
                }
                if (currentBuild.result == 'SUCCESS') {
                    step([$class           : 'Mailer',
                          recipients       : emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                                                                 [$class: 'DevelopersRecipientProvider']]),
                          sendToIndividuals: true])
                }
            }

        }
    }
}
