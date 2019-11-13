import java.net.*;
import java.io.*;

public class Cliente
{
	public static void main (String[] args)
	{
		System.out.println(" ~~ Jogo  Pedra Papel Tesoura ~~ ");
		System.out.println("Para começar, digite o ip e porta da sala desejada");
		System.out.println("Caso deseja realizar um jogo local, digite localhost");

		String host = null;
		int porta = 0;
		try
		{
			System.out.println("Ip:");
			host = Teclado.getUmString();
			System.out.println("Porta:");
			porta = Teclado.getUmInt();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Valores inválidos");
			return;
		}

		Socket conexao=null;
		try
		{
		    conexao = new Socket (host, porta);
		}
		catch (Exception erro)
		{
		    erro.printStackTrace();

		    System.err.println ("Indique o servidor e a porta corretos!\n");
		    return;
		}

		ObjectOutputStream transmissor=null;
		try
		{
		    transmissor =
		    new ObjectOutputStream(
		    conexao.getOutputStream());
		}
		catch (Exception erro)
		{
		    erro.printStackTrace();

		    System.err.println ("Indique o servidor e a porta corretos!\n");
		    return;
		}

		ObjectInputStream receptor=null;
		try
		{
		    receptor =
		    new ObjectInputStream(
		    conexao.getInputStream());
		}
		catch (Exception erro)
		{
		    erro.printStackTrace();
		    System.err.println ("Indique o servidor e a porta corretos!\n");
		    return;
		}

		Parceiro servidor=null;
		try
		{
		    servidor = new Parceiro (conexao, receptor, transmissor);
		}
		catch (Exception erro)
		{
		    erro.printStackTrace();

		    System.err.println ("Indique o servidor e a porta corretos!\n");
		    return;
		}


		try
		{
			System.out.println("Aguardando um oponente...");
			ComunicadoComecar podeIr = (ComunicadoComecar)servidor.envie();
		}
		catch(Exception e)
		{}


		String nome = null;
		try
		{
			System.out.println("Digite seu nome:\n");
			nome = Teclado.getUmString();
			servidor.receba(new PedidoDeNome(nome));
		}
		catch(Exception ex)
		{
		    System.err.println ("Nome inválido!\n");
		    return;
		}

		char opcao=' ';
		do
		{
		    System.out.print ("Sua jogada: \n"+
		    					"A - pedra \n" +
		    					"B - papel \n" +
		    					"C - tesoura\n" +
		    					"Z - Sair do jogo\n");

		    try
		    {
				opcao = Character.toUpperCase(Teclado.getUmChar());
		    }
		    catch (Exception erro)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
		    }
		   if ("ABCZ".indexOf(opcao)==-1)
		   {
			System.err.println ("Opcao invalida!\n");
			continue;
		   }

			try
			{
				if ("ABC".indexOf(opcao)!=-1)
				{
					switch (opcao)
					{
						case 'A':
							servidor.receba (new PedidoDeJogada("pedra"));
							break;

						case 'B':
							servidor.receba (new PedidoDeJogada("papel"));
							break;

						case 'C':
							servidor.receba (new PedidoDeJogada("tesoura"));
							break;
					}
					try
					{
						Resultado resultado = (Resultado) servidor.envie();
						System.out.println("Ganhador: " + resultado.getGanhador() + "\n");
					}
					catch(Exception e)
					{
						System.out.println("aaa");
					}
				}
			}
			catch (Exception erro)
			{
				System.err.println ("Erro de comunicacao com o servidor;");
				System.err.println ("Tente novamente!");
				System.err.println ("Caso o erro persista, termine o programa");
				System.err.println ("e volte a tentar mais tarde!");
			}
		}
		while (opcao != 'Z');
    	}
}
