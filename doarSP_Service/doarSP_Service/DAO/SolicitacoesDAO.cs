using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data.Sql;
using System.Data.Common;

/// <summary>
/// Summary description for SolicitacoesDAO
/// </summary>
public class SolicitacoesDAO
{
    private static String connectionString = "server=.\\SQLSERVER; database=doarSP; User ID=sa;Password=123";
    public SolicitacoesDAO()
    {
    }

    #region public

    public Boolean insertNewDonation(Solicitacoes donationData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("InsertDonationTransaction");
            try
            {
                String cmdInsert = " insert into TB_Solicitacoes(SOL_IdUsuarioSolicitador, SOL_QuantidadeSolicitacoes, " +
                                   "                             SOL_QuantidadeRealizadas, SOL_IdHemocentroSolicitado, " +
                                   "                             SOL_NomePaciente, SOL_TipoSanguineo, SOL_StatusSolicitacao,  " +
                                   "                             SOL_DtdAberturaSolicitacao, SOL_Comentario)  " +
                                   " values (@idSolicitador, @qtnQuantidadeSol, @qtnQuantidadeRel, @idHemoCentro, " +
                                   " @nomePaciente, @tpSanguineo, @SOL_StatusSolicitacao, @dtdAberturaSolicitacao, @comentario ) ";
                String cmdNewUser = " select MAX(SOL_IdUsuarioSolicitador) from TB_Solicitacoes ";


                SqlCommand insertDonation = new SqlCommand(cmdInsert, conn, transaction);
                insertDonation.Parameters.AddWithValue("@idSolicitador", donationData.idUserSolicitante);
                insertDonation.Parameters.AddWithValue("@qtnQuantidadeSol", donationData.qtnDoacoes);
                insertDonation.Parameters.AddWithValue("@qtnQuantidadeRel", donationData.qtnRealizadas);
                insertDonation.Parameters.AddWithValue("@idHemoCentro", donationData.hemoCentro);
                insertDonation.Parameters.AddWithValue("@nomePaciente", donationData.nomePaciente);
                insertDonation.Parameters.AddWithValue("@tpSanguineo", donationData.tpSanguineo);
                insertDonation.Parameters.AddWithValue("@SOL_StatusSolicitacao", 1);
                insertDonation.Parameters.AddWithValue("@dtdAberturaSolicitacao", DateTime.Now);
                insertDonation.Parameters.AddWithValue("@comentario", donationData.comentario);
                insertDonation.ExecuteNonQuery();

                SqlCommand getNewDonation = new SqlCommand(cmdNewUser, conn, transaction);
                SqlDataReader newRowDonation = getNewDonation.ExecuteReader();

                if (newRowDonation.Read())
                {
                    donationData.codDoacao = newRowDonation.GetInt32(0);
                }
                newRowDonation.Close();
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

    public void getSolicitacao(int userID, ref List<Solicitacoes> list)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            try
            {
                String cmdDonationRecords = " Select SOL_IdSolicitacao, SOL_IdUsuarioSolicitador, SOL_QuantidadeSolicitacoes, " +
                                            " SOL_QuantidadeRealizadas, SOL_IdHemocentroSolicitado, SOL_NomePaciente, SOL_TipoSanguineo, SOL_DtdAberturaSolicitacao, SOL_Comentario " +
                                            " From TB_Solicitacoes " +
                                            " where SOL_IdUsuarioSolicitador = @idUser";

                SqlCommand queryRecords = new SqlCommand(cmdDonationRecords, conn);

                queryRecords.Parameters.AddWithValue("@idUser", userID);

                SqlDataReader reader = queryRecords.ExecuteReader();

                int i = 0;
                foreach (DbDataRecord recordDonation in reader)
                {
                    Solicitacoes data = new Solicitacoes();

                    data.codDoacao = reader.GetInt32(0);
                    data.idUserSolicitante = reader.GetInt32(1);
                    data.qtnDoacoes = reader.GetInt32(2);
                    data.qtnRealizadas = reader.GetInt32(3);
                    data.hemoCentro = reader.GetInt32(4);
                    data.nomePaciente = reader.GetString(5);
                    data.tpSanguineo = reader.GetByte(6);
                    data.dataAbertura = reader.GetDateTime(7);
                    data.comentario = reader.GetString(8);

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

    public void getMural(int userID, ref List<Solicitacoes> list)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            try
            {
                String cmdDonationRecords = "SELECT SOL_IdSolicitacao, SOL_IdUsuarioSolicitador, " +
                                            "SOL_NomePaciente, SOL_TipoSanguineo, SOL_Comentario "+
                                            "FROM TB_Solicitacoes INNER JOIN TB_Doacoes ON SOL_IdSolicitacao = DOC_IdSolicitacao " +
                                            "WHERE DOC_IdUsuarioDoador = @idUser AND DOC_StatusDoacao = 2";

                SqlCommand queryRecords = new SqlCommand(cmdDonationRecords, conn);

                queryRecords.Parameters.AddWithValue("@idUser", userID);

                SqlDataReader reader = queryRecords.ExecuteReader();
                
                int i = 0;
                foreach (DbDataRecord recordDonation in reader)
                {
                    Solicitacoes data = new Solicitacoes();

                    data.codDoacao          = reader.GetInt32(0);
                    data.idUserSolicitante  = reader.GetInt32(1);
                    data.nomePaciente       = reader.GetString(2);
                    data.tpSanguineo        = reader.GetByte(3);

                    if (reader.GetString(4).Length > 2)
                    {
                        data.comentario = reader.GetString(4);
                    }
                    else
                    {
                        data.comentario = "O solicitante não inseriu nenhuma mensagem de agradecimento, mas a equipe do DoarSP agradece imensamente sua contribuição.";
                    }

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

    public int getUsuarioSolicitador(Solicitacoes donationData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();

            try
            {
                String cmdInsert = "SELECT SOL_IdUsuarioSolicitador FROM TB_Solicitacoes WHERE SOL_IdSolicitacao = @solId";

                SqlCommand checkusr = new SqlCommand(cmdInsert, conn);
                checkusr.Parameters.AddWithValue("@solId", donationData.codDoacao);
                checkusr.ExecuteNonQuery();

                SqlDataReader reader = checkusr.ExecuteReader();

                if (reader.Read())
                {
                    int ret = reader.GetInt32(0);
                    reader.Close();
                    return ret;
                }
                else
                {
                    reader.Close();
                    return 0;
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

    public void getSolicitacaoHemocentro(int idHemoCentro, ref List<Solicitacoes> list)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            try
            {
                String cmdDonationRecords = " Select SOL_IdSolicitacao, SOL_IdUsuarioSolicitador, SOL_QuantidadeSolicitacoes, " +
                                            " SOL_QuantidadeRealizadas, SOL_IdHemocentroSolicitado, SOL_NomePaciente, SOL_TipoSanguineo, SOL_DtdAberturaSolicitacao, SOL_Comentario " +
                                            " From TB_Solicitacoes " +
                                            " where SOL_IdHemocentroSolicitado = @idHemoCentro and SOL_StatusSolicitacao = 1";

                SqlCommand queryRecords = new SqlCommand(cmdDonationRecords, conn);

                queryRecords.Parameters.AddWithValue("@idHemoCentro", idHemoCentro);

                SqlDataReader reader = queryRecords.ExecuteReader();

                int i = 0;
                foreach (DbDataRecord recordDonation in reader)
                {
                    Solicitacoes data = new Solicitacoes();

                    data.codDoacao = reader.GetInt32(0);
                    data.idUserSolicitante = reader.GetInt32(1);
                    data.qtnDoacoes = reader.GetInt32(2);
                    data.qtnRealizadas = reader.GetInt32(3);
                    data.hemoCentro = reader.GetInt32(4);
                    data.nomePaciente = reader.GetString(5);
                    data.tpSanguineo = reader.GetByte(6);
                    data.dataAbertura = reader.GetDateTime(7);
                    data.comentario = reader.GetString(8);

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


    #endregion
    
}