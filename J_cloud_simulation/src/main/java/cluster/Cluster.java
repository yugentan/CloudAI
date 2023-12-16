package cluster;
import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;

import node.Node;

// Mapping Cluster -> Cloudsim Object::Datacenter
public class Cluster {
	private Datacenter cluster;
	private DatacenterCharacteristics characteristics;
	
	private String name;
	
	
	List<Host> hostList = new ArrayList<Host>();
	
	private static final double TIME_ZONE = 10.0; //Fixed values currently, to be changed later to dynamic
	private static final double COST = 3.0; //Fixed values currently, to be changed later to dynamic
	private static final double COST_PER_MEM = 0.05; //Fixed values currently, to be changed later to dynamic
	private static final double COST_PER_STORAGE = 0.001; //Fixed values currently, to be changed later to dynamic
	private static final double COST_PER_BW = 0.0; //Fixed values currently, to be changed later to dynamic
	
	//Initializing Cluster
	public Cluster(String name, String arch, String os, String vmm) {
		this.name = name;

		this.characteristics  = new DatacenterCharacteristics(arch, os, vmm,
				this.hostList, TIME_ZONE, COST, COST_PER_MEM, COST_PER_STORAGE, COST_PER_BW);
		try {
		this.cluster = new Datacenter(this.name, this.characteristics, 
				new VmAllocationPolicySimple(hostList), null, 0);
	
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//Getting cluster
	public Datacenter getCluster() {
		return this.cluster;
	}
	
	//Adding nodes to a cluster
	public void addNode(Node node) {
		this.hostList.add(node.getNode());
	}
	
}
