public class PedidoDeJogada extends Comunicado
{
	private Jogada jogada;
	public PedidoDeJogada(String strJogada)
	{
		this.jogada = new Jogada(strJogada);
	}

	public Jogada getValorJogada()
	{
		return this.jogada;
	}
}