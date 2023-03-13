package controller;

import java.util.concurrent.Semaphore;

public class ThreadEscuderia extends Thread{
	
	private Semaphore semaforo;
	private int idEscuderia;
	
	private static double[][] tempoCarros = new double[14][2];
	private static int carrosEmEspera = 14;
	
	public ThreadEscuderia(int idEscuderia, Semaphore semaforo) {
		this.semaforo = semaforo;
		this.idEscuderia = idEscuderia;
	}
	
	// TODO : Refatorar código
	
	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			int carro = i + (idEscuderia * 2);
			try {
				semaforo.acquire();
				tempoCarros[carro][0] = entrarPista(carro + 1);
				tempoCarros[carro][1] = carro + 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sairPista(carro + 1);
				carrosEmEspera--;
				semaforo.release();
			}
		}
		if (carrosEmEspera == 0) { //Este é o ultimo carro que faltava.
			mostrarResultados();
		}
	}

	private double entrarPista(int carro) {
		System.out.println("O carro #" + carro + " (Escuderia " + (idEscuderia + 1) + ") entrou na pista!");
		double voltaMaisRapida = 0;
		for (int j = 1; j <= 3; j++) {
			double tempoVolta = ((Math.random() * 20001) + 30000); // 30000 - 50000 ms ou 30 - 50 s.
			try {
				sleep((int)tempoVolta / 10); // Diminuir o valor para que o programa não demore muito tempo para executar 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tempoVolta /= 1000; // Converter para segundos.
			System.out.println("O carro #" + carro + " (Escuderia " + (idEscuderia + 1) + ") terminou sua " + j + "ª volta em " + String.format("%,.2f", tempoVolta) + " s.");
			voltaMaisRapida = (j == 1 || tempoVolta < voltaMaisRapida) ? tempoVolta : voltaMaisRapida;
		}
		return (voltaMaisRapida);
	}

	
	private void sairPista(int carro) {
		System.out.println("O carro #" + carro + " saiu da pista!");
	}

	
	private void mostrarResultados() {
		System.out.println(" == A corrida acabou!! == ");
		
		double[] temp = new double[2];
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 13 - i; j++) {
				if (tempoCarros[j][0] > tempoCarros[j + 1][0]) {
					temp = tempoCarros[j];
					tempoCarros[j] = tempoCarros[j + 1];
					tempoCarros[j + 1] = temp;
				}
			}
		}
		System.out.println("Resultados da corrida: ");
		for (int i = 0; i < 14; i++) {
			System.out.println((i + 1) + "º ) Carro #" + (int) tempoCarros[i][1] + " = " + String.format("%,.2f", tempoCarros[i][0])  + " s.");
		}
	}
}
