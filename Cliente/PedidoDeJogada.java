public class PedidoDeJogada extends Comunicado
{
	private Jogada jogada;
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

	public Jogada getValorJogada()
	{
		return this.jogada;
	}
}