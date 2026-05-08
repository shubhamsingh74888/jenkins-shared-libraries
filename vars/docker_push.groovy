def call(String project, String ImageTag, String dockerHubUser){
   withCredentials([usernamePassword(credentialsId: 'dockerHubCred', passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
                    sh "docker login -u ${dockerHubUser} -p ${dockerHubPass}"
   }
                    sh "docker tag flask-app:latest ${dockerHubUser}/flask-app:latest"
                    sh "docker push ${dockerHubUser}/flask-app:latest"
}
