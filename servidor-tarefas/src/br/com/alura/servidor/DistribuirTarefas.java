package br.com.alura.servidor;

import java.io.PrintStream;
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
			
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			
			while(entrada.hasNextLine()) {
				String comando = entrada.nextLine();
				
				System.out.println(comando);
				
				switch(comando) {
				case "c1":
					saidaCliente.println("Confirmação do comando c1");
					break;
				case "c2":
					saidaCliente.println("Confirmação do comando c2");
					break;
				default:
					saidaCliente.println("Comando não encontrado");
				}
			}
			
			saidaCliente.close();
			entrada.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
