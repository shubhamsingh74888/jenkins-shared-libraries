def call(String projectName, String imageTag, String DockerHubUser) 
{
sh "docker build -t DockerHubUser/projectName:imageTag ."
}
