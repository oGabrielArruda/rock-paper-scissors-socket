/**
A classe Resultado representa a situação da rodada.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@since 2019.
*/

public class Resultado extends Comunicado
{
	private String ganhador;

	/**
	Constroi uma nova instância da classe Resultado.
	Para tal, o constutor necessita de um parâmetro 
	que contenha o nome do ganhador ou a situação de
	empate.
	@param ganhador		Uma String com o ganhador da rodada ou com o valor "empate".
	*/

	public Resultado(String ganhador)
	{
		this.ganhador = ganhador;
	}

	/**
	O método getGanhador() retorna o nome do ganhador
	da rodada.
	@return		Retorna uma String com o nome do vencedor.
	*/
	
	public String getGanhador()
	{
		return this.ganhador;
	}
	
	public String toString()
	{
		return "Ganhador: " + this.ganhador;
	}
	
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj.getClass() != this.getClass())
			return false;
		Resultado result = (Resultado)obj;
		if(result.ganhador != this.ganhador)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int ret = 666;
		
		ret = ret * 11 + this.ganhador.hashCode();
		
		return ret;
	}
}
