def call(String project,String ImageTag,String dockerHubUser){
echo "Deploying on Main Instance (Master)..."
                withCredentials([usernamePassword(credentialsId: 'dockerHubCred', 
                                 passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
                    
                    // 1. Log in to pull the private/updated image
                    sh "docker login -u ${dockerHubUser} -p ${dockerHubPass}"
                    
                    // 2. Pull the fresh image built by the Agent
                    sh "docker pull ${dockerHubUser}/flask-app:latest" 
                    
                    // 3. FORCE CLEANUP: This is the critical fix for the 'Conflict' error.
                    // It kills any container named mysql or flask-app regardless of who started it.
                   sh "docker rm -f mysql flask-app || true"
                    
                    // 4. Standard Compose Cleanup
                    sh "docker-compose -p flask-app down --remove-orphans || true"
                    
                    // 5. Start the new containers
                    sh "docker-compose -p flask-app up -d"
                    
                    // 6. Optional: Clean up dangling images to save disk space (highly recommended for 86% usage)
                    sh "docker image prune -f"
}
