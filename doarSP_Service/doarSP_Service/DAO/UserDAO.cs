using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Collections;
using System.Data.Common;

/// <summary>
/// Summary description for UserDAO
/// </summary>
public class UserDAO
{
    private static String connectionString = "server=.\\SQLSERVER; database=doarSP; User ID=sa;Password=123";

    public UserDAO()
    {

    }

    #region public
    public Boolean inserUser(User userData)
    {
        if (checkUserName(userData.userName) == true)
        {
            return false;
        }
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            
            try
            {
                String cmdInsert = " insert into TB_Usuarios(USU_TpSanguineo, USU_Nome, USU_EndEmail, USU_NotificacaoPush, USU_NotificacaoEmail, " +
                                   "                         USU_StatusApto, USU_DtdNascimento, USU_UserName, USU_Password, USU_GcmId) " +
                                   " values " +
                                   " (@tpSanguineo, @nome, @endEmail, @notPush, @notEmail, @statusApto, @dtdNasci, @userName, @password, @gcmId)";
                
                String cmdNewUser = " select MAX(USU_IdUsuario) from TB_Usuarios ";

                SqlCommand insertUser = new SqlCommand(cmdInsert, conn);
                insertUser.Parameters.AddWithValue("@tpSanguineo", userData.tpSanguineo);
                insertUser.Parameters.AddWithValue("@nome", userData.nome);
                insertUser.Parameters.AddWithValue("@endEmail", userData.eMail);
                insertUser.Parameters.AddWithValue("@notPush", userData.notificacaoPush);
                insertUser.Parameters.AddWithValue("@notEmail", userData.notificaoEmail);
                insertUser.Parameters.AddWithValue("@statusApto", userData.statusApto);                
                insertUser.Parameters.AddWithValue("@dtdNasci", userData.dtdNascimento);
                insertUser.Parameters.AddWithValue("@userName", userData.userName);
                insertUser.Parameters.AddWithValue("@password", userData.password);
                insertUser.Parameters.AddWithValue("@gcmId", userData.gcmId);
                insertUser.ExecuteNonQuery();

                SqlCommand getNewUser = new SqlCommand(cmdNewUser, conn);
                SqlDataReader newRowUser = getNewUser.ExecuteReader();

                if (newRowUser.Read())
                {
                    userData.codUsuario = newRowUser.GetInt32(0);
                }
                newRowUser.Close();                
                return true;
            }
            catch (Exception ex)
            {                
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }

    }

    public Boolean checkUserName(String username)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();

