/**
A classe PedidoDeJogada representa uma escolha de jogada feita pelo usuário.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@since 2019.
*/

public class PedidoDeJogada extends Comunicado
{
	private Jogada jogada;
	
	/**
	Constroi uma nova instância da classe PedidoDeJogada.
	No entanto, precisa de um parâmetro String que contenha o valor da jogada.
	@param			Uma String com o valor da jogada.
	@throws Exception	Se houver erro em alguma parte do programa que utilize essa classe.
	*/
	
	public PedidoDeJogada(String strJogada) throws Exception
	{
		try
		{
			this.jogada = new Jogada(strJogada);
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}
	
	/**
	O método getValorJogada() recupera o valor da jogada do usuário.
	@return 	Retorna uma jogada.
	*/

	public Jogada getValorJogada()
	{
		return this.jogada;
	}
}
