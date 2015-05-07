package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named
@ApplicationScoped
public class Statistic implements Serializable{

	private static final long serialVersionUID = 2824077580964346416L;
	ArrayList<String> lista;
	String[] listastat;
	
	//Class constructor
	public Statistic(){
		lista = new ArrayList<String>();
		listastat = new String[4];
	}

	//List with stats getter
	public ArrayList<String> getLista() {
		return lista;
	}
	
	//List with stats setter
	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
	
	//Adds another expression to list with stats
	public void addStat(String srt){
		lista.add(srt);
	}

	public String[] getListastat() {
		int addition=0, subtraction=0, multiplication=0, division=0;
		for (String x: lista){
			for (int i=0; i<x.length(); i++){
				if (x.charAt(i)=='+'){
					addition++;		
				}else if (x.charAt(i)=='-'){
					subtraction++;
				}else if (x.charAt(i)=='*'){
					multiplication++;
				}else if (x.charAt(i)=='/'){
					division++;
				}	
			}
		}
		listastat[0]="Sums: "+addition;
		listastat[1]="Subtractions: "+subtraction;
		listastat[2]="Multiplications: "+multiplication;
		listastat[3]="Divisions "+division;
		return listastat;
	}

	public void setListastat(String[] listastat) {
		this.listastat = listastat;
	}
	
	

}
