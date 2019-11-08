public class Jogada
{
	private String valor;

	public Jogada(String valor) throws Exception
	{
		if(valor == null)
			throw new Exception("Valor para jogada inválida");
		this.valor = valor;
	}

	public int compareTo(Jogada outra)
	{
		if(this.valor.equals(outra.valor))
			return 0;
		if( (this.valor.equals("tesoura") && outra.valor.equals("papel")) ||
		(this.valor.equals("pedra") && outra.valor.equals("tesoura")) ||
		(this.valor.equals("papel") && outra.valor.equals("pedra")))
			return 1;

		return -1;
	}
}