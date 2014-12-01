using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data.Sql;
using System.Data.Common;

public class DoacoesDAO
{
    private static String connectionString = "server=.\\SQLSERVER; database=doarSP; User ID=sa;Password=123";

    #region Public
    public Boolean checkStatusApto(int idUser)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();

            try
            {
                String cmdInsert = "SELECT 1 FROM TB_Usuarios WHERE USU_IdUsuario = @idUser AND USU_StatusApto = 2";


                SqlCommand checkusr = new SqlCommand(cmdInsert, conn);
                checkusr.Parameters.AddWithValue("@idUser", idUser);
                checkusr.ExecuteNonQuery();

                SqlDataReader reader = checkusr.ExecuteReader();

                if (reader.HasRows)
                {
                    reader.Close();
                    return false;
                }
                else
                {
                    reader.Close();
                    return true;
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

    public Boolean insertUserDonation(Doacao objData)
    {
        if (checkExistDonation(objData.usuarioDoador, objData.idSolicitacao) == true)
        {
            return false;
        }

        if (checkStatusApto(objData.usuarioDoador) == false)
        {
            return false;
        }

        using (SqlConnection con = new SqlConnection(connectionString))
        {
            con.Open();
            SqlTransaction transaction = con.BeginTransaction("InsertUserDonation");
            try
            {
                String cmdInsert = " insert into TB_Doacoes(DOC_IdSolicitacao, DOC_IdUsuarioDoador, DOC_DtdDataDoacao, DOC_StatusDoacao) " +
                                   "                 values(@idSolicitacao, @idUsuarioDoador, @dtdDataDoacao, @statusDoacao) ";

                SqlCommand newUserDonation = new SqlCommand(cmdInsert, con, transaction);
                newUserDonation.Parameters.AddWithValue("@idSolicitacao", objData.idSolicitacao);
                newUserDonation.Parameters.AddWithValue("@idUsuarioDoador", objData.usuarioDoador);
                newUserDonation.Parameters.AddWithValue("@dtdDataDoacao", DateTime.Now);
                newUserDonation.Parameters.AddWithValue("@statusDoacao", 1);
                newUserDonation.ExecuteNonQuery();

                transaction.Commit();
                return true;
            }
            catch (Exception ex)
            {
                transaction.Rollback();
                throw new Exception("Erro:" + ex.ToString());
            }
            finally
            {
                con.Close();
            }

        }
    }

    public void getDoacao(int userID, ref List<Doacao> list)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            try
            {
                String cmdDonationRecords = "SELECT DOC_IdDoacao, SOL_IdSolicitacao, SOL_IdHemocentroSolicitado, SOL_NomePaciente " +
                                            "FROM TB_Doacoes INNER JOIN " + 
                                            "TB_Solicitacoes ON DOC_IdSolicitacao = SOL_IdSolicitacao WHERE "+
                                            "DOC_IdUsuarioDoador = @idUser AND DOC_StatusDoacao = 1";

                SqlCommand queryRecords = new SqlCommand(cmdDonationRecords, conn);

                queryRecords.Parameters.AddWithValue("@idUser", userID);

                SqlDataReader reader = queryRecords.ExecuteReader();

                int i = 0;
                foreach (DbDataRecord recordDonation in reader)
                {
                    Doacao data = new Doacao();

                    data.idDoacao       = reader.GetInt32(0);
                    data.idSolicitacao  = reader.GetInt32(1);
                    data.idHemocentro   = reader.GetInt32(2);
                    data.nomePaciente   = reader.GetString(3);
                    

                    list.Insert(i, data);
                    i++;
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
     
    public Boolean checkExistDonation(int iduser, int idsoli)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();

            try
            {
                String cmdInsert = "SELECT 1 FROM TB_Doacoes WHERE DOC_IdUsuarioDoador = @iduser AND DOC_IdSolicitacao = @idsoli";


                SqlCommand checkusr = new SqlCommand(cmdInsert, conn);
                checkusr.Parameters.AddWithValue("@iduser", iduser);
                checkusr.Parameters.AddWithValue("@idsoli", idsoli);
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

    public bool checkInDonation(Doacao objData)
    {
        double usu_lat  = 0.0;
        double usu_long = 0.0;
        double hem_lat  = 0.0;
        double hem_long = 0.0;

        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            //SqlTransaction transaction = conn.BeginTransaction("CheckInDonation");

            try
            {
                
                String queryUser = "SELECT USU_Lat, USU_Long FROM TB_Usuarios WHERE USU_IdUsuario = @idUser";
                SqlCommand queryUserCommand = new SqlCommand(queryUser, conn);
                queryUserCommand.Parameters.AddWithValue("@idUser", objData.usuarioDoador);
                SqlDataReader userRow = queryUserCommand.ExecuteReader();
                
                if (userRow.Read())
                {
                    usu_lat     = userRow.GetDouble(0);
                    usu_long    = userRow.GetDouble(1);
                }
                userRow.Close();
                
                String queryHemo = "SELECT HEM_Lat, HEM_Long FROM TB_Hemocentros WHERE HEM_IdHemocentro = @idHemo";
                SqlCommand queryHemoCommand = new SqlCommand(queryHemo, conn);
                queryHemoCommand.Parameters.AddWithValue("@idHemo", objData.idHemocentro);
                SqlDataReader hemoRow = queryHemoCommand.ExecuteReader();
                
                if (hemoRow.Read())
                {
                    hem_lat     = hemoRow.GetDouble(0);
                    hem_long    = hemoRow.GetDouble(1);
                }
                hemoRow.Close();
                
                calcDistance validateCheckIn = new calcDistance();
                if (validateCheckIn.calcDistances(usu_lat, usu_long, hem_lat, hem_long) < 0.5)
                {
                    String cmdUpdateSolcitacao = " update TB_Solicitacoes set SOL_QuantidadeRealizadas = ( Select SOL_QuantidadeRealizadas + 1 " +
                                   "                                                         from TB_Solicitacoes " +
                                   "                                                         where SOL_IdSolicitacao = @idSolicitacao) ";
                    SqlCommand checkInCursor = new SqlCommand(cmdUpdateSolcitacao, conn);
                    checkInCursor.Parameters.AddWithValue("@idSolicitacao", objData.idSolicitacao);
                    checkInCursor.ExecuteNonQuery();

                    String cmdUpdateDoacao = " update TB_Doacoes set DOC_StatusDoacao = 2 where DOC_IdDoacao = @idDoacao ";
                    checkInCursor.CommandText = cmdUpdateDoacao;
                    checkInCursor.Parameters.AddWithValue("@idDoacao", objData.idDoacao);
                    checkInCursor.ExecuteNonQuery();

                    String cmdUpdateUser = " update TB_Usuarios set USU_StatusApto = 2 where USU_IdUsuario = @idUser ";
                    checkInCursor.CommandText = cmdUpdateUser;
                    checkInCursor.Parameters.AddWithValue("@idUser", objData.usuarioDoador);
                    checkInCursor.ExecuteNonQuery();

                    String cmdUpdateUser2 = " update TB_Usuarios set USU_DtdUltimaDoacao = @data where USU_IdUsuario = @idUser2 ";
                    checkInCursor.CommandText = cmdUpdateUser2;
                    checkInCursor.Parameters.AddWithValue("@idUser2", objData.usuarioDoador);
                    checkInCursor.Parameters.AddWithValue("@data", DateTime.Now);
                    checkInCursor.ExecuteNonQuery();

                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch(Exception ex)
            {
               // transaction.Rollback();                
                throw new Exception("Erro " + ex.ToString());
            }
            finally
            {
                conn.Close();
            }

        }
    }
    #endregion
}
