import java.net.*;
import java.io.*;
import java.util.Scanner;

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
			System.out.println("Conectado! \n");
			System.out.println("Aguardando um oponente...");
			Comunicado comunicado = servidor.envie();
			if(comunicado instanceof ComunicadoDeDesligamento)
			{
				System.out.println("Jogo cheio! 2/2");
				System.out.println("Crie um servidor com outra porta!");
				return;
			}
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
			try
			{
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				System.out.println(" ~~ Jogo  Pedra Papel Tesoura ~~ \n");
				System.out.println("Usuário: " + nome);
			}
			catch(Exception erro)
			{
				System.err.println(erro.getMessage());
			}
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
						e.printStackTrace();
						System.err.println("Erro ao escolher sua jogada");
						return;
					}
				}
				else if(opcao == 'Z')
				{
					servidor.receba(new PedidoParaSair());
				}
			}
			catch (Exception erro)
			{
				System.err.println ("Erro de comunicacao com o servidor;");
				System.err.println ("Tente novamente!");
				System.err.println ("Caso o erro persista, termine o programa");
				System.err.println ("e volte a tentar mais tarde!");
			}
			if(opcao != 'Z')
			{
				System.out.println("Pressione [Enter] para continuar");
				new Scanner(System.in).nextLine();
			}
		}
		while (opcao != 'Z');
    	}
}

