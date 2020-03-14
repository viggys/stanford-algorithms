package com.viggys.algorithms.part2.week1;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

	private int id;
	private int fTime;
	private boolean isExplored = false;
	private Vertex leader;

	public Vertex(int id) {
		this.id = id;
	}
	
	private List<Vertex> heads = new ArrayList<>();
	private List<Vertex> tails = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFTime() {
		return fTime;
	}

	public void setFTime(int fTime) {
		this.fTime = fTime;
	}

	public Vertex getLeader() {
		return leader;
	}

	public void setLeader(Vertex leader) {
		this.leader = leader;
	}

	public boolean isExplored() {
		return isExplored;
	}

	public void setExplored(boolean explored) {
		isExplored = explored;
	}

	public List<Vertex> getHeads() {
		return heads;
	}

	public void setHeads(List<Vertex> heads) {
		this.heads = heads;
	}

	public void addHead(Vertex head) {
		this.heads.add(head);
	}

	public List<Vertex> getTails() {
		return tails;
	}

	public void setTails(List<Vertex> tails) {
		this.tails = tails;
	}

	public void addTail(Vertex tail) {
		this.tails.add(tail);
	}

	@Override
	public String toString() {
		return "V[" + id + "]";
	}
	
	
}
