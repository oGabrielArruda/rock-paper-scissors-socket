/**
Esta classe é responsável comunicar ao servidor que a partida pode começar.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@since 2019.
*/

public class ComunicadoComecar extends Comunicado
{
	private boolean podeIr;
	
	/**
	Constroi uma nova instância da classe ComunicadoComecar.
	Essa necessita de um parâmetro boolean para sinalizar o começo da partida.
	@param pode	É uma variável boolean que indica se a partida pode ou não começar.
	*/
	
	public ComunicadoComecar(boolean pode)
	{
		this.podeIr = pode;
	}
	
	/**
	Este método retorna o valor presente no atributo podeIr.
	@return		Retorna o valor presente nesta variável boolean.
	*/

	public boolean getPodeComecar()
	{
		return this.podeIr;
	}
	
	public String toString()
	{
		if(podeIr)
			return "Começar";
		return "Não começar";
	}
	
	public boolean equals(Object obj) throws Exception
	{
		if(obj == null)
			return false;
		
		if(obj == this)
			return true;
		
		if(this.getClass() != obj.getClass())
			return false;
		ComunicadoComecar comeca = (ComunicadoComecar)obj;
		if(comeca.podeIr != this.podeIr)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int ret = 666;
		
		ret = ret * 11 + new Boolean(podeIr).hashCode();
		
		return ret;
	}
}
