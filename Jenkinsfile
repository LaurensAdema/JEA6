pipeline {
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
                sh 'make'
                archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                /* `make check` returns non-zero on test failures,
                * using `true` to allow the Pipeline to continue nonetheless
                */
                sh 'make check || true'
                junit '**/target/*.xml'
            }
        }
        stage('Artifactory') {
            steps {
                configFileProvider([configFile(fileId: 'artifactory-settings', variable: 'artifactory-settings')]) {
                    sh 'mvn -s $artifactory-settings clean package deploy -DskipTests -B'
                }
            }
        }
        stage('Deploy') {
            when {
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            steps {
                sh 'make publish'
            }
        }
    }
}
