package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.Instance;
import model.Job;

class TestMachine {	

	@Test
	void testInitialize() throws IOException {
		Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
		assertEquals(1, instance.getListMachines().get(0).getId());
		assertEquals(2, instance.getListMachines().get(1).getId());
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				assertEquals(1000, instance.getListMachines().get(i).getListResources().get(j).intValue());
			}
		}
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 1440; k++) {
					assertEquals(0, instance.getListMachines().get(i).getListResourcesUsed().get(j)[k]);
				}
			}
		}
	}
	
	@Test
	void testAddJobOK() throws IOException {
		Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
		Job job = instance.getListJobs().get(0);
		assertEquals(0, instance.getListMachines().get(0).getListJobs().size());
		assertEquals(true, instance.getListMachines().get(0).addJob(job));
		assertEquals(1, instance.getListMachines().get(0).getListJobs().size());
		//first resource
		assertEquals(0, instance.getListMachines().get(0).getListResourcesUsed().get(0)[1400]);
		assertEquals(365, instance.getListMachines().get(0).getListResourcesUsed().get(0)[1401]);
		assertEquals(365, instance.getListMachines().get(0).getListResourcesUsed().get(0)[1411]);
		assertEquals(365, instance.getListMachines().get(0).getListResourcesUsed().get(0)[1424]);
		assertEquals(0, instance.getListMachines().get(0).getListResourcesUsed().get(0)[1425]);
		//second resource
		assertEquals(0, instance.getListMachines().get(0).getListResourcesUsed().get(1)[1400]);
		assertEquals(621, instance.getListMachines().get(0).getListResourcesUsed().get(1)[1401]);
		assertEquals(621, instance.getListMachines().get(0).getListResourcesUsed().get(1)[1411]);
		assertEquals(621, instance.getListMachines().get(0).getListResourcesUsed().get(1)[1424]);
		assertEquals(0, instance.getListMachines().get(0).getListResourcesUsed().get(1)[1425]);
		//third resource
		assertEquals(0, instance.getListMachines().get(0).getListResourcesUsed().get(2)[1400]);
		assertEquals(611, instance.getListMachines().get(0).getListResourcesUsed().get(2)[1401]);
		assertEquals(611, instance.getListMachines().get(0).getListResourcesUsed().get(2)[1411]);
		assertEquals(611, instance.getListMachines().get(0).getListResourcesUsed().get(2)[1424]);
		assertEquals(0, instance.getListMachines().get(0).getListResourcesUsed().get(2)[1425]);
		
		Job job2 = instance.getListJobs().get(1);
		assertEquals(true, instance.getListMachines().get(0).addJob(job2));
		assertEquals(2, instance.getListMachines().get(0).getListJobs().size());
	}
	
	@Test
	void testAddJobKO() throws IOException {
		Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
		Job job = instance.getListJobs().get(0);
		Job job2 = instance.getListJobs().get(2);
		assertEquals(0, instance.getListMachines().get(0).getListJobs().size());
		assertEquals(true, instance.getListMachines().get(0).addJob(job));
		assertEquals(1, instance.getListMachines().get(0).getListJobs().size());
		assertEquals(false, instance.getListMachines().get(0).addJob(job2));
		assertEquals(1, instance.getListMachines().get(0).getListJobs().size());
	}

}
