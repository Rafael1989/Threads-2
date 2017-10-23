package br.com.alura.thread;

import java.util.Set;

public class ThreadTest {
	public static void main(String[] args) {
		Set<Thread> todasAsThreads = Thread.getAllStackTraces().keySet();
		
		for(Thread thread : todasAsThreads) {
			System.out.println(thread.getName());
		}
		
		Runtime runtime = Runtime.getRuntime();
		int qtdProcessadores = runtime.availableProcessors();
		System.out.println("Qtd de processadores: " + qtdProcessadores);
	}
}
