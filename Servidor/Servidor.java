import java.util.*;
import java.net.*;
public class Servidor
{
	public static void main(String[] args)
	{
		System.out.println("~Hospedar servidor para jogo~\n");
		System.out.println("Indique a porta desejada:");
		String porta = null;
		try
		{
			porta = Teclado.getUmString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Valor inválido");
			return;
		}
		ArrayList<Parceiro> jogadores = new ArrayList<Parceiro>();

		AceitadoraDeConexao aceitadoraDeConexao = null;
		try
		{
			 aceitadoraDeConexao = new AceitadoraDeConexao(porta, jogadores);
			 try
			 {
			     System.out.println("\nIp para jogo: "+InetAddress.getLocalHost().getHostAddress()+"\n");
			 }
			 catch(Exception e)
       		 {}
			aceitadoraDeConexao.start();
		}
		catch(Exception ex)
		{
			System.err.println("Escolha uma porta livre e apropriada para o uso!\n");
			return;
		}

		for(;;)
		{
			System.out.println ("O servidor esta ativo! Para desativa-lo,");
			System.out.println ("use o comando \"desativar\"\n");
            System.out.print   ("> ");

            String comando = null;
            try
            {
				comando = Teclado.getUmString();
			}
			catch(Exception ex)
			{}

			if(comando.toLowerCase().equals("desativar"))
			{
				synchronized(jogadores)
				{
					for(Parceiro jogador: jogadores)
					{
						ComunicadoDeDesligamento com = new ComunicadoDeDesligamento();
						try
						{
							jogador.receba(com);
							jogador.adeus();
						}
						catch(Exception ex)
						{}
					}
				}
				System.out.println("O servidor foi desativado!\n");
				System.exit(0);
			}
			else
				System.out.println("Comando inválido!\n");
		}
	}
}