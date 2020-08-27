package it.polito.tdp.food.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {

	private Graph<Food,DefaultWeightedEdge> graph;
	List<Food> vertici;

	
	FoodDAO dao;
	
	public Model() {
		dao=new FoodDAO();

	}
	
	public void creaGrafo(int porzione) {
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		vertici=this.dao.getVertici(porzione);
		Graphs.addAllVertices(graph, vertici);
		
		for(Arco a: dao.getArco()) {
			if(this.graph.containsVertex(a.getF1()) && this.graph.containsVertex(a.getF2()) && !a.getF1().equals(a.getF2())){
				Graphs.addEdgeWithVertices(graph, a.getF1(), a.getF2(),a.getPeso());
			}
			
		}
		
	}
	
	
	public List<Food> getVertici(){
		return vertici;
	}
	//NUMERO VERTICI:

	public int nVertici() {
			return this.graph.vertexSet().size();
		}

	//NUMERO ARCHI:

		public int nArchi() {
			return this.graph.edgeSet().size();
		}
		
	public List<Arco> getArchiConnessi(Food f){
		
		List<Arco> result= new ArrayList<>();
		
		List<Food> vicini=Graphs.neighborListOf(graph, f);
		for(Food v: vicini) {
			Double peso=this.graph.getEdgeWeight(this.graph.getEdge(f, v));
			Arco a =new Arco(f,v,peso);
			result.add(a);
		}
		Collections.sort(result);
		return result;
		
	}
}
