package model.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Algorithm;
import model.Instance;
import model.Job;
import model.Machine;
/**
 * cette classe comporte les m�thodes de g�n�ration d'un algorithme g�n�tique
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Generation {

	/**
	 * Permet de cr�er un individus valides. Le codage utilis� est 0 pour un job rejet�, 1 � j (le nombre
	 * de machines max) pour savoir � quelle machine est attribu�e le job
	 * @param instance l'instance de base g�n�r�e depuis un fichier
	 * @return un tableau repr�sentant un individu valide
	 */
	public int [] initPersonCompleteMachineFirst(Instance instance) {
		// on cr�e le tableau de n jobs et on initialise � -1 toutes les cases
		// la derni�re case du tableau indiquera le nombre de jobs rejet�s
		int [] solRealisable = new int[instance.getNbJobs()+1];
		for (int indexTab = 0; indexTab < solRealisable.length; indexTab++) {
			solRealisable[indexTab] = -1;
		}
		
		//on copie la liste de jobs � placer
		ArrayList<Job> jobsAPlacer = new ArrayList<>(instance.getListJobs());
		
		//on copie la liste des machines � g�rer
		ArrayList<Machine> machinesAPlacer = new ArrayList<>(instance.getListMachines());
		
		Random randomGenerator = new Random();
		
		// tant que toutes les machines n'ont pas �t� remplies
		while(!machinesAPlacer.isEmpty()) {
			
			// on choisi une machine au hasard
			int indexRandomMachine = randomGenerator.nextInt(machinesAPlacer.size());
			Machine machineARemplir = machinesAPlacer.get(indexRandomMachine);
			
			/* On cr�e une liste des jobs qu'il reste � teste en fonction des job � placer
			   Cela permet d'optimiser la g�n�ration du nombre al�atoire */
			ArrayList<Job> jobsATester = new ArrayList<>(jobsAPlacer);
			// on teste tous les jobs pour une machine
			while(!jobsAPlacer.isEmpty() && !jobsATester.isEmpty()) {
				
				// on choisi un index random pour s�l�ctionner un job dans notre tableau
				int indexRandomJob = randomGenerator.nextInt(jobsATester.size());
				Job jobToAddT = jobsATester.get(indexRandomJob);
				Job jobToAddP = jobsAPlacer.get(indexRandomJob);
				
				// si on peut ajouter le job dans la machine
				if(machineARemplir.addJob(jobToAddP)) {
					solRealisable[jobToAddP.getId()] = machineARemplir.getId();					
					jobsAPlacer.remove(jobToAddP);
					jobsATester.remove(jobToAddT);
				/* sinon on ne peut pas ajouter le job, on le retire de la liste des jobs � tester 
				  pour voir si il peut passer sur une autre machine */
				}else {
					// dans le cas ou il reste une machine, le job est rejet� (=0)
					if(machinesAPlacer.size() == 1) {
						solRealisable[jobToAddP.getId()] = 0;
						jobsAPlacer.remove(jobToAddP);
					}
					jobsATester.remove(jobToAddT);
				}	
			}
			machinesAPlacer.remove(machineARemplir);
				
		}
		// on remplit la derni�re case pour indiquer le nombre de jobs rejet�s pour cet individu
		solRealisable[solRealisable.length-1] = instance.getNbRejetedJobs();
		
		// on vide l'instance pour pouvoir la r�utiliser
		instance.cleanInstance();
		return solRealisable;
	}
	
	/**
	 * cette classe cr�er le codage d'un individu
	 * @param instance
	 * @return un individu valide
	 */
	public int [] initPersonDistributedMachine(Instance instance) {
		instance.cleanInstance();
		// on cr�e le tableau de n jobs et on initialise � -1 toutes les cases
		// la derni�re case du tableau indiquera le nombre de jobs rejet�s
		int [] solRealisable = new int[instance.getNbJobs()+1];
		for (int indexTab = 0; indexTab < solRealisable.length; indexTab++) {
			solRealisable[indexTab] = -1;
		}
		
		//on copie la liste de jobs � placer
		ArrayList<Job> jobsAPlacer = new ArrayList<>(instance.getListJobs());
		Random randomGenerator = new Random();
		
		while(!jobsAPlacer.isEmpty()) {
			//on choisie au hasard un job
			int indexRandomJob = randomGenerator.nextInt(jobsAPlacer.size());
			//on copie la liste des machines � g�rer
			ArrayList<Machine> machinesATester = new ArrayList<>(instance.getListMachines());
			
			while(!machinesATester.isEmpty()) {
				int indexRandomMachine = randomGenerator.nextInt(machinesATester.size());
				Machine machineToTest = machinesATester.get(indexRandomMachine);
				
				// si on peut ajouter le job dans la machine
				if(machineToTest.addJob(jobsAPlacer.get(indexRandomJob))) {
					solRealisable[jobsAPlacer.get(indexRandomJob).getId()] = machineToTest.getId();					
					machinesATester.clear();
				/* sinon on ne peut pas ajouter le job, on le retire de la liste des jobs � tester 
				  pour voir si il peut passer sur une autre machine */
				}
				else {
					// dans le cas ou il reste une machine, le job est rejet� (=0), la derni�re machine � d�j� �t� verifi�
					if(machinesATester.size() == 1) {
						solRealisable[jobsAPlacer.get(indexRandomJob).getId()] = 0;
						machinesATester.clear();
					} 
					else {
						machinesATester.remove(indexRandomMachine);
					}
				}
			}
			jobsAPlacer.remove(indexRandomJob);
		}
		
		// on remplit la derni�re case pour indiquer le nombre de jobs rejet�s pour cet individu
		solRealisable[solRealisable.length-1] = instance.getNbRejetedJobs();
		return solRealisable;
	}
	
	/**
	 * g�n�re une population en bourrant les t�ches machine par machine
	 * @param instance
	 * @param sizePopulation
	 * @param cloneRate
	 * @return la population initiale
	 */
	public List<int[]> generatePopulationCompleteMachineFirst(Instance instance, int sizePopulation, double cloneRate){
		
		Algorithm algo = new Algorithm();
		List<int[]> population = new ArrayList<>();
		// cr�ation de la population
		while(population.size() < sizePopulation) {
			int[] person = initPersonCompleteMachineFirst(instance);
			if(algo.calculCloneRate(population, person, sizePopulation, cloneRate)) {
				population.add(person);
			}
		}
		return population;
	}

	/**
	 * g�n�re une population en r�partissant les t�ches sur machines distribu�es
	 * @param instance
	 * @param sizePopulation
	 * @param cloneRate
	 * @return la population initiale
	 */
	public List<int[]> generatePopulationDistributedMachine(Instance instance, int sizePopulation, double cloneRate){
		
		Algorithm algo = new Algorithm();
		List<int[]> population = new ArrayList<>();
		// cr�ation de la population
		while(population.size() < sizePopulation) {
			int[] person = initPersonDistributedMachine(instance);
			if(algo.calculCloneRate(population, person, sizePopulation, cloneRate)) {
				population.add(person);
			}
		}
		return population;
	}
}
