using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

public class Doacao
{
    DoacoesDAO dao;
    public Doacao()
    {
        dao = new DoacoesDAO();
    }
    #region Campos
    public int idDoacao { get; set; }
    public int idSolicitacao { get; set; }
    public int usuarioDoador { get; set; }
    public DateTime dataDoacao { get; set; }
    public int statusDoacao { get; set; }
    #endregion

    #region Operacoes
    public Boolean insertDoacao()
    {
        return dao.insertUserDonation(this);
    }

    public Boolean checkInDonation()
    {
        return dao.checkInDonation(this);
    }
    #endregion
}   
