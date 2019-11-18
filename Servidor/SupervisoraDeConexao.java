import java.io.*;
import java.net.*;
import java.util.*;

/**
A classe SupervisoraDeConexao é responsável por manter o servidor ativo.
@author Gabriel Alves Arruda
@see java.io.*
@see java.net.*
@see java.util.*
@since 2019
*/


public class SupervisoraDeConexao extends Thread
{
    private double              valor=0;
    private Parceiro            jogador;
    private Socket              conexao;
    private ArrayList<Parceiro> jogadores;
    private static int nmrJogadas = 0;
    private static int qtdJogadores = 0;

    /**
    Constroi uma nova instância da classe SupervisoraDeConexao.
    Este construtor recebe uma conexao entre o servidor e o cliente e uma lista de jogadores.
    @param conexao 	É a conexao entre o servidor e a aplicação.
    @param jogadores	É a lista de jogadores conectados no momento.
    @throws Exception	Caso a conexão seja nula.
    @throws Exception	Se a lista estiver vazia.
    */
    
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

    /**
    Este é o método responsável por manter a comunicação ativa entre servidor e aplicação.
    @see Socket#getInputStream().
    @see Socket#getOutputStream().
    @see ObjectInputStream#close()
    @see ArrayList#add().
    @see Parceiro#receba(Comunicado x).
    @see Parceiro#envie().
    @see Parceiro#setNome().
    @see Parceiro#getNome().
    */
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
                this.qtdJogadores++;
                if(this.qtdJogadores == 2)
                	for(Parceiro jogador: this.jogadores)
                	{
						jogador.receba(new ComunicadoComecar(true));
					}
            }

            for(;;)
            {
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

					    System.out.println("jogada: " + nmrJogadas);
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
