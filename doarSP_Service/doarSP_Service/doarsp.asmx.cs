using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;
using System.Web.Script.Serialization;
using System.Data.SqlClient;
using System.Threading;

namespace doarSP_Service
{
    /// <summary>
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "http://doarsp.ws/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class doarsp : System.Web.Services.WebService
    {
        #region Insere_Usuario
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String usuario_insereNovoUsuario(int tpSanguineo, String nome, String eMail, int notificacaoPush, int notificacaoEmail,
                                 int statusApto, String dtdNascimento, String username, String password, String gcmId)
        {
            User userData = new User();            

            userData.tpSanguineo = tpSanguineo;
            userData.nome = nome;
            userData.eMail = eMail;
            userData.notificacaoPush = notificacaoPush;
            userData.notificaoEmail = notificacaoEmail;
            userData.statusApto = statusApto;            
            userData.dtdNascimento = dtdNascimento;
            userData.userName = username;
            userData.password = password;
            userData.gcmId = gcmId;
            
            /*List<User> jsonUser = new List<User>();
            jsonUser.Insert(0, userData);

            JavaScriptSerializer jsonClient = new JavaScriptSerializer();
            return jsonClient.Serialize(jsonUser);
            List<Boolean> json = new List<Boolean>();
            json.Insert(0, userData.registerNewUser());
            JavaScriptSerializer jsonClient = new JavaScriptSerializer();
            return jsonClient.Serialize(json);*/
            if (userData.registerNewUser())
            {
                return "true";
            }
            else
            {
                return "false";
            }
        }
        #endregion

        #region Atualiza_Usuario
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public Boolean usuario_AtualizaUsuario(int CodUser, int tpSanguineo, String nome, String eMail, int notificacaoPush, int notificacaoEmail,
                                                String dtdNascimento, String password)
        {
            User userData = new User();
            userData.codUsuario = CodUser;
            userData.tpSanguineo = tpSanguineo;
            userData.nome = nome;
            userData.eMail = eMail;
            userData.notificacaoPush = notificacaoPush;
            userData.notificaoEmail = notificacaoEmail;
            userData.dtdNascimento = dtdNascimento;
            userData.password = password;

            if (userData.updateUser())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        #endregion

        #region Atualiza_Localizacao
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public Boolean usuario_AtualizaLocalizacao(int CodUser, double latitude, double longitude)
        {
            User userData = new User();
            userData.codUsuario = CodUser;
            userData.latitude   = latitude;
            userData.longitude  = longitude;

            if (userData.updateLocation())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        #endregion

        #region Informacoes_Ranking
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String usuario_GetRanking()
        {
            User userData = new User();

            List<Ranking> recordsRanking = new List<Ranking>();
            SqlDataReader records = userData.getRanking();
            Ranking data = new Ranking();
            int Count = 0;
            while (records.Read())
            {
                data.nome = records.GetString(0);
                data.numDoacoes = records.GetInt32(1);
                recordsRanking.Insert(Count, data);
                Count++;
            }
            JavaScriptSerializer jsonClient = new JavaScriptSerializer();
            return jsonClient.Serialize(recordsRanking);
        }
        #endregion

        #region Insere_Solicitacao
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String solicitacao_InserirNovaSolicitacao(int userId, int qtnNecessaria, int idHemoCentro, int tpSanguineo, String pacienteNome, String comentario)
        {
            Solicitacoes novaDoacao = new Solicitacoes();
            NotificacaoPush push = new NotificacaoPush();
            novaDoacao.idUserSolicitante = userId;
            novaDoacao.qtnDoacoes = qtnNecessaria;
            novaDoacao.qtnRealizadas = 0;
            novaDoacao.hemoCentro = idHemoCentro;
            novaDoacao.tpSanguineo = tpSanguineo;
            novaDoacao.nomePaciente = pacienteNome;
            novaDoacao.comentario = comentario;

            if (novaDoacao.insertNewDonation())
            {
                push.pushNotificacao(userId, "Uma nova solicitação foi aberta");
                return "true";
            }
            else
            {
                return "false";
            }
        }
        #endregion

        #region Send_PushAndEmail
        public void Notificacoes_EnviarNotificacoes(int idSolicitacao)
        {
            SendEmail sender = new SendEmail();
            sender.fillUsersForPushAndEmail(idSolicitacao);
        }

        #endregion

        #region Insere_Nova_Doacao
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String doacao_InserirNovaDoacao(int userId, int idDonation)
        {
            Doacao doacao = new Doacao();
            doacao.idSolicitacao = idDonation;
            doacao.usuarioDoador = userId;

            List<Boolean> json = new List<Boolean>();
            if(doacao.insertDoacao()){
                json.Insert(0, true);
                NotificacaoPush push = new NotificacaoPush();
            }
            else
            {
                json.Insert(0, false);
            }

            JavaScriptSerializer jsonClient = new JavaScriptSerializer();
            return jsonClient.Serialize(json);
        }
        #endregion

        #region CheckInDoacao
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String doacao_CheckInDoacao(int userId, int idDoacao, int idSolicitacao, double latitude, double longitude, double latitudeHemo, double longitudeHemo)
        {
            Doacao doacao = new Doacao();
            doacao.idDoacao = idDoacao;
            doacao.usuarioDoador = userId;
            doacao.idSolicitacao = idSolicitacao;
            List<Boolean> json = new List<Boolean>();
            JavaScriptSerializer jsonClient = new JavaScriptSerializer();


            calcDistance validateCheckIn = new calcDistance();
            if (validateCheckIn.calcDistances(latitude, longitude, latitudeHemo, longitudeHemo) == 0)
            {
                if (doacao.insertDoacao())
                {
                    // rotina de push e email
                    json.Insert(0, true);
                }
                else
                {
                    json.Insert(0, false);
                }
            }
            else
            {
                json.Insert(0, false);
            }
            return jsonClient.Serialize(json);
        }
        #endregion

        #region Informacoes_Doacao
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String solicitacoes_GetSolicitacoes(int userID)
        {
            Solicitacoes sol = new Solicitacoes();
            List<Solicitacoes> recordsDonation = new List<Solicitacoes>();

            recordsDonation = sol.getDonationRecords(userID);
            
            JavaScriptSerializer jsonClient = new JavaScriptSerializer();
            return jsonClient.Serialize(recordsDonation);
        }
        #endregion

        #region Login
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String usuario_Login(String username, String password)
        {            
            try
            {
                
                User userLogin = new User();
                userLogin.userName = username;
                userLogin.password = password;

                if (userLogin.login())
                {
                    List<User> jsonUser = new List<User>();
                    jsonUser.Insert(0, userLogin);

                    JavaScriptSerializer jsonClient = new JavaScriptSerializer();
                    return jsonClient.Serialize(jsonUser);
                }
                else
                {
                    return "false";
                }
            }
            catch (Exception ex)
            {
                throw new Exception("" + ex.ToString() + ex.StackTrace);
            }
        }
        #endregion

        #region Mural
        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod]
        public String mural_PegarComentario(int idSolicitacao)
        {
            Solicitacoes solicitacaoMural = new Solicitacoes();
            solicitacaoMural.codDoacao = idSolicitacao;
            solicitacaoMural.getMural();
            
            List<String> jsonMural = new List<String>();
            jsonMural.Insert(0, solicitacaoMural.comentario);

            JavaScriptSerializer jsonClient = new JavaScriptSerializer();
            return jsonClient.Serialize(jsonMural);            
        }
        #endregion
    }
}