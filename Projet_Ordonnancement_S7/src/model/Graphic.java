package model;

import java.util.ArrayList;
import java.util.List;

import model.algorithm.Crossing;
import model.algorithm.Generation;
import model.algorithm.Mutation;
import model.algorithm.Renewal;
import model.algorithm.Selection;

/**
 * Cette classe permet de générer les graphes de convergence
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Graphic {
	/**
	 * cette méthode retourne un tableau contenant la meilleure solution pour chaque itération en déroulant un algorithme génétique
	 * @param instance l'instance choisie
	 * @param maxIt le nombre maximum d'itération
	 * @param sizePopulation la taille de la population
	 * @param cloneRate le pourcentage de clones autorisés
	 * @param mutationRate le pourcentage de mutation
	 * @return un tableau contenant les meilleures solution pour chaque itération
	 */
	public int[] graphConvergenceCurve(Instance instance, int maxIt, int sizePopulation, double cloneRate, double mutationRate) {
		Generation generation = new Generation();
		Selection selection = new Selection();
		Crossing crossing = new Crossing();
		Mutation mutation = new Mutation(mutationRate);
		Renewal renewal = new Renewal();
		Algorithm algo = new Algorithm();
		
		int[] graphRejectedJob = new int[maxIt];
		int miniRejectedJob = instance.getNbJobs();
		List<int[]> population = new ArrayList<>();
		
		population = generation.generatePopulationCompleteMachineFirst(instance, sizePopulation, cloneRate);
		for(int i = 0; i < maxIt; i++) {
			List<List<int[]>> listCouple = selection.randomCoupleByTournament(population);
			crossing.twoPointCrossing(listCouple, population, instance, sizePopulation, cloneRate, mutation);
			renewal.populationRenewalByElitism(population, sizePopulation, algo);
			
			int rejecteJob = algo.updateBestSolInt(population, instance.getNbJobs());
			if(rejecteJob < miniRejectedJob) {
				miniRejectedJob = rejecteJob;
			}
			
			graphRejectedJob[i] = miniRejectedJob;
		}
		return graphRejectedJob;
	}
}