            try
            {
                String cmdInsert = "SELECT 1 FROM TB_Usuarios WHERE USU_UserName = @userName";


                SqlCommand checkusr = new SqlCommand(cmdInsert, conn);
                checkusr.Parameters.AddWithValue("@userName", username);
                checkusr.ExecuteNonQuery();

                SqlDataReader reader = checkusr.ExecuteReader();

                if (reader.HasRows)
                {
                    reader.Close();
                    return true;
                }
                else
                {
                    reader.Close();
                    return false;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    public Boolean updateUser(User userData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("UpdateTransaction");
            try
            {
                String cmdUpdate = " update TB_Usuarios set USU_TpSanguineo = @tpSanguineo, USU_Nome = @nome, USU_EndEmail = @endEmail,  " +
                                    "                        USU_NotificacaoPush = @notPush, USU_NotificacaoEmail = @notEmail, " +
                                    "                        USU_Password = @password, " +
                                    "                        USU_DtdNascimento = @dtdNasci where USU_IdUsuario = @codUser ";
                SqlCommand updateUser = new SqlCommand(cmdUpdate, conn, transaction);

                updateUser.Parameters.AddWithValue("@tpSanguineo", userData.tpSanguineo);
                updateUser.Parameters.AddWithValue("@nome", userData.nome);
                updateUser.Parameters.AddWithValue("@endEmail", userData.eMail);
                updateUser.Parameters.AddWithValue("@notPush", userData.notificacaoPush);
                updateUser.Parameters.AddWithValue("@notEmail", userData.notificaoEmail);
                updateUser.Parameters.AddWithValue("@dtdNasci", userData.dtdNascimento);
                updateUser.Parameters.AddWithValue("@password", userData.password);
                updateUser.Parameters.AddWithValue("@codUser", userData.codUsuario);
                updateUser.ExecuteNonQuery();

                transaction.Commit();
                return true;
            }
            catch (Exception ex)
            {
                transaction.Rollback();
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    public Boolean updateLocation(User userData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("UpdateTransaction");
            try
            {
                String cmdUpdate = " update TB_Usuarios set USU_Lat = @lat, USU_Long = @long where USU_IdUsuario = @codUser ";
                SqlCommand updateUser = new SqlCommand(cmdUpdate, conn, transaction);

                updateUser.Parameters.AddWithValue("@lat", userData.latitude);
                updateUser.Parameters.AddWithValue("@long", userData.longitude);
                updateUser.Parameters.AddWithValue("@codUser", userData.codUsuario);
                updateUser.ExecuteNonQuery();

                transaction.Commit();

                return true;
            }
            catch (Exception ex)
            {
                transaction.Rollback();
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    public String getGcm(int userId)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();

            try
            {
                String cmdInsert = "SELECT USU_GcmId FROM TB_Usuarios WHERE USU_IdUsuario = @userId";

                SqlCommand checkusr = new SqlCommand(cmdInsert, conn);
                checkusr.Parameters.AddWithValue("@userId", userId);
                checkusr.ExecuteNonQuery();

                SqlDataReader reader = checkusr.ExecuteReader();

                if (reader.Read())
                {
                    String ret = reader.GetString(0);
                    reader.Close();
                    return ret;
                }
                else
                {
                    reader.Close();
                    return "";
                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    public List<Ranking> getRanking()
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            //SqlTransaction transaction = conn.BeginTransaction("RankingTransaction");

            try
            {
                String cmdRanking = " Select top 10 USU_Nome, COUNT(DOC_IdDoacao) as CountDoacao " +
                                    " from TB_Usuarios " +
                                    " inner join TB_Doacoes on (USU_IdUsuario = DOC_IdUsuarioDoador) " +
                                    " where DOC_StatusDoacao = 2 " +
                                    " group by USU_Nome " +
                                    " order by CountDoacao desc ";

                SqlCommand queryRanking = new SqlCommand(cmdRanking, conn);
                
                List<Ranking> ranking = new List<Ranking>();
                
                SqlDataReader recordsRanking = queryRanking.ExecuteReader();
                Ranking data = new Ranking();
                int Count = 0;
                
                while (recordsRanking.Read())
                {
                    data.nome = recordsRanking.GetString(0);
                    data.numDoacoes = recordsRanking.GetInt32(1);
                    ranking.Insert(Count, data);
                    Count++;
                }
                //transaction.Commit();
                return ranking;
            }
            catch (Exception ex)
            {
                //transaction.Rollback();
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    public bool login(User userData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();            

            try
            {
                String cmdLogin = " Select USU_IdUsuario, USU_TpSanguineo, USU_Nome, USU_EndEmail, USU_NotificacaoPush, " +
                                  "        USU_NotificacaoEmail, USU_StatusApto, USU_DtdUltimaDoacao, USU_DtdNascimento, " +
                                  "        USU_UserName, USU_Password " +
                                  " from TB_Usuarios where " +
                                  "        USU_UserName = @userName and USU_Password = @password";

                SqlCommand queryLogin = new SqlCommand(cmdLogin, conn);
                queryLogin.Parameters.AddWithValue("@userName", userData.userName);
                queryLogin.Parameters.AddWithValue("@password", userData.password);

                SqlDataReader userRecord = queryLogin.ExecuteReader();

                if (userRecord.Read())
                {
                    if (userRecord.GetInt32(0) > 0)
                    {
                        userData.codUsuario = userRecord.GetInt32(0);
                        userData.tpSanguineo = userRecord.GetInt32(1);
                        userData.nome = userRecord.GetString(2);
                        userData.eMail = userRecord.GetString(3);
                        userData.notificacaoPush = userRecord.GetInt32(4);
                        userData.notificaoEmail = userRecord.GetInt32(5);
                        userData.statusApto = userRecord.GetInt32(6);
                        //userData.ultimaDoacao = userRecord.GetString(7);
                        userData.dtdNascimento = userRecord.GetString(8);
                        return true;
                    }
                    else
                        return false;
                }
                else
                {
                    return false;
                }
            }
            catch (Exception ex)
            {                
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    public void sendNotPush(int userId, int idHemocentro, int idSolicitacao)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();

            try
            {
                String cmdInsert = "SELECT USU_IdUsuario, USU_Lat, USU_Long FROM TB_Usuarios WHERE USU_IdUsuario <> @userId and USU_StatusApto = 1 and USU_NotificacaoPush = 1 and USU_Long > 0 and USU_Lat > 0";
                String cmdHemo = "Select HEM_Lat, HEM_Long from TB_Hemocentros where HEM_IdHemocentro = @idHemo";

                SqlCommand getHemoData = new SqlCommand(cmdHemo, conn);
                getHemoData.Parameters.AddWithValue("@idHemo", idHemocentro);                

                SqlDataReader hemoData = getHemoData.ExecuteReader();

                double hemoLat = 0;
                double hemoLog = 0;
                if (hemoData.Read())
                {
                    hemoLat = hemoData.GetDouble(0);
                    hemoLog = hemoData.GetDouble(1);
                }
                hemoData.Close();

                SqlCommand checkusr = new SqlCommand(cmdInsert, conn);
                checkusr.Parameters.AddWithValue("@userId", userId);                

                SqlDataReader reader = checkusr.ExecuteReader();

                calcDistance calc = new calcDistance();
                NotificacaoPush push = new NotificacaoPush();

                foreach (DbDataRecord recordUser in reader)
                {
                    if (calc.calcDistances(hemoLat, hemoLog, recordUser.GetDouble(1), recordUser.GetDouble(2)) <= 10)
                    {
                        push.pushNotificacao(recordUser.GetInt32(0), "A Solicitação de número " + idSolicitacao + " foi aberta próximo a você.");
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
            finally
            {
                conn.Close();
            }
        }
    }

    #endregion

}