/**
A classe PedidoDeNome representa uma escolha de nome feita pelo usuário.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@since 2019.
*/

public class PedidoDeNome extends Comunicado
{
	private String nome;

	/**
	Constroi uma nova instância da classe PedidoDeNome.
	Para tal, é necessário um parâmetro da classe String 
	que contenha o nome desejado pelo usuário.
	@param nome	Uma String com o nome desejado pelo usuário.
	*/

	public PedidoDeNome(String nome)
	{
		this.nome = nome;
	}

	/**
	O método getNome() recupera o nome do usuário.
	@return 	Retorna uma String contendo o nome desejado pelo usuário.
	*/

	public String getNome()
	{
		return this.nome;
	}
	
	public String toString()
	{
		return "Nome: " + this.nome;
	}
	
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj.getClass() != this.getClass())
			return false;
		PedidoDeNome pedido = (PedidoDeNome)obj;
		if(pedido.nome != this.nome)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		int ret = 666;
		
		ret = ret * 11 + this.nome.hashCode();
		
		return ret;
	}
}
