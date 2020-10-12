package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.Instance;

class TestInstance {

	@Test
	void testInitialize() throws IOException {
		Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
		assertEquals(20, instance.getNbJobs());
		assertEquals(20, instance.getListJobs().size());
		assertEquals(2, instance.getNbMachine());
		assertEquals(2, instance.getListMachines().size());
		assertEquals(3, instance.getNbResource());
		for(int i = 0; i <instance.getNbMachine(); i++) {
			assertEquals(3, instance.getListMachines().get(i).getListResources().size());
		}
		
	}

}
