package it.polito.tdp.artsmia.model;

import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;


public class Model {
	private ArtsmiaDAO dao;
	private List <String> ruoli;
	private List <Integer>artisti;
	private List <Arco> connessioni;
	private  SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo;

	public Model() {
		dao= new ArtsmiaDAO();
		ruoli=dao.listRoles();
	}

	public List<String> getRuoli() {
		return ruoli;
	}
	
	public void buildGraph(String ruolo) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		artisti=dao.listArtistbyRole(ruolo);
		Graphs.addAllVertices(this.grafo,artisti );
		System.out.println("MODEL : numerodi vertici: "+this.grafo.vertexSet().size());
		connessioni=dao.listEdges(ruolo);
		for (Arco a:connessioni) {
			if(grafo.containsVertex(a.getArtist1()) && grafo.containsVertex(a.getArtist2())) {
			Graphs.addEdge(this.grafo, a.getArtist1(), a.getArtist2(), a.getWeight());}
		}
		System.out.println("MODEL : numerodi archi: "+this.grafo.edgeSet().size());
	}
	
	public List<Arco> artistiConnessi(){
		//Collections.sort(this.connessioni);
		
		System.out.println("MODEL : invio lista connessioni dimensione: "+this.connessioni.size());
		return connessioni;
		
	}
}
