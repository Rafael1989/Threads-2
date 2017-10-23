package br.com.alura.servidor;

import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable{

	private Socket socket;
	
	public DistribuirTarefas(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("Distribuindo tarefas para o cliente " + socket);
		
		try {
			Scanner entrada = new Scanner(socket.getInputStream());
			
			while(entrada.hasNextLine()) {
				String comando = entrada.nextLine();
				System.out.println(comando);
			}
			
			entrada.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
