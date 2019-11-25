import java.io.*;
import java.net.*;
import java.util.*;

/**
A classe SupervisoraDeConexao é responsável por manter o servidor ativo.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@see java.io.*.
@see java.net.*.
@see java.util.*.
@since 2019.
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
    @param  conexao 	É a conexao entre o servidor e a aplicação.
    @param  jogadores	É a lista de jogadores conectados no momento.
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
    @see ObjectInputStream#close().
    @see ObjectOutputStream#close().
    @see ArrayList#add(Parceiro parceiro).
    @see Parceiro#receba(Comunicado x).
    @see Parceiro#envie().
    @see Parceiro#setNome().
    @see Parceiro#getNome().
    @see Parceiro#setJogada().
    @see Parceiro#getValorJogada().
    @see #quemGanhou().
    @see ArrayList#remove(Parceiro parceiro).
    @see Parceiro#adeus().
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
    
    /**
    O método quemGanhou() é responsável por verificar quem é o vencedor da rodada.
    Comparando as jogadas, o método descobre a situação da rodada, se é empate ou se há um ganhador.
    @see ArrayList#get(int index).
    @see Jogada#getJogada().
    @see Jogada#compareTo(Jogada outra).
    @return	Retorna uma string revelendo a situação da rodada, se houve um vencedor ou um empate.
    */
    
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
	
	
    public String toString()
    {
    	String ret =  "Valor: " + this.valor + " Jogador: " + this.jogador + " Conexão: " + this.conexao + " Jogadores: ";
	
	for(int i=0; i < this.jogadores.size(); i++)
	{
		ret += this.jogadores.get(i).getNome() + " ";
	}
	return ret;
    }
    
    public boolean equals(Object obj)
    {
    	if(obj == null)
		return false;
	if(obj == this)
		return true;
	if(obj.getClass() != this.getClass())
		return false;
	SupervisoraDeConexao supervisora = (SupervisoraDeConexao)obj;
	if(supervisora.valor != this.valor || supervisora.jogador != this.jogador || supervisora.conexao != this.conexao)
		return false;
		
	for(int i=0; i < this.jogadores.size(); i++)
	{
		if(!supervisora.jogadores.get(i).equals(this.jogadores.get(i)))
			return false;
	}
	
	return true;
    }
    
    public int hashCode()
    {
    	int ret = 666;
	
	ret = ret * 11 + new Double(valor).hashCode();
	ret = ret * 11 + this.jogador.hashCode();
	ret = ret * 11 + this.conexao.hashCode();
	for(int i=0; i < this.jogadores.size(); i++)
	{
		ret = ret * 11 + this.jogadores.get(i).hashCode();
	}
	
	return ret;
    }

}
