import subprocess
import os 
import re 
import yaml 

# Function to run get kubectl get all to determine what resource we have currently
def run_get_all():
    command = "kubectl get all"
    process = subprocess.Popen(command.split(), stdout=subprocess.PIPE)
    output, error = process.communicate()
    #error check 
    if error:
        print(f"Error on get all call: {error.decode()}")
    return output.decode()

# Function to ge all unique services and deployments. Will entend to other service 
# but this is what we need for Resource Allocation(RA) and LoadBalancing (LB) at the moment
def get_all_unique_name(resource): 
    deployments = re.findall(r"deployment\.apps/(\S+)\s+", resource)
    services = re.findall(r"service/(\S+)\s+", resource)
    #set of default deployments 
    unique_deployments = set(deployments)
    #set of default services 
    unique_services = set(services)
    #remove default
    unique_services.remove("kubernetes")
    return unique_deployments, unique_services

def load_yaml(file_path):
    with open(file_path, 'r') as file:
        return yaml.safe_load_all(file)

def crawl_kubernetes_folder(folder_path, deployments, services):
    deployment_yaml_data = {}
    service_yaml_data = {}

    for root, dirs, files in os.walk(folder_path):
        for file in files:
            file_path = os.path.join(root, file)
            with open(file_path, 'r') as yaml_file:
                yaml_contents = list(yaml.safe_load_all(yaml_file))
                if yaml_contents is not None:
                    for content in yaml_contents:
                        if 'kind' in content and 'metadata' in content:
                            if content['kind'] == 'Service' and content['metadata']['name'] in services:
                                service_yaml_data[content['metadata']['name']] = content
                            elif content['kind'] == 'Deployment' and content['metadata']['name'] in deployments:
                                deployment_yaml_data[content['metadata']['name']] = content
    
    return deployment_yaml_data, service_yaml_data

# Function to map a resource from its yaml data to get relavant information
# Finialize Structure to be 
# resourceX: replicas, storage size, cpu, mem (maybe need more) -> Structure to be map to cloudsim
def map_resource(resource_yaml_data):
    pass 

def main():
    # Run kubectl get all and capture output
    kubectl_output = run_get_all()
    kubernetes_folder = '/home/Julias/Projects/Microservice-ex-1/kubernetes'
    if kubectl_output:
        deployments, services = get_all_unique_name(kubectl_output)

        deployment_data, service_data = crawl_kubernetes_folder(kubernetes_folder, deployments, services)

        for deployment_name, yaml_data in deployment_data.items():
            print(f"YAML content for {deployment_name}:")
            print(yaml.dump(yaml_data))
            print("-------------------------------------")

        for service_name, yaml_data in service_data.items():
            print(f"YAML content for {service_name}:")
            print(yaml.dump(yaml_data))
            print("-------------------------------------")

        #currently this just print the relavaml .yaml files. 
        #end goal: Break down yaml into their relavant resource down to the pods level
        #and if require and cpu/mem -> for mapping into simulation.

if __name__ == "__main__":
    main()
