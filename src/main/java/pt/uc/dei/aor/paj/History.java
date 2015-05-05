package pt.uc.dei.aor.paj;
import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class History implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7514545189871598589L;
	ArrayList<String> lista;
	
	
	//Constructor
	public History(){
		lista = new ArrayList<String>();
	}

	//ArrayList with historic getter
	public ArrayList<String> getLista() {
		return lista;
	}
	
	//ArrayList with historic setter
	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
	
	//Adds another expression to historic ArrayList
	public void addHist(String srt){
		boolean exists=false;
		for (String x: lista){
			if (x.equals(srt)){
				exists=true;
			}
		}
		if (!exists){
			lista.add(srt);
		}
	}
	
	
	
	
	
}
