package br.com.alura.cliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTarefas {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 12345);
		
		System.out.println("conexão estabelecida");
		
		Scanner teclado = new Scanner(System.in);
		teclado.nextLine();
		
		socket.close();
	}
}
