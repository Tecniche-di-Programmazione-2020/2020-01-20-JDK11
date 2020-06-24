package it.polito.tdp.artsmia.model;

public class Arco implements Comparable<Arco>{
	Integer artist1;
	Integer artist2;
	Integer weight;
	public Arco(Integer artist1, Integer artist2, Integer weight) {
		super();
		this.artist1 = artist1;
		this.artist2 = artist2;
		this.weight = weight;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artist1 == null) ? 0 : artist1.hashCode());
		result = prime * result + ((artist2 == null) ? 0 : artist2.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		if (artist1 == null) {
			if (other.artist1 != null)
				return false;
		} else if (!artist1.equals(other.artist1))
			return false;
		if (artist2 == null) {
			if (other.artist2 != null)
				return false;
		} else if (!artist2.equals(other.artist2))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	public Integer getArtist1() {
		return artist1;
	}
	public Integer getArtist2() {
		return artist2;
	}
	public Integer containsVertex() {
		return artist2;
	}
	public Integer getWeight() {
		return weight;
	}
	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return this.weight-o.getWeight();
	}
	@Override
	public String toString() {
		return "[artista1=" + artist1 + ", artista2=" + artist2 + ", weight=" + weight + "]\n";
	}
	

}
