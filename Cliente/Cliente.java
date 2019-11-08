import java.net.*;
import java.io.*;

public class Cliente
{
	public static final String HOST_PADRAO  = "177.220.18.44";
	public static final int    PORTA_PADRAO = 3000;

	public static void main (String[] args)
	{
		if (args.length>2)
		{
		    System.err.println ("Uso esperado: java Cliente [HOST [PORTA]]\n");
		    return;
        	}

		Socket conexao=null;
		try
		{
		    String host = Cliente.HOST_PADRAO;
		    int    porta= Cliente.PORTA_PADRAO;

		    if (args.length>0)
			host = args[0];

		    if (args.length==2)
			porta = Integer.parseInt(args[1]);
		    System.out.println (host+" "+porta);
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

		String nome = null;
		try
		{
			System.out.println("Digite seu nome:\n");
			nome = Teclado.getUmString();
		}
		catch(Exception ex)
		{

		}

		char opcao=' ';
		do
		{
		    System.out.print ("Sua jogada: \n"+
		    					"A - pedra \n" +
		    					"B - papel \n" +
		    					"C - tesoura\n");

		    try
		    {
				opcao = Character.toUpperCase(Teclado.getUmChar());
		    }
		    catch (Exception erro)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
		    }
		   if ("ABC".indexOf(opcao)==-1)
		   {
			System.err.println ("Opcao invalida!\n");
			continue;
		   }

			try
			{
				double valor=0;
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
		while (opcao != 'T');
    	}
}
