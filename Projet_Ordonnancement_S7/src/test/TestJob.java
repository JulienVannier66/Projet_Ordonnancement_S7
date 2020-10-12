package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.Instance;

class TestJob {

	@Test
	void testInitialize() throws IOException {
		Instance instance = new Instance("data/20/3/2/instance0-20-3-2.data");
		for(int i = 0; i < instance.getNbJobs(); i++) {
			assertEquals(i, instance.getListJobs().get(i).getId());
			assertEquals(0, instance.getListJobs().get(i).getIdMachine());
		}
		
		instance.getListJobs().get(0).setIdMachine(1);
		assertEquals(1, instance.getListJobs().get(0).getIdMachine());
		
		assertEquals(1401, instance.getListJobs().get(0).getStartTime());
		assertEquals(1424, instance.getListJobs().get(0).getEndTime());

		assertEquals(674, instance.getListJobs().get(1).getStartTime());
		assertEquals(692, instance.getListJobs().get(1).getEndTime());

		assertEquals(199, instance.getListJobs().get(2).getStartTime());
		assertEquals(1415, instance.getListJobs().get(2).getEndTime());

		assertEquals(635, instance.getListJobs().get(10).getStartTime());
		assertEquals(704, instance.getListJobs().get(10).getEndTime());

		assertEquals(1293, instance.getListJobs().get(11).getStartTime());
		assertEquals(1325, instance.getListJobs().get(11).getEndTime());

		assertEquals(105, instance.getListJobs().get(18).getStartTime());
		assertEquals(1297, instance.getListJobs().get(18).getEndTime());

		assertEquals(163, instance.getListJobs().get(19).getStartTime());
		assertEquals(233, instance.getListJobs().get(19).getEndTime());
		
		assertEquals(365, instance.getListJobs().get(0).getListResources().get(0).intValue());
		assertEquals(621, instance.getListJobs().get(0).getListResources().get(1).intValue());
		assertEquals(611, instance.getListJobs().get(0).getListResources().get(2).intValue());
		
		assertEquals(892, instance.getListJobs().get(10).getListResources().get(0).intValue());
		assertEquals(568, instance.getListJobs().get(10).getListResources().get(1).intValue());
		assertEquals(77, instance.getListJobs().get(10).getListResources().get(2).intValue());
		
		assertEquals(57, instance.getListJobs().get(19).getListResources().get(0).intValue());
		assertEquals(709, instance.getListJobs().get(19).getListResources().get(1).intValue());
		assertEquals(216, instance.getListJobs().get(19).getListResources().get(2).intValue());
	}
}
