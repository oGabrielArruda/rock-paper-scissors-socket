public class Resultado extends Comunicado
{
	private String ganhador;

	public Resultado(String ganhador)
	{
		this.ganhador = ganhador;
	}

	public String getGanhador()
	{
		return this.ganhador;
	}
}