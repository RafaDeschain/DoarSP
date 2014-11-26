using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;

/// <summary>
/// Summary description for User
/// </summary>
public class User
{
    UserDAO daoUser;
    public User()
    {
        daoUser = new UserDAO();
    }

    #region CamposUsuario
    public int codUsuario { get; set; }

    public int tpSanguineo { get; set; }

    public string nome { get; set; }

    public string eMail { get; set; }

    public int notificacaoPush { get; set; }

    public int notificaoEmail { get; set; }

    public int statusApto { get; set; }

    public String ultimaDoacao { get; set; }

    public String dtdNascimento { get; set; }

    public String userName { get; set; }

    public String password { get; set; }

    public String gcmId { get; set; }

    public double latitude { get; set; }

    public double longitude { get; set; }

    #endregion

    #region Métodos

    public Boolean registerNewUser()
    {
        return daoUser.inserUser(this);
    }

    public Boolean updateUser()
    {
        return daoUser.updateUser(this);
    }

    public Boolean updateLocation()
    {
        return daoUser.updateLocation(this);
    }

    public SqlDataReader getRanking()
    {
        return daoUser.getRanking();
    }

    public bool login()
    {
        return daoUser.login(this);
    }

    public String getGcm(int userId)
    {
        return daoUser.getGcm(userId);
    }

    public void sendNotPush(int idHemocentro, int idSolicitacao)
    {
        daoUser.sendNotPush(this.codUsuario, idHemocentro, idSolicitacao);
    }

    #endregion
}