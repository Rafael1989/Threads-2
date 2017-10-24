package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;
	
	public ServidorTarefas() throws IOException {
		System.out.println("---- Iniciando servidor ----");
		this.servidor = new ServerSocket(12345);
		this.threadPool = Executors.newCachedThreadPool();
		this.estaRodando = new AtomicBoolean(true);
	}
	
	public void rodar() throws IOException {
		while(this.estaRodando.get()) {
			try {
				Socket socket = this.servidor.accept();
				System.out.println("Aceitando novo cliente na porta " + socket.getPort());
				
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this);
				this.threadPool.execute(distribuirTarefas);
			}catch(SocketException e) {
				System.out.println("SocketException, está rodando? " + this.estaRodando);
			}
		}
	}
	
	public void parar() throws IOException {
		System.out.println("Parando o servidor");
		this.estaRodando.set(false);
		this.threadPool.shutdown();
		this.servidor.close();
	}
	
	public static void main(String[] args) throws IOException {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
	}
}
