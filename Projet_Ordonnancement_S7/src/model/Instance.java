package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * cette classe représente une instance
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Instance {
	//le nombre de jobs
	private int nbJobs;
	//le nombre de ressources différentes
	private int nbResources;
	//le nombre de machine
	private int nbMachines;
	//la liste de tous les jobs
	private List<Job> listJobs = new ArrayList<>();
	//la liste de toutes les machines
	private List<Machine> listMachines = new ArrayList<>();
	
	/**
	 * @return le nombre de jobs
	 */
	public int getNbJobs() {
		return nbJobs;
	}
	/**
	 * @return le nombre de ressources différentes
	 */
	public int getNbResource() {
		return nbResources;
	}
	/**
	 * @return le nombre de machine
	 */
	public int getNbMachine() {
		return nbMachines;
	}
	/**
	 * @return la liste de tous les jobs
	 */
	public List<Job> getListJobs() {
		return listJobs;
	}
	/**
	 * @return la liste de toutes les machines
	 */
	public List<Machine> getListMachines() {
		return listMachines;
	}
	
	/**
	 * @return the nbRejetedJobs
	 */
	public int getNbRejetedJobs() {
		int nbAcceptedJobsT = 0;
		for(int i = 0; i < nbMachines; i++) {
			nbAcceptedJobsT += listMachines.get(i).getListJobs().size();
		}
		
		return (nbJobs - nbAcceptedJobsT);
	}
	
	/**
	 * permet de vider l'instance
	 */
	public void cleanInstance() {
		for(int i = 0; i < nbMachines; i++) {
			listMachines.get(i).getListJobs().clear();
			for(int j = 0; j < nbResources; j++) {
				for(int k = 0; k < 1440; k++) {
					listMachines.get(i).getListResourcesUsed().get(j)[k] = 0;
				}
			}
		}
	}
	
	/**
	 * Le parser permettant de lire un fichier de données est d'initialiser l'objet instance
	 * @param pathFile le chemin du fichier
	 * @throws IOException Problème avec le fichier
	 */
	public Instance(String pathFile) throws IOException{
		//ouverture du fichier
		BufferedReader sourceFile = new BufferedReader(new FileReader(pathFile));
		String line;
		int i = 0;
		int counterJob = 0;
		
		// tant que le curseur n'est pas à la fin du fichier
		while((line = sourceFile.readLine()) != null) {
			
			// récupération des informations de la première ligne du fichier
			if(i == 0) {
				String[] information = line.split(" ");
				this.nbJobs = Integer.parseInt(information[0]);
				this.nbResources = Integer.parseInt(information[1]);
				this.nbMachines = Integer.parseInt(information[2]);
				
				for(int j = 1; j <= this.nbMachines; j++) {
					this.listMachines.add(new Machine(j, this.nbResources));
				}
			}
			
			// récupération des informations concernant les ressources disponibles pour chaque machine
			if(i > 0 && i <= this.nbMachines) {
				String[] information = line.split(" ");
				for(int j = 0; j < this.nbResources; j++) {
					this.listMachines.get(i-1).getListResources().add(Integer.parseInt(information[j]));
				}
			}
			
			//récupération des informations concernant chaque job (munéro, date de début, date de fin)
			if(i > this.nbMachines + 1 && i <= (this.nbMachines + this.nbJobs + 1)) {
				String[] information = line.split("	");
				this.listJobs.add(new Job(Integer.parseInt(information[0]), Integer.parseInt(information[1]), Integer.parseInt(information[2])));
			}
			
			//recupération des informations concernant le nombre de ressources utilisées dans chaque type de ressources
			if(i > this.nbMachines + this.nbJobs + 2) {
				if(counterJob < this.nbJobs) {
					String[] information = line.split(" ");
					for(int j = 0; j < this.nbResources; j++) {
						this.listJobs.get(counterJob).getListResources().add(Integer.parseInt(information[j]));
					}
					counterJob++;
				}
			}
			
			
			i++;
		}
		//fermeture du fichier
		sourceFile.close();
	}
	
	/**
	 * retourne une machine en ayant passé un id en param
	 * @param idMachine l'identifiant de la machine
	 * @return une machine
	 */
	public Machine getMachineById(int idMachine) {
		for(Machine machine : listMachines) {
			if(machine.getId() == idMachine) {
				return machine;
			}
		}
		return null;
	}
	
	/**
	 * retourne un job en ayant passé un id en paramètre
	 * @param idJob l'identifiant du job
	 * @return un job
	 */
	public Job getJobById(int idJob) {
		for(Job job : listJobs) {
			if(job.getId() == idJob) {
				return job;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		String result;
		result = "Nombre jobs : " + this.nbJobs + "\n" +
				"Nombre machines : " + this.nbMachines + "\n" +
				"Nombre ressources : " + this.nbResources + "\n" +
				"-- Machine -- \n";
		for(int i = 0; i < this.listMachines.size(); i++) {
			result += this.listMachines.get(i).toString();
		}
		result += "-- Jobs -- \n";
		for(int i = 0; i < this.listJobs.size(); i++) {
			result += this.listJobs.get(i).toString();
		}
				
		return result;
	}
}
