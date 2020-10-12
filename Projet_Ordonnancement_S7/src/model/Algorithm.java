package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
/**
 * Cette classe continent différents algorithmes utilisés dans le projet
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Algorithm {
	/**
	 * Cette méthode met à jour la solution courante
	 * @param population la population
	 * @param nbJobs le nb de jobs rejetés de la solution
	 * @return le nb de jobs rejetés
	 */
	public int updateBestSolInt(List<int[]> population, int nbJobs) {
		// on trouve le plus petit nombre de job rejeté
		int rejectedJob = nbJobs;
		for(int i = 0; i < population.size(); i++) {
			if(population.get(i)[nbJobs] < rejectedJob) {
				rejectedJob = population.get(i)[nbJobs];
			}
		}
		return rejectedJob;
	}
	
	/**
	 * comparateur pour comparer le nombre de jobs rejetés entre 2 individus
	 */
	public static Comparator<int[]> personJobsRejectedDesc = new Comparator<int[]>(){
		public int compare(int[] person1, int[] person2) {
			int jobsRejectedPerson1 = person1[person1.length-1];
			int jobsRejectedPerson2 = person2[person2.length-1];
			
			if(jobsRejectedPerson1 < jobsRejectedPerson2) {
				return 1;
			}else if(jobsRejectedPerson1 == jobsRejectedPerson2){
				return 0;
			}else {
				return -1;
			}
		}
	};
	
	/**
	 * Cette méthode trie la population par nb de jobs rejetés des individus décroissant
	 * on utilisera cette méthode pour le renouvellement par elitisme
	 * on trie notre population par nb de jobs rejetés des individus décroissant
	 * @param population
	 */
	public void sortPopulationDescendingOrderJobsRejected(List<int[]> population) {
		Collections.sort(population, personJobsRejectedDesc);
	}
	
	/**
	 * Cette méthode affiche le résultat d'un algorithme génétique
	 * @param instance l'instance choisie
	 * @param listSol la liste des solutions
	 */
	public void showResult(Instance instance, List<int[]> listSol) {
		System.out.println("Nombre de job rejeté : " + String.valueOf(listSol.get(0)[instance.getNbJobs()]));
		System.out.println("Best solution(s):");
		
		for(int i = 0; i < listSol.size(); i++) {
			System.out.print("[ ");
			for(int j = 0; j < instance.getNbJobs(); j++) {
				System.out.print(String.valueOf(listSol.get(i)[j]) + " ");
			}
			System.out.print("]");
			System.out.println();
		}
	}
	
	/**
	 * cette méthode calcule le taux de clones dans la population
	 * @param population la poplation courante
	 * @param person l'individu choisi
	 * @param sizePopulation la taille de la population
	 * @param cloneRate le pourcentage de clones autorisés
	 * @return vrai si le l'individu respecte le taux de clone, faux sinon
	 */
	public boolean calculCloneRate(List<int[]> population, int[] person, int sizePopulation, double cloneRate) {
		double rateClonePerson = 0;
		double compt = 1;
		boolean add = false;
		//on compte le nombre de répétition de l'individu
		for(int[] personTest : population) {
			if(Arrays.equals(personTest, person)) {
				compt++;
//				System.out.println("compt: " + String.valueOf(compt));
			}
		}
		//calcul de taux de clone après ajout
		rateClonePerson = (double)compt/(double)sizePopulation;
//		System.out.println("rateClonePers: " + String.valueOf(rateClonePerson));
		//ajout si taux de clone ok
		if(rateClonePerson <= cloneRate) {
			add = true;
		}
		return add;
	}
	
	/**
	 * Affiche dans la console de taux de clonage de chaque individu
	 * @param population la population courante
	 * @param sizePopulation la taille de la population
	 * @param cloneRate la pourcentage de clones autorisés
	 */
	public void afficheCloneRate(List<int[]> population, int sizePopulation, double cloneRate) {
		for(int i = 0; i < population.size(); i++) {
			int compt = 0;
			for(int[] personTest : population) {
				if(Arrays.equals(personTest, population.get(i))) {
					compt++;
					System.out.println("compt: " + String.valueOf(compt));
				}
			}
			System.out.println("rateClonePerson : " + String.valueOf((double)compt/(double)sizePopulation));
		}	
	}
}
