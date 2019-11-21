/**
A classe Jogada é responsável por armazenar a jogada de um usuário e compará-la com a de outro.
@author Antônio Hideto Borges Kotsubo & Gabriel Alves Arruda & Matheus Seiji Luna Noda & Nouani Gabriel Sanches.
@since 2019.
*/

public class Jogada extends Comunicado
{
	private String valor;
	
	/** 
	Constroi uma nova instância da classe Jogada.
	Esse método pede um parâmetro que corresponde ao valor da jogada do usuário.
	@param  valor		Esta string armazena a jogada do usuário.
	@throws Exception	Caso a jogada seja igual a cadeia vazia.
	*/
	
	public Jogada(String valor) throws Exception
	{
		if(valor == null)
			throw new Exception("Valor para jogada inválida");
		this.valor = valor;
	}

	/**
	Este método compara os valores das jogadas dos usuários.
	Para tal, é necessário que se tenha dois valores, um do objeto que chama o método
	e outro do objeto passado como parâmetro.
	@param outra	É um objeto da classe jogada.
	@see		String#equals().
	@return		Um inteiro que expressa a situação da jogada: quem ganhou ou se deu empate.
	*/

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
