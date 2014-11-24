﻿using System;
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
    #endregion

}