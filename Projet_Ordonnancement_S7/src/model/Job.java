package model;

import java.util.ArrayList;
import java.util.List;

/**
 * cette classe repr�sente un job ou t�che
 * @author Martin De La Funente
 * @author Julien Vannier
 *
 */
public class Job {
	//le num�ro identifiant du job
	private int id;
	//la date de d�but
	private int startTime;
	//la date de fin
	private int endTime;
	//le num�ro de la machine dans lequel est ex�cut� le job, 0 = attribuer � aucune machine
	private int idMachine = 0;
	//la liste des diff�rentes ressources n�cessaires
	private List<Integer> listResources = new ArrayList<>();
	
	/**
	 * Le constructeur de base de la classe Job
	 * @param id le num�ro identifiant de la machine
	 * @param startTime la date de d�but
	 * @param endTime la date de fin
	 */
	public Job(int id, int startTime, int endTime) {
		this.id=id;
		this.startTime=startTime;
		this.endTime=endTime;
	}

	/**
	 * @return le num�ro identifiant de la machine sur lequel il est ex�cut�
	 */
	public int getIdMachine() {
		return idMachine;
	}
	
	/**
	 * @param id le num�ro identifiant du job
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @param idMachine le num�ro identifiant de la machine
	 */
	public void setIdMachine(int idMachine) {
		this.idMachine = idMachine;
	}

	/**
	 * @return le num�ro identifiant du job
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return la date de d�but
	 */
	public int getStartTime() {
		return startTime;
	}
	/**
	 * @return la date de fin
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * @return la liste des diff�rentes ressources n�cessaires
	 */
	public List<Integer> getListResources() {
		return listResources;
	}
	
	@Override
	public String toString() {
		String result;
		result = this.id + " " + this.startTime + " " + this.endTime + " M:" + this.idMachine + " R: ";
		for(int i = 0; i < this.listResources.size(); i++) {
			result += this.listResources.get(i).intValue() + " ";
		}
		result += "\n";
		return result;
	}
}
