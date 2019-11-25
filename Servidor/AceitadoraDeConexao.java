import java.net.*;
import java.util.*;

/** 
A classe AceitadoraDeConexao realiza conexões entre o cliente e o servidor.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@see import java.net.*.
@see import java.util.*.
@since 2019.
*/

public class AceitadoraDeConexao extends Thread
{
    private static final int PORTA_PADRAO = 3000;

    private ServerSocket        pedido;
    private ArrayList<Parceiro> jogadores;

    /**
    Constroi uma nova instância da classe AceitadoraDeConexao.
    No entanto, é necessário que se passe uma string que será a porta e
    um ArrayList com os jogadores.
    @param escolha 	É a string na qual está armazenada a porta.
    @param jogadores 	É a lista de jogares da classe Parceiro.
    @see		Integer#Integer.parseInt(int value).
    @throws Exception 	Se a porta for inválida.
    @throws Exception 	Se a lista de jogadores estiver vazia.
    */
    
    public AceitadoraDeConexao
    (String escolha, ArrayList<Parceiro> jogadores)
    throws Exception
    {
        int porta = AceitadoraDeConexao.PORTA_PADRAO;

        if (escolha!=null)
        {
			porta = Integer.parseInt(escolha);
		}

       	try
        {
            this.pedido = new ServerSocket (porta);
        }
        catch (Exception  erro)
        {
            throw new Exception ("Porta invalida");
		}

        if (jogadores==null)
            throw new Exception ("Usuarios ausentes");

        this.jogadores = jogadores;
    }
	
    /**
    Este é o método responsável por aceitar as conexões entre o servidor e o cliente.
    @see ServerSocket#accept().
    @see SupervisoraDeConexao#start().
    */

    public void run ()
    {
        for(;;)
        {
            Socket conexao=null;
            try
            {
                conexao = this.pedido.accept();
                System.out.println ("Jogador Conectado!");
            }
            catch (Exception erro)
            {
                continue;
            }

            SupervisoraDeConexao supervisoraDeConexao=null;
            try
            {
                supervisoraDeConexao =
                new SupervisoraDeConexao (conexao, jogadores);
            }
            catch (Exception erro)
            {} // sei que passei parametros corretos para o construtor
            supervisoraDeConexao.start();
        }
    }
    
    public String toString()
    {
    	String ret = "Pedido: " + this.pedido + "Jogadores: ";
	
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
	AceitadoraDeConexao aceitadora = (AceitadoraDeConexao)
	if(aceitadora.pedido != this.pedido)
		return false;
		
	for(int i=0; i < this.jogadores.size(); i++)
	{
		if(this.jogadores.get(i).equals(aceitadora.jogadores.get(i)))
			return false;
	}
	return true;
    }
    
    public int hashCode()
    {
    	int ret = 666;
	
	ret = ret * 11 + this.pedido.hashCode();
	
	for(int i = 0; i < this.jogadores.size(); i++)
	{
		ret = ret * 11 + this.jogadores.get(i).hashCode();
	}
	return ret;
    }
}
