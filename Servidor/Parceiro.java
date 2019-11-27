import java.io.*;
import java.net.*;
import java.util.*;

/*
A classe Parceiro diz respeito a um usuário em comunicação com o servidor.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@see java.io.*.
@see java.net.*.
@see java.util.*.
@since 2019.
*/

public class Parceiro
{
    private Socket             conexao;
    private ObjectInputStream  receptor;
    private ObjectOutputStream transmissor;
    private Jogada jogada;
    private String nome;

    /**
    Constroi uma nova instância da classe Parceiro.
    Esta instância se refere a um usuário que estebelece uma conexão com o servidor, 
    recebendo e enviando informações para o mesmo.
    @param  conexao	Estabelece conexão com o servidor.
    @param  receptor	O servidor recebe informações provenientes do usuário.
    @param  transmissor	O usuário recebe informações provenientes do servidor.
    @throws Exception	Se a conexão for nula.
    @throws Exception	Se o receptor for nulo.
    @throws Exception	Se o transmissor for nulo.
    */
	
    public Parceiro (Socket             conexao,
                     ObjectInputStream  receptor,
                     ObjectOutputStream transmissor)
                     throws Exception // se parametro nulos
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (receptor==null)
            throw new Exception ("Receptor ausente");

        if (transmissor==null)
            throw new Exception ("Transmissor ausente");

        this.conexao     = conexao;
        this.receptor    = receptor;
        this.transmissor = transmissor;
    }
	
    /**
    O método receba() faz com que o usuário receba as informações do servidor.
    @param x		É o comunicado proveniente do servidor.
    @see		ObjectOutputStream#writeObject().
    @see		ObjectOutputStream#flush().
    @throws Exception	Se houver erro durante a transmissão de dados.
    */

    public void receba (Comunicado x) throws Exception
    {
        try
        {
            this.transmissor.writeObject (x);
            this.transmissor.flush       ();
        }
        catch (IOException erro)
        {
            throw new Exception ("Erro de transmissao");
        }
    }
	
    /**
    Este método é responsável por enviar informações do usuário para o servidor.
    @see 		ObjectInputStream#readObject().
    @throws Exception	Se houver erro durante a recepção de dados.
    @return		Um comunicado recebido do cliente.
    */

    public Comunicado envie () throws Exception
    {
        try
        {
            return (Comunicado)this.receptor.readObject();
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de recepcao");
        }
    }

    /**
    O método setJogada guarda uma jogada realizada pelo usuário.
    @param  jogada	Uma jogada do usuário.
    @throws Exception	Se a jogada for nula.
    */
	
    public void setJogada(Jogada jogada) throws Exception
    {
		if(jogada == null)
			throw new Exception("Jogada inválida");
		this.jogada = jogada;
	}
	
	/**
	Este método guarda o nome passado pelo usuário.
	@param  nome		É uma string com o nome passado.
	@throws Exception	Se o nome for nulo.
	*/

	public void setNome(String nome) throws Exception
	{
		if(nome == null)
			throw new Exception("Nome inválido!");
		this.nome = nome;
	}
	
	/**
	O método getJogada() retorna a jogada de determinado usuário.
	@return		Uma String com o valor da jogada.
	*/

	public Jogada getJogada()
	{
		return this.jogada;
	}
	
	/**
	O método getNome() retorna o nome de determinado usuário.
	@return		Uma String com o nome do usuário.
	*/

	public String getNome()
	{
		return this.nome;
	}
	
	/**
	Este método retira o jogador da partida caso o mesmo deseje.
	@see 			ObjectOutputStream#close().
	@see 			ObjectInputStream#close().
	@see 			Socket#close().
	@throws Exception	Se houver erro na desconexão.
	*/

    public void adeus () throws Exception
    {
        try
        {
            this.transmissor.close();
            this.receptor   .close();
            this.conexao    .close();
        }
        catch (Exception erro)
        {
            throw new Exception ("Erro de desconexao");
        }
    }	
}
