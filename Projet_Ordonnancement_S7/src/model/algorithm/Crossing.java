package model.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Algorithm;
import model.Instance;
import model.algorithm.Mutation;

/**
 * cette classe est constitué de plusieurs méthodes de croisement, utilisées lors 
 * d'exécutions d'algorithmes génétiques.
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Crossing {
	/**
	 * Cette méthode effectue un croisement entre 2 parents pour créer 2 individus enfants en
	 * choisissant 2 points au hasard dans chaque parent (donc en découpant en 3 parties les parents)
	 * pour créer les enfants.
	 * @param listCouple la liste des couples
	 * @param population la population
	 * @param instance l'instance
	 * @param mutation la mutation
	 */
	public void twoPointCrossing(List<List<int[]>> listCouple, List<int[]> population, Instance instance, int sizePopulation, double cloneRate, Mutation mutation) {
		Algorithm algo = new Algorithm();
		Random randomGenerator = new Random();
		
		int firstPoint = randomGenerator.nextInt(population.get(0).length);
		int secondPoint = randomGenerator.nextInt(population.get(0).length);
		
		// on évite de tirer deux fois le même nombre random
		while(secondPoint == firstPoint) {
			secondPoint = randomGenerator.nextInt(population.get(0).length);
		}
		
		// on inverse les valeurs pour la cohérence avec le nom des variables si besoin
		if(firstPoint > secondPoint) {
			int tempPoint = firstPoint;
			firstPoint = secondPoint;
			secondPoint = tempPoint;
		}
		
		// pour tous les couples disponibles
		for(int i = 0; i < listCouple.size(); i++) {
			int[] person1 = listCouple.get(i).get(0);
			int[] person2 = listCouple.get(i).get(1);
			
			int[] child1 = new int[person1.length];
			int[] child2 = new int[person1.length];

			for(int indexJob = 0; indexJob < firstPoint; indexJob++) {
				child1[indexJob] = person1[indexJob];
				child2[indexJob] = person2[indexJob];
			}
			for(int indexJob = firstPoint; indexJob < secondPoint; indexJob++) {
				child1[indexJob] = person2[indexJob];
				child2[indexJob] = person1[indexJob];
			}
			for(int indexJob = secondPoint; indexJob < person1.length; indexJob++) {
				child1[indexJob] = person1[indexJob];
				child2[indexJob] = person2[indexJob];
			}
			
			mutation.mutatePerson(child1, instance);
			mutation.mutatePerson(child2, instance);
			
			// si les enfants sont des invididus valides, on les ajoute dans notre population d'enfants
			if(checkPerson(instance, child1)) {
				if(algo.calculCloneRate(population, child1, sizePopulation, cloneRate)) {
					population.add(child1);
				}
			}
			if(checkPerson(instance, child2)) {
				if(algo.calculCloneRate(population, child2, sizePopulation, cloneRate)) {
					population.add(child2);
				}
			}
		}
		mutation.mutateAllClone(population); 
	}
	
	/**
	 * Cette méthode retourne vrai si l'individu passé en paramètre est valide (si la solution 
	 * est réalisable), faux sinon.
	 * @param instance l'instance
	 * @param person l'individu à vérifier
	 * @return vrai si l'individu est valide
	 */
	public boolean checkPerson(Instance instance, int[] person) {
		instance.cleanInstance();
		boolean accept = true;
		int i = 0;
		
		while(i < instance.getNbJobs() && accept) {
			int idMachine = person[i];
			if(idMachine != 0) {
				accept = instance.getListMachines().get(idMachine - 1).addJob(instance.getListJobs().get(i));
			}
			i++;
		}
		person[instance.getNbJobs()] = instance.getNbRejetedJobs();
		
		return accept;
	}
}
