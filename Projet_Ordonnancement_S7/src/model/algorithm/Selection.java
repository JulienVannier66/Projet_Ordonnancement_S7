package model.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Algorithm;
/**
 * cette classe comporte les méthodes de sélection d'individus dans un algorithme génétique
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Selection {

	/**
	 * Cette méthode retourne une liste de couples au hasard, uniques créés à partir de la population 
	 * passée en paramètre.
	 * @param population la population
	 * @return une liste de couples
	 */
	public List<List<int[]>> randomUniqueCouple(List<int[]> population) {
		List<List<int[]>> listCouple = new ArrayList<>();
		Random randomGenerator = new Random();
		// cette liste permettra de gérer des couples uniques au sein de la population
		// (pas de polygamie)
		ArrayList<int[]> populationRemaining = new ArrayList<>(population);
		
		for(int indexPerson = 0; indexPerson < population.size()/2; indexPerson++) {
			List<int[]> couple = new ArrayList<>();
			// on sélectionne aléatoirement 2 individus dans la population
			int indexPerson1 = randomGenerator.nextInt(populationRemaining.size());
			int indexPerson2 = randomGenerator.nextInt(populationRemaining.size());
			
			while(indexPerson1 == indexPerson2 || populationRemaining.get(indexPerson1).equals(populationRemaining.get(indexPerson2))) {
				indexPerson2 = randomGenerator.nextInt(populationRemaining.size());
			}
			
			int[] person1 = populationRemaining.get(indexPerson1);
			int[] person2 = populationRemaining.get(indexPerson2);
			
			populationRemaining.remove(person1);
			populationRemaining.remove(person2);
			
			//on ajoute chaque individu du couple
			couple.add(person1);
			couple.add(person2);
			
			//on ajoute le couple à la liste
			listCouple.add(couple);
		}
		return listCouple;
	}
	
	/**
	 * Cette méthode selectionne le pire individu (rejetant le maximum de tâches) avec le meilleur (rejetant le minimum de tâches)
	 * @param population la population courante
	 * @return un liste de couples
	 */
	public List<List<int[]>> bestWithWorstCouple(List<int[]> population) {
		List<List<int[]>> listCouple = new ArrayList<>();
		Random randomGenerator = new Random();
		Algorithm algo = new Algorithm();
		ArrayList<int[]> populationRemaining = new ArrayList<>(population);
		
		algo.sortPopulationDescendingOrderJobsRejected(population);
		
		for(int indexPerson = 0; indexPerson < population.size()/2; indexPerson++) {
			List<int[]> couple = new ArrayList<>();
			// on sélectionne aléatoirement 2 individus dans la population
			int indexPerson1 = 0;
			int indexPerson2 = populationRemaining.size() -1;
			
//				while(indexPerson1 == indexPerson2 || populationRemaining.get(indexPerson1).equals(populationRemaining.get(indexPerson2))) {
//					indexPerson2 = randomGenerator.nextInt(populationRemaining.size());
//				}
			
			int[] person1 = populationRemaining.get(indexPerson1);
			int[] person2 = populationRemaining.get(indexPerson2);
			
			populationRemaining.remove(person1);
			populationRemaining.remove(person2);
			
			//on ajoute chaque individu du couple
			couple.add(person1);
			couple.add(person2);
			
			//on ajoute le couple à la liste
			listCouple.add(couple);
		}
		
//		for(int i = 0; i < listCouple.size(); i++) {
//			System.out.print("[");
//			for(int j = 0; j <= 20; j++) {
//				System.out.print(String.valueOf(listCouple.get(i).get(0)[j]) + " ");
//			}
//			System.out.print("][");
//			for(int j = 0; j <= 20; j++) {
//				System.out.print(String.valueOf(listCouple.get(i).get(1)[j]) + " ");
//			}
//			System.out.print("]");
//			System.out.println();
//		}
		return listCouple;
	}
	
	/**
	 * Cette méthode retourne une liste de couples au hasard, uniques, dont chaque individu a été 
	 * précédemment sélectionné par tournoi.
	 * @param population la population
	 * @return une liste de couples
	 */
	public List<List<int[]>> randomCoupleByTournament(List<int[]> population){
		
		List<List<int[]>> listCouple = new ArrayList<>();
		Random randomGenerator = new Random();
		ArrayList<int[]> populationRemaining = new ArrayList<>(population);
		
		for(int indexPerson = 0; indexPerson < population.size()/2; indexPerson++) {
			
			// on sélectionne aléatoirement 2 couples d'individus dans la population,
			// puis on sélectionne le meilleur individu de chaque couple pour former 
			// un seul couple
			List<int[]> couple = new ArrayList<>();
			
			int indexPerson1 = randomGenerator.nextInt(populationRemaining.size());
			int indexPerson2 = randomGenerator.nextInt(populationRemaining.size());
			
			while(indexPerson1 == indexPerson2  || populationRemaining.get(indexPerson1).equals(populationRemaining.get(indexPerson2))) {
				indexPerson2 = randomGenerator.nextInt(populationRemaining.size());
			}
			
			int[] person1 = populationRemaining.get(indexPerson1);
			int[] person2 = populationRemaining.get(indexPerson2);
			
			int[] parent1 = bestPersonBetween2Person(person1, person2);
			
			int indexPerson3 = randomGenerator.nextInt(populationRemaining.size());
			int indexPerson4 = randomGenerator.nextInt(populationRemaining.size());
			
			while(indexPerson3 == indexPerson4) {
				indexPerson4 = randomGenerator.nextInt(populationRemaining.size());
			}
			
			int[] person3 = populationRemaining.get(indexPerson3);
			int[] person4 = populationRemaining.get(indexPerson4);
			
			int[] parent2 = bestPersonBetween2Person(person3, person4);
			
			//on enlève les parents de la liste de la population restante pour ne pas les
			// resélectionner lors de la prochaine itération
			populationRemaining.remove(parent1);
			populationRemaining.remove(parent2);
			
			//on ajoute chaque individu du couple
			couple.add(parent1);
			couple.add(parent2);
						
			//on ajoute le couple à la liste
			listCouple.add(couple);				
		}
		return listCouple;
	}
	
	/**
	 * cette méthode retourne le meilleur l'individu (celui qui rejette le moins de jobs) entre 2
	 * individus
	 * @param person1 l'individu 1
	 * @param person2 l'individu 2
	 * @return le meilleur individu
	 */
	public int[] bestPersonBetween2Person(int[] person1, int[] person2) {
		int jobsRejectedPerson1 = person1[person1.length-1];
		int jobsRejectedPerson2 = person2[person2.length-1];
		if(jobsRejectedPerson1 <= jobsRejectedPerson2) {
			return person1;
		}else{
			return person2;
		}
	}
}
