import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private double              valor=0;
    private Parceiro            jogador;
    private Socket              conexao;
    private ArrayList<Parceiro> jogadores;

    public SupervisoraDeConexao
    (Socket conexao, ArrayList<Parceiro> jogadores)
    throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (jogadores==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.jogadores = jogadores;
    }

    public void run ()
    {
        ObjectInputStream receptor=null;
        try
        {
            receptor=
            new ObjectInputStream(
            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            return;
        }

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            try
            {
                receptor.close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }

        try
        {
            this.jogador =
            new Parceiro (this.conexao,
                          receptor,
                          transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.jogadores)
            {
                this.jogadores.add (this.jogador);
            }


            for(;;)
            {
                Comunicado comunicado = this.jogador.envie ();

                if      (comunicado==null)
                    return;
                else if (comunicado instanceof PedidoDeAdicao)
                {
                    this.valor += ((PedidoDeAdicao)comunicado).getValorParaAdicionar();
                }
                else if (comunicado instanceof PedidoDeSubtracao)
                {
                    this.valor -= ((PedidoDeSubtracao)comunicado).getValorParaSubtrair();
                }
                else if (comunicado instanceof PedidoDeMultiplicacao)
                {
                    this.valor *= ((PedidoDeMultiplicacao)comunicado).getValorParaMultiplicar();
                }
                else if (comunicado instanceof PedidoDeDivisao)
                {
                    this.valor /= ((PedidoDeDivisao)comunicado).getValorParaDividir();
                }
                else if (comunicado instanceof PedidoDeResultado)
                {
                    this.jogador.receba (new Resultado (this.valor));
                }
                else if (comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.jogadores.remove (this.usuario);
                    }
                    this.jogador.adeus();
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}
