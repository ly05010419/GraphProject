package com.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;

public class JunitTest {

	@Test
	public void testShowfordFulkerson1() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph1 = new Graph("./assets/Fluss/Fluss.txt", true, true, false);
		ArrayList<Fluss> flussList = algorithmus.fordFulkerson(0, 7, graph1);

		float result = 0;
		for (Fluss fluss : flussList) {

			result += fluss.flussWert;
		}
		TestCase.assertTrue(4.0 == result);
	}

	@Test
	public void testShowfordFulkerson2() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph1 = new Graph("./assets/MST/G_1_2.txt", true, true, false);
		ArrayList<Fluss> flussList = algorithmus.fordFulkerson(0, 7, graph1);

		float result = 0;
		for (Fluss fluss : flussList) {

			result += fluss.flussWert;
		}

		TestCase.assertEquals(0.7358016, result, 0.0000001f);
	}

	@Test
	public void testDijkstra1() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph = new Graph("./assets/Wege/Wege1.txt", true, false, false);
		float result = algorithmus.dijkstra(2, 0, graph);
		TestCase.assertEquals(6, result, 0.1f);
		
	}

	@Test
	public void testDijkstra2() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph = new Graph("./assets/Wege/Wege2.txt", true, false, false);
		float result = algorithmus.mooreBellmanFord(2, 0, graph);

		TestCase.assertEquals(2, result, 0.1f);
	}
	
	@Test
	public void testDijkstra3() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph = new Graph("./assets/Wege/Wege3.txt", true, false, false);
		float result = algorithmus.mooreBellmanFord(2, 0, graph);

		TestCase.assertEquals(-1, result, 0.1f);
	}
	
	@Test
	public void testDijkstra4() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph = new Graph("./assets/MST/G_1_2.txt", true, false, false);
		float result = algorithmus.mooreBellmanFord(0, 1, graph);

		TestCase.assertEquals(5.544174, result, 0.000001f);
		
		float resultVonDijkstra = algorithmus.dijkstra(0, 1, graph);

		TestCase.assertEquals(5.544174, resultVonDijkstra, 0.000001f);
	}
	
	@Test
	public void testDijkstra5() throws Exception {
		Algorithmus algorithmus = new Algorithmus();
		Graph graph = new Graph("./assets/MST/G_1_2.txt", false, false, false);
		float result = algorithmus.mooreBellmanFord(0, 1, graph);

		TestCase.assertEquals(2.3679616, result, 0.000001f);
		
		float resultVonDijkstra = algorithmus.dijkstra(0, 1, graph);

		TestCase.assertEquals(2.3679616, resultVonDijkstra, 0.000001f);
	}
	

}
