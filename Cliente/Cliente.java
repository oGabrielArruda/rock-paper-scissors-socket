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
        {			erro.printStackTrace();

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
        {			erro.printStackTrace();

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
        {			erro.printStackTrace();

            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor=null;
        try
        {
            servidor =
            new Parceiro (conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {			erro.printStackTrace();

            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        char opcao=' ';
        do
        {
            System.out.print ("Sua opcao (" +
                              "A=Adicao/" +
                              "S=Subtracao/" +
                              "M=Multiplicacao/" +
                              "D=Divisao/" +
                              "V=Ver valor/" +
                              "T=Terminar)" +
                              "? ");

            try
            {
				opcao = Character.toUpperCase(Teclado.getUmChar());
		    }
		    catch (Exception erro)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}

			if ("ASMDVT".indexOf(opcao)==-1)
		    {
				System.err.println ("Opcao invalida!\n");
				continue;
			}

			try
			{
				double valor=0;
				if ("ASMD".indexOf(opcao)!=-1)
				{
					System.out.print ("Valor? ");
					try
					{
						valor = Teclado.getUmDouble();
					}
					catch (Exception erro)
					{
						System.err.println ("Valor invalido!\n");
						continue;
					}

					switch (opcao)
					{
						case 'A':
							servidor.receba (new PedidoDeAdicao (valor));
							break;

						case 'S':
							servidor.receba (new PedidoDeSubtracao (valor));
							break;

						case 'M':
							servidor.receba (new PedidoDeMultiplicacao (valor));
							break;

						case 'D':
							servidor.receba (new PedidoDeDivisao (valor));
					}
				}
				else if (opcao=='V')
				{
					servidor.receba (new PedidoDeResultado ());
					Resultado resultado = (Resultado)servidor.envie ();
					System.out.println ("Resultado atual: "+resultado.getValorResultante());
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