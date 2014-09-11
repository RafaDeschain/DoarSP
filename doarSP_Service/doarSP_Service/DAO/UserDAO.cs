using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Collections;

/// <summary>
/// Summary description for UserDAO
/// </summary>
public class UserDAO
{
    private static String connectionString = "server=.\\SQLEXPRESS; database=doarSP; Integrated Security=SSPI";

    public UserDAO()
    {

    }

    #region public
    public void inserUser(User userData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("InsertTransaction");
            try
            {
                String cmdInsert = " insert into TB_Usuarios(USU_TpSanguineo, USU_Nome, USU_EndEmail, USU_NotificacaoPush, USU_NotificacaoEmail, " +
                                   "                         USU_StatusApto, USU_DtdUltimaDoacao, USU_DtdDataNascimento, USU_UserName, USU_Password) " +
                                   " values " +
                                   " (@tpSanguineo, @nome, @endEmail, @notPush, @notEmail, @statusApto, @dtdUltimaDoa, @dtdNasci, @userName, @password)";
                String cmdNewUser = " select MAX(USU_IdUsuario) from TB_Usuarios ";


                SqlCommand insertUser = new SqlCommand(cmdInsert, conn, transaction);
                insertUser.Parameters.AddWithValue("@tpSanguineo", userData.tpSanguineo);
                insertUser.Parameters.AddWithValue("@nome", userData.nome);
                insertUser.Parameters.AddWithValue("@endEmail", userData.eMail);
                insertUser.Parameters.AddWithValue("@notPush", userData.notificacaoPush);
                insertUser.Parameters.AddWithValue("@notEmail", userData.notificaoEmail);
                insertUser.Parameters.AddWithValue("@statusApto", userData.statusApto);
                insertUser.Parameters.AddWithValue("@dtdUltimaDoa", userData.ultimaDoacao);
                insertUser.Parameters.AddWithValue("@dtdNasci", userData.dtdNascimento);
                insertUser.Parameters.AddWithValue("@userName", userData.userName);
                insertUser.Parameters.AddWithValue("@password", userData.password);
                insertUser.ExecuteNonQuery();

                SqlCommand getNewUser = new SqlCommand(cmdNewUser, conn, transaction);
                SqlDataReader newRowUser = getNewUser.ExecuteReader();

                if (newRowUser.Read())
                {
                    userData.codUsuario = newRowUser.GetInt32(0);
                }
                newRowUser.Close();
                transaction.Commit();
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
                                    "                        USU_StatusApto = @statusApto, USU_DtdUltimaDoacao = @dtdUltimaDoa, " +
                                    "                        USU_DtdNascimento = @dtdNasci where USU_CodUsuario = @codUser ";
                SqlCommand updateUser = new SqlCommand(cmdUpdate, conn, transaction);

                updateUser.Parameters.AddWithValue("@tpSanguineo", userData.tpSanguineo);
                updateUser.Parameters.AddWithValue("@nome", userData.nome);
                updateUser.Parameters.AddWithValue("@endEmail", userData.eMail);
                updateUser.Parameters.AddWithValue("@notPush", userData.notificacaoPush);
                updateUser.Parameters.AddWithValue("@notEmail", userData.notificaoEmail);
                updateUser.Parameters.AddWithValue("@statusApto", userData.statusApto);
                updateUser.Parameters.AddWithValue("@dtdUltimaDoa", userData.ultimaDoacao);
                updateUser.Parameters.AddWithValue("@dtdNasci", userData.dtdNascimento);
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

    public SqlDataReader getRanking()
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("RankingTransaction");

            try
            {
                String cmdRanking = " Select top 10 USU_Nome, COUNT(DOC_IdDoacao) as CountDoacao " +
                                    " from TB_Usuarios " +
                                    " inner join TB_Doacoes on (USU_IdUsuario = DOC_IdUsuarioDoador) " +
                                    " where DOC_StatusDoacao = 1 " +
                                    " group by USU_Nome " +
                                    " order by CountDoacao desc ";

                SqlCommand queryRanking = new SqlCommand(cmdRanking, conn, transaction);
                SqlDataReader recordsRanking = queryRanking.ExecuteReader();
                transaction.Commit();
                return recordsRanking;
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

    public bool login(User userData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("LoginTransaction");

            try
            {
                String cmdLogin = " Select USU_IdUsuario, USU_TpSanguineo, USU_Nome, USU_EndEmail, USU_NotificacaoPush " +
                                  "        USU_NotificacaoEmail, USU_StatusApto, USU_DtdUltimaDoacao, USU_DtdDataNascimento, " +
                                  "        USU_UserName, USU_Password " +
                                  " from TB_Usuarios where " +
                                  "        USU_UserName = @userName and USU_Password = @password";

                SqlCommand queryLogin = new SqlCommand(cmdLogin, conn, transaction);
                queryLogin.Parameters.AddWithValue("@userName", userData.userName);
                queryLogin.Parameters.AddWithValue("@password", userData.password);

                SqlDataReader userRecord = queryLogin.ExecuteReader();

                if (userRecord.Read())
                {
                    userData.codUsuario = userRecord.GetInt32(0);
                    userData.tpSanguineo = userRecord.GetInt32(1);
                    userData.nome = userRecord.GetString(2);
                    userData.eMail = userRecord.GetString(3);
                    userData.notificacaoPush = userRecord.GetInt16(4);
                    userData.notificaoEmail = userRecord.GetInt16(5);
                    userData.statusApto = userRecord.GetInt16(6);
                    userData.ultimaDoacao = userRecord.GetDateTime(7);
                    userData.dtdNascimento = userRecord.GetDateTime(8);                    
                }
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
    #endregion

    #region private

    #endregion
}