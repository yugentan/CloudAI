package sim;

import cluster.Cluster;
import environment.Environment;

public class Main {
	public static void main(String[] args) {
//		Cluster a = new Cluster("Hello", "x86", "Linux", "Xen");
		Environment testEnvironment = new Environment();
		testEnvironment.createCluster();//OUTPUT OF kubectl comes here.
		testEnvironment.startSimulation();
	}

}
