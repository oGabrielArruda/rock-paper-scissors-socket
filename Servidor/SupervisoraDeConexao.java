import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private double              valor=0;
    private Parceiro            jogador;
    private Socket              conexao;
    private ArrayList<Parceiro> jogadores;
    private static int nmrJogadas = 0;

    public SupervisoraDeConexao
    (Socket conexao, ArrayList<Parceiro> jogadores)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (jogadores==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.jogadores = jogadores;
    }

    public void run ()
    {
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception erro)
        {
            return;
        }

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            try
            {
                receptor.close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }

        try
        {
            this.jogador =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.jogadores)
            {
                this.jogadores.add (this.jogador);
            }


            for(;;)
            {
				if(this.jogadores.size() != 2)
					return;

				for(Parceiro jogador: this.jogadores) // se entrou aqui é pq tem 2 usuarios conectados
					jogador.receba(new ComunicadoComecar(true));

                Comunicado comunicado = this.jogador.envie ();

                 if(comunicado==null)
                    return;

				  if(comunicado instanceof PedidoDeNome)
				        this.jogador.setNome(((PedidoDeNome)comunicado).getNome());
				  else
				  {
					  nmrJogadas++;

             		  if(comunicado instanceof PedidoDeJogada)
             		   	this.jogador.setJogada(((PedidoDeJogada)comunicado).getValorJogada());


             		   if(nmrJogadas == 2)
             		   {
							String ganhador = quemGanhou();
							for(Parceiro jogador:this.jogadores)
             			   		jogador.receba(new Resultado(ganhador));
             			   	nmrJogadas = 0;
					    }
                		else if (comunicado instanceof PedidoParaSair)
                		{
                	    	synchronized (this.jogadores)
                	    	{
                	    	    this.jogadores.remove (this.jogador);
                	    	}
                	    	this.jogador.adeus();
                		}
            		}
				 }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }

    private String quemGanhou()
    {
		Jogada jogada1 = this.jogadores.get(0).getJogada();
		Jogada jogada2 = this.jogadores.get(1).getJogada();

		int comp = jogada1.compareTo(jogada2);

		if(comp == 0)
			return "empate";
		if(comp > 0)
			return this.jogadores.get(0).getNome();
		return this.jogadores.get(1).getNome();
	}
}
