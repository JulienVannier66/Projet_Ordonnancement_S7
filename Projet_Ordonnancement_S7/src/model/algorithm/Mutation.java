package model.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import model.Instance;
/**
 * cette classe contient les m�thodes de mutation d'un algorithme g�n�tique
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Mutation {

	double mutationProbability = 0;
	
	/**
	 * Constructeur de la classe Mutation
	 * @param mutationProbability la probabilit� de mutation
	 */
	public Mutation(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	
	/**
	 * Cette m�thode retourne la probabilit� de mutation.
	 * @return la probabilit� de mutation
	 */
	public double getMutationProbability() {
		return mutationProbability;
	}

	/**
	 * Cette m�thode permet de tirer un nombre al�atoire entre 0 et 1, et si le
	 * nombre tir� est inf�rieur � la probabilit� de mutation, alors on effectue 
	 * une mutation sur l'individu en s�lectionnant un job au hasard et en modifiant
	 * sa valeur par une autre.
	 * @param person l'individu � muter
	 * @param instance l'instance
	 */
	public void mutatePerson(int[] person, Instance instance) {
		
		Random randomGenerator = new Random();
		// generer un nombre al�atoire entre 0 et 1
		double randomNumber = randomGenerator.nextDouble();
		//si le nombre g�n�r� est inf�rieur au taux de mutation,
		//alors on fait la mutation
		if(randomNumber <= mutationProbability) {
			int indexJobMutation = randomGenerator.nextInt(person.length);
			
			// on tire au sort la nouvelle valeur que prendra l'indice s�lectionn�
			int indexMachineMutation = randomGenerator.nextInt(instance.getNbMachine()+1);
			
			while(indexMachineMutation == person[indexJobMutation]) {
				indexMachineMutation = randomGenerator.nextInt(instance.getNbMachine()+1);
			}
			person[indexJobMutation] = indexMachineMutation;
		}
	}
	
	/**
	 * Cette m�thode tire al�atoire trois t�ches de l'individu pour les rejeter
	 * La valeur mise est 0
	 * @param person l'individu subissant la mutation
	 */
	public void mutateOnePersonThreeTime0(int[] person) {
		Random randomGenerator = new Random();
		
		for(int i = 0; i < 3; i++) {
			int indexJobMutation = randomGenerator.nextInt(person.length - 1);
			
			if(person[indexJobMutation] != 0) {
				person[indexJobMutation] = 0;
				person[person.length - 1] += 1;
			}
		}
	}
	
	/**
	 * Cette m�thode permet de muter tous les clones d'une population donn�es
	 * La mutation utilis�e est mutateOnePersonThreeTime0
	 * @param population une population d'individus
	 */
	public void mutateAllClone(List<int[]> population) {
		List<int[]> listOriginal = new ArrayList<>();
		List<int[]> listClone = new ArrayList<>();
		
		for(int i = 0; i < population.size(); i++) {
			int j = 0;
			boolean clone = false;
			//on test avec tous les individus connu diff�rents si il y est un clone
			while(j < listOriginal.size() && !clone) {
				if(Arrays.equals(listOriginal.get(j), population.get(i))) {
					listClone.add(population.get(i));
					clone = true;
				}
				j++;
			}
			//si pas de clone, on l'ajoute aux individus diff�rents
			if(!clone) {
				listOriginal.add(population.get(i));
			}
		}
		
		for(int i = 0; i < listClone.size(); i++) {
			mutateOnePersonThreeTime0(listClone.get(i));
		}
	}
}
