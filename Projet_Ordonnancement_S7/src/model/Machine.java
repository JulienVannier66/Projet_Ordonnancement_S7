package model;

import java.util.ArrayList;
import java.util.List;
/**
 * cette classe repr�sente une machine
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Machine {
	
	//Le num�ro de la machine
	private int id;
	//La liste du nombre maximun de ressources � utilis� pour chaque ressource
	private List<Integer> listResources = new ArrayList<>();
	//La liste du nombre de ressources en cours d'utilisation � chaque instant t
	private List<int[]> listResourcesUsed = new ArrayList<>();
	//La liste des jobs
	private List<Job> listJobs = new ArrayList<>();
	//le nombre de jobs accept�s
	
	/**
	 * Constructeur de base de la classe Machine
	 * @param id le num�ro identifiant de la machine
	 * @param nbresources le nombre de ressources attribu� � la machine
	 */
	public Machine(int id, int nbresources) {
		this.id=id;
		for(int i = 0; i < nbresources; i++) {
			int[] resource = new int[1441];
			for(int j = 0; j < 1441; j++) {
				resource[j] = 0;
			}
			listResourcesUsed.add(resource);
		}
	}
	
	/**
	 * @return le num�ro identifiant de la machine
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return la liste du nombre maximun de ressources � utilis� pour chaque ressource
	 */
	public List<Integer> getListResources() {
		return listResources;
	}

	/**
	 * @return la liste du nombre de ressources utilis�es
	 */
	public List<int[]> getListResourcesUsed() {
		return listResourcesUsed;
	}

	/**
	 * @return la liste des jobs
	 */
	public List<Job> getListJobs() {
		return listJobs;
	}

	/**
	 * Permet d'ajouter un job � une machine
	 * @param job le job � ajouter
	 * @return true s'il est possible de l'ajouter, false sinon
	 */
	public boolean addJob(Job job) {
		boolean accept = true;
		int i = 0;

		//v�rification
		while(i < listResources.size() && accept) {
			int lengthJob = job.getStartTime();
			int resource = job.getListResources().get(i).intValue();
			while(lengthJob <= job.getEndTime() && accept) {
				if(!((listResourcesUsed.get(i)[lengthJob] + resource) <= listResources.get(i).intValue())) {
					accept = false;
				}
				lengthJob++;
			}
			i++;
		}
		
		i = 0;
		
		//�criture
		while(i < listResources.size() && accept) {
			int lengthJob = job.getStartTime();
			int resource = job.getListResources().get(i).intValue();
			while(lengthJob <= job.getEndTime() && accept) {
				listResourcesUsed.get(i)[lengthJob] += resource;
				lengthJob++;
			}
			i++;
		}
		
		if(accept) {
			listJobs.add(job);
		}
		return accept;
	}
	
	@Override
	public String toString() {
		String result;
		result = "Machine n�" + this.id + " R : ";
		for(int i = 0; i < this.listResources.size(); i++) {
			result += this.listResources.get(i).intValue() + " ";
		}
		result += "\n";
		return result;
	}
	
}
