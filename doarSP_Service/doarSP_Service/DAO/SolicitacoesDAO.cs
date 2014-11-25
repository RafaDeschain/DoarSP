using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data.Sql;

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

    public List<Solicitacoes> getSolicitacao(int userID)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            try
            {
                String cmdDonationRecords = " Select SOL_IdSolicitacao, SOL_IdUsuarioSolicitador, SOL_QuantidadeSolicitacoes " +
                                            " SOL_QuantidadeRealizadas, SOL_IdHemocentroSolicitado, SOL_NomePaciente, SOL_TipoSanguineo, SOL_DtdAberturaSolicitacao, SOL_Comentario " +
                                            " From TB_Solicitacoes " +
                                            " where SOL_IdUsuarioSolicitador = @idUser";

                SqlCommand queryRecords = new SqlCommand(cmdDonationRecords, conn);

                queryRecords.Parameters.AddWithValue("@idUser", userID);

                SqlDataReader reader = queryRecords.ExecuteReader();
                List<Solicitacoes> recordsDonation = new List<Solicitacoes>();

                if (reader.HasRows)
                {
                    int i = 0;
                    while (reader.Read())
                    {
                        Solicitacoes data = new Solicitacoes();
                        
                        data.codDoacao          = reader.GetInt32(0);
                        data.idUserSolicitante  = reader.GetInt32(1);
                        data.qtnRealizadas      = reader.GetInt32(2);
                        data.hemoCentro         = reader.GetInt32(3);
                        data.nomePaciente       = reader.GetString(4);
                        data.tpSanguineo        = reader.GetByte(5);
                        data.dataAbertura       = reader.GetDateTime(6);
                        data.comentario         = reader.GetString(7);
                        
                        recordsDonation.Insert(i, data);
                        i++;
                        reader.NextResult();
                    }
                    reader.Close();
                }

                return recordsDonation;
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

    public void getMural(Solicitacoes donationData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("FindDonations");
            try
            {
                String cmdMural = " Select SOL_Comentario " +
                                            " From TB_Solicitacoes " +
                                            " where SOL_IdSolicitacao = @idSolicitacao ";
                SqlCommand queryComentarios = new SqlCommand(cmdMural, conn, transaction);
                queryComentarios.Parameters.AddWithValue("@idSolicitacao", donationData.codDoacao);

                SqlDataReader comentarioReader = queryComentarios.ExecuteReader();

                if (comentarioReader.Read() && String.IsNullOrEmpty(comentarioReader.GetString(0)))
                {
                  donationData.comentario = comentarioReader.GetString(0);
                }
                else
                {
                    donationData.comentario = "Nenhum comentário registrado para exibição";
                }
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

    #endregion
    
}