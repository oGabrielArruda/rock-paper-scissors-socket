public class ComunicadoComecar extends Comunicado
{
	private boolean podeIr;
	public ComunicadoComecar(boolean pode)
	{
		this.podeIr = pode;
	}

	public boolean getPodeComecar()
	{
		return this.podeIr;
	}
}