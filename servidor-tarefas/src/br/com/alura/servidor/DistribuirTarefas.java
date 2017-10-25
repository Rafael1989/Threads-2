package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable{

	private Socket socket;
	private ServidorTarefas servidorTarefas;
	private ExecutorService threadPool;
	
	public DistribuirTarefas(ExecutorService threadPool, Socket socket, ServidorTarefas servidorTarefas) {
		this.threadPool = threadPool;
		this.socket = socket;
		this.servidorTarefas = servidorTarefas;
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
					saidaCliente.println("Confirma��o do comando c1");
					ComandoC1 c1 = new ComandoC1(saidaCliente);
					this.threadPool.execute(c1);
					break;
				case "c2":
					saidaCliente.println("Confirma��o do comando c2");
					ComandoC2AcessaBanco comandoC2AcessaBanco = new ComandoC2AcessaBanco(saidaCliente);
					ComandoC2ChamaWS comandoC2ChamaWS = new ComandoC2ChamaWS(saidaCliente);
					Future<String> futureBanco = this.threadPool.submit(comandoC2AcessaBanco);
					Future<String> futureWS = this.threadPool.submit(comandoC2ChamaWS);
					Callable<Void> juntaResultados = new JuntaResultadosFutureWSFutureBanco(futureWS, futureBanco, saidaCliente);
					this.threadPool.submit(juntaResultados);
					break;
				case "fim":
					saidaCliente.println("Desligando o servidor");
					servidorTarefas.parar();
					return;
				default:
					saidaCliente.println("Comando n�o encontrado");
				}
			}
			
			saidaCliente.close();
			entrada.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
