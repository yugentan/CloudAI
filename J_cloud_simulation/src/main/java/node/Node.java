package node;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

// Mapping Typically Worker Nodes -> Cloudsim Object::Host
public class Node {
	private Host node;
	private int hostid;
	private int ramSize;
	private int storageSize;
	private int bandwidth;
	
	/**
	 * CloudSim Pe (Processing Element) class represents CPU unit, defined in terms of Millions
	 * Instructions Per Second (MIPS) rating.<br>
	 * <b>ASSUMPTION:<b> All PEs under the same Machine have the same MIPS rating.
	 */
	private List<Pe> peList = new ArrayList<Pe>();
	private int mips = 1000;
	
	public Node(int hostid, int ramSize, int storageSize, int bandwidth) {
		this.hostid = hostid;
		this.ramSize = ramSize;
		this.storageSize = storageSize;
		this.bandwidth = bandwidth;
		//Initialize CPUs/Cores default 1 core now, will change to dynamic later base on allocation
		peList.add(new Pe(0,new PeProvisionerSimple(this.mips)));
	}
	
	// Initialize the node as a host
	public void initializeHost() {
		node = new Host(this.hostid,
				new RamProvisionerSimple(this.ramSize),
				new BwProvisionerSimple(this.bandwidth),
				this.storageSize,
				this.peList,
				new VmSchedulerTimeShared(this.peList));
				
	}
	
	// Gets the node as a host to include in our cluster
	public Host getNode() {
		if(this.node == null) {
			this.initializeHost();
		}
		return this.node;
	}
	
	
}
