/**
A classe PedidoDeNome representa uma escolha de nome feita pelo usuário.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@since 2019
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

	public String getNome()
	{
		return this.nome;
	}
}
