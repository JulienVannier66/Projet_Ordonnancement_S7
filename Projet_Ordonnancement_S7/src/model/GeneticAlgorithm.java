package model;

import java.util.ArrayList;
import java.util.List;

import model.algorithm.Crossing;
import model.algorithm.Generation;
import model.algorithm.Mutation;
import model.algorithm.Renewal;
import model.algorithm.Selection;
/**
 * Cette classe contient tous les algorithmes génétiques
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class GeneticAlgorithm {
	/**
	 * Cet algorithme génétique génère une population en bourrant une machine avant de passer à l'autre,
	 * effectue une sélection par tournoi, un croisement en 2-points, un renouvellement par élitisme.
	 * @param instance l'instance choisie
	 * @param maxIt le nb max d'itérations
	 * @param sizePopulation la taille de la population
	 * @param mutationRate le taux de mutation
	 * @param cloneRate le taux de clones
	 * @return la meilleure solution
	 */
	public int algoTestFinal(Instance instance, int maxIt, int sizePopulation, double mutationRate, double cloneRate) {
		Generation generation = new Generation();
		Selection selection = new Selection();
		Crossing crossing = new Crossing();
		Mutation mutation = new Mutation(mutationRate);
		Renewal renewal = new Renewal();
		Algorithm algo = new Algorithm();
		
		int miniRejectedJob = instance.getNbJobs();
		List<int[]> population = new ArrayList<>();
		
//		System.out.println("Population de départ");
		population = generation.generatePopulationCompleteMachineFirst(instance, sizePopulation, cloneRate);
//		for(int i = 0; i < sizePopulation; i++) {
//			for(int j = 0; j <= instance.getNbJobs(); j++) {
//				System.out.print(String.valueOf(population.get(i)[j]) + " ");
//			}
//			System.out.println();
//		}
		for(int i = 0; i < maxIt; i++) {
			List<List<int[]>> listCouple = selection.randomCoupleByTournament(population);
//			System.out.println("--- Before Crossing ---");
//			System.out.println("----------------");
			crossing.twoPointCrossing(listCouple, population, instance, sizePopulation, cloneRate, mutation);
//			System.out.println("--- Crossing ---");
//			System.out.println("----------------");
			renewal.populationRenewalByElitism(population, sizePopulation, algo);
			//calcul de la meilleure sol
			int rejecteJob = algo.updateBestSolInt(population, instance.getNbJobs());
			if(rejecteJob < miniRejectedJob) {
				miniRejectedJob = rejecteJob;
			}
		}
		
//		System.out.println("----------Pop fin-------------");
//		for(int i1 = 0; i1 < sizePopulation; i1++) {
//			for(int j = 0; j <= instance.getNbJobs(); j++) {
//				System.out.print(String.valueOf(population.get(i1)[j]) + " ");
//			}
//			System.out.println();
//		}
		
		return miniRejectedJob;
	}
	
	/**
	 * Cet algorithme génétique génère une population en bourrant une machine avant de passer à l'autre,
	 * effectue une sélection par tournoi, un croisement en 2-points, un renouvellement par tournoi.
	 * @param instance
	 * @param maxIt le nb max d'itérations
	 * @param sizePopulation la taille de la population
	 * @param mutationRate le taux de mutation
	 * @param cloneRate le taux de clones
	 * @return la meilleure solution
	 */
	public int algoTestFinal2(Instance instance, int maxIt, int sizePopulation, double mutationRate, double cloneRate) {
		Generation generation = new Generation();
		Selection selection = new Selection();
		Crossing crossing = new Crossing();
		Mutation mutation = new Mutation(mutationRate);
		Renewal renewal = new Renewal();
		Algorithm algo = new Algorithm();
		
		int miniRejectedJob = instance.getNbJobs();
		List<int[]> population = new ArrayList<>();
		
//		System.out.println("Population de départ");
		population = generation.generatePopulationCompleteMachineFirst(instance, sizePopulation, cloneRate);
//		for(int i = 0; i < sizePopulation; i++) {
//			for(int j = 0; j <= instance.getNbJobs(); j++) {
//				System.out.print(String.valueOf(population.get(i)[j]) + " ");
//			}
//			System.out.println();
//		}
		for(int i = 0; i < maxIt; i++) {
			List<List<int[]>> listCouple = selection.randomCoupleByTournament(population);
//			System.out.println("--- Before Crossing ---");
//			System.out.println("----------------");
			crossing.twoPointCrossing(listCouple, population, instance, sizePopulation, cloneRate, mutation);
//			System.out.println("--- Crossing ---");
//			System.out.println("----------------");
			renewal.populationRenewalByTournament(population, sizePopulation);
			//calcul de la meilleure sol
			int rejecteJob = algo.updateBestSolInt(population, instance.getNbJobs());
			if(rejecteJob < miniRejectedJob) {
				miniRejectedJob = rejecteJob;
			}
		}
		
//		System.out.println("----------Pop fin-------------");
//		for(int i1 = 0; i1 < sizePopulation; i1++) {
//			for(int j = 0; j <= instance.getNbJobs(); j++) {
//				System.out.print(String.valueOf(population.get(i1)[j]) + " ");
//			}
//			System.out.println();
//		}		
		return miniRejectedJob;
	}
}
