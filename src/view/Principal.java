package view;

import java.util.concurrent.Semaphore;

import controller.ThreadEscuderia;

public class Principal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(5);
		
		System.out.println(" == A corrida começou !! == ");
		
		for (int i = 0; i < 7; i++) {
			Thread Escuderia = new ThreadEscuderia(i, semaforo);
			Escuderia.start();
		}
	}

}
