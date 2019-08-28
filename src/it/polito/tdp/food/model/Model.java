package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {

	private FoodDao dao;
	private List<Condiment> ingredienticalorie;
	private SimpleWeightedGraph<Condiment,DefaultWeightedEdge> grafo;
	
	public Model() {
		dao= new FoodDao();
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}

	public void creagrafo(double calorie) {
		int peso=0;
		ingredienticalorie= this.dao.listAllCondimentbycalories(calorie);
		Graphs.addAllVertices(this.grafo,ingredienticalorie);
		for (Condiment c1: ingredienticalorie)
			for(Condiment c2: ingredienticalorie) {
				if( !c1.equals(c2)) {
				peso=this.dao.calcolapeso(c1, c2);
				if(peso>0) {
					DefaultWeightedEdge e1= this.grafo.getEdge(c1, c2);
					DefaultWeightedEdge e2= this.grafo.getEdge(c2, c1);

					if(e1==null && e2==null)
						Graphs.addEdge(this.grafo, c1, c2, peso);
				}
			}
		}
	}
	
	public List<Condiment> get_ingredienti() {
		Collections.sort(ingredienticalorie);
		return ingredienticalorie;
	}

	/*public int pesitotali(Condiment c) {
		int pesototale=0;
		for(DefaultWeightedEdge e: this.grafo.edgesOf(c))
			pesototale=(int) (pesototale+ this.grafo.getEdgeWeight(e));
		return pesototale;
	}*/
}
