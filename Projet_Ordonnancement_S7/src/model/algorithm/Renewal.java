package model.algorithm;

import java.util.List;
import java.util.Random;

import model.Algorithm;
/**
 * cette classe comporte les m�thodes pour le renouvellement d'une population dans un algorithme g�n�tique
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Renewal {

	/**
	 * cette m�thode renouvelle une population en effectuant une m�thode par tournoi entre
	 * 2 individus jusqu'� obtenir une population �gale � la taille de la population de d�part.
	 * @param population la population
	 * @param startSizePopulation la taille de la population initiale
	 */
	public void populationRenewalByTournament(List<int[]> population, int startSizePopulation) {
		Random randomGenerator = new Random();
		
		while(population.size() > startSizePopulation) {
			int indexPerson1 = randomGenerator.nextInt(population.size());
			int indexPerson2 = randomGenerator.nextInt(population.size());
			
			while(indexPerson1 == indexPerson2) {
				indexPerson2 = randomGenerator.nextInt(population.size());
			}
			
			int[] person1 = population.get(indexPerson1);
			int[] person2 = population.get(indexPerson2);
			int[] worstPerson = worstPersonBetween2Person(person1, person2);
			population.remove(worstPerson);
		}
	}
	
	/**
	 * cette m�thode retourne l'individu le moins bon (celui qui rejette le plus de jobs) entre 2
	 * individus
	 * @param person1 l'individu 1
	 * @param person2 l'individu 2
	 * @return l'individu le moins bon
	 */
	public int[] worstPersonBetween2Person(int[] person1, int[] person2) {
		int jobsRejectedPerson1 = person1[person1.length-1];
		int jobsRejectedPerson2 = person2[person2.length-1];
		if(jobsRejectedPerson1 >= jobsRejectedPerson2) {
			return person1;
		}else{
			return person2;
		}
	}
	
	/**
	 * cette m�thode renouvelle une population avec une m�thode par elitisme. PRECONDITION : 
	 * la population a �t� tri� par ordre d�croissant du nombre de jobs rejet�s
	 * @param population la population
	 * @param startSizePopulation la taille de la population initiale
	 */
	public void populationRenewalByElitism(List<int[]> population, int startSizePopulation, Algorithm algo) {
		// on trie les individus de la population par ordre d�croissant du nombre de jobs rejet�s
		algo.sortPopulationDescendingOrderJobsRejected(population);
		// puis on renouvelle la population par elitisme
		int i = 0;
		while(population.size() > startSizePopulation) {
			population.remove(i);
			i++;
		}
	}
}
