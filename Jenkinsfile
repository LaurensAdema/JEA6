pipeline {
    environment {
        DOCKER_HOST = 'tcp://192.168.1.11:2375'
    }
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
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
                configFileProvider([configFile(fileId: 'b7d2e7fe-2005-44f6-bde2-77587a78c6a2', variable: 'ArtifactorySettings')]) {
                    sh 'mvn -s $ArtifactorySettings clean package deploy -DskipTests -B'
                }
            }
        }
        stage('Deploy Dev') {
            when {
                branch 'dev'
            }
            steps {
				sh 'curl -v -X POST http://192.168.1.11:2375/containers/dev.kwetter/stop'
				sh 'curl -v -X DELETE http://192.168.1.11:2375/containers/dev.kwetter'
                sh 'curl -v -X POST -H "Content-Type: application/json" -d \'{"Image": "ma.ade/kwetter2:latest","ExposedPorts": {"8080/tcp": { "HostPort": "59388" }}}\' http://192.168.1.11:2375/containers/create?name=dev.kwetter'
				sh 'curl -v -X POST -H http://192.168.1.11:2375/containers/dev.kwetter/start'
            }
        }
        stage('Deploy Master') {
            when {
                branch 'master'
            }
            steps {
				sh 'curl -v -X POST http://192.168.1.11:2375/containers/kwetter/stop'
				sh 'curl -v -X DELETE http://192.168.1.11:2375/containers/kwetter'
                sh 'curl -v -X POST -H "Content-Type: application/json" -d \'{"Image": "ma.ade/kwetter2:latest","ExposedPorts": {"8080/tcp": { "HostPort": "5938" }}}\' http://192.168.1.11:2375/containers/create?name=kwetter'
				sh 'curl -v -X POST -H http://192.168.1.11:2375/containers/kwetter/start'
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
