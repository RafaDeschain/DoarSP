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
    #endregion

    public Boolean insertNewDonation()
    {
        return dao.insertNewDonation(this);
    }

    public List<Solicitacoes> getDonationRecords(int userID)
    {
        return dao.getSolicitacao(userID);
    }

    public void getMural()
    {
        dao.getMural(this);
    }
}