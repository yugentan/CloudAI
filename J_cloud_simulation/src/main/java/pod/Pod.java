package pod;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

// Mapping Pods -> Cloudsim Object::Vm
public class Pod {
	private Vm pod;
	
	private int vmid;
	private int mips;
	private long imageSize;
	private int ramSize;
	private long bandwidth;
	private int numCpu;
	private String vmMonitor = "Xen"; //will change to dynamic if needed. Xen is use to monitor lifecycle of VM in CloudSim
	private int brokerId; //Entity to allocate task to vm
	
	public Pod(int vmid, int mips, long imageSize, int ramSize, long bandWidth, int numCpu, int brokerId) {
		this.vmid = vmid;
		this.mips = mips;
		this.imageSize = imageSize;
		this.ramSize = ramSize;
		this.bandwidth = bandWidth;
		this.numCpu = numCpu;
		this.brokerId = brokerId;
	}
	
	// Create Pod as a VM
	public void initialize() {
		this.pod = new Vm(this.vmid, this.brokerId, this.mips, this.numCpu, 
				this.ramSize, this.bandwidth, this.imageSize, vmMonitor, new CloudletSchedulerTimeShared());
	}
	
	public Vm getPod() {
		if(this.pod == null) {
			this.initialize();
		}
		return pod;
	}
}
