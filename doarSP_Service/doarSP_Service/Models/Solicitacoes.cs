using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;

/// <summary>
/// Summary description for Hemocentros
/// </summary>
public class Solicitacoes
{
    SolicitacoesDAO dao;
    public Solicitacoes()
    {
        dao = new SolicitacoesDAO();
    }

    #region CamposSolicitacao
    public int codDoacao { get; set; }
    public int idUserSolicitante { get; set; }
    public int qtnDoacoes { get; set; }
    public int qtnRealizadas { get; set; }
    public int hemoCentro { get; set; }
    public int hemoCentroSobre { get; set; }
    public int tpSanguineo { get; set; }
    public String nomePaciente { get; set; }
    public String comentario { get; set; }
    public DateTime dataAbertura { get; set; }
    #endregion

    public Boolean insertNewDonation()
    {
        return dao.insertNewDonation(this);
    }

    public void getDonationRecords(int userID, ref List<Solicitacoes> list)
    {
        dao.getSolicitacao(userID, ref list);
    }

    public void getMural()
    {
        dao.getMural(this);
    }

    public int getUsuarioSolicitador()
    {
        return dao.getUsuarioSolicitador(this);
    }
}