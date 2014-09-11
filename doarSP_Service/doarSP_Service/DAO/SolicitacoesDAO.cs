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
    private static String connectionString = "server=.\\SQLEXPRESS; database=doarSP; Integrated Security=SSPI";
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
                                   "                             SOL_NomePaciente, SOL_TipoSanguineo, SOL_DtdAberturaSolicitacao,  " +
                                   "                             SOL_Comentario)  " +
                                   " values (@idSolicitador, @qtnQuantidadeSol, @qtnQuantidadeRel, @idHemoCentro, " +
                                   " @nomePaciente, @tpSanguineo, @dtdAberturaSolicitacao, @comentario ) ";
                String cmdNewUser = " select MAX(SOL_IdUsuarioSolicitador) from TB_Solicitacoes ";


                SqlCommand insertDonation = new SqlCommand(cmdInsert, conn, transaction);
                insertDonation.Parameters.AddWithValue("@idSolicitador", donationData.idUserSolicitante);
                insertDonation.Parameters.AddWithValue("@qtnQuantidadeSol", donationData.qtnDoacoes);
                insertDonation.Parameters.AddWithValue("@qtnQuantidadeRel", donationData.qtnRealizadas);
                insertDonation.Parameters.AddWithValue("@idHemoCentro", donationData.hemoCentro);
                insertDonation.Parameters.AddWithValue("@nomePaciente", donationData.nomePaciente);
                insertDonation.Parameters.AddWithValue("@tpSanguineo",  donationData.tpSanguineo);
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

    public SqlDataReader getDonationRecords(bool getAll, int idHemocentro, int userID)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("FindDonations");
            String whereClause;
            try
            {
                if (getAll)
                {
                    whereClause = " where SOL_StatusSolicitacao = 1";
                }
                else if (idHemocentro > 0)
                {
                    whereClause = " where SOL_StatusSolicitacao = 1 and SOL_IdHemocentroSolicitado = @idHemoCentro ";
                }
                else if (userID > 0)
                {
                    whereClause = " where SOL_IdUsuarioSolicitador = @idUser ";
                }
                else
                {
                    whereClause = " SOL_StatusSolicitacao = 1 ";
                }

                String cmdDonationRecords = " Select SOL_IdSolicitacao, SOL_IdUsuarioSolicitador, SOL_QuantidadeSolicitacoes " +
                                            "        SOL_QuantidadeRealizadas, SOL_IdHemocentroSolicitado, SOL_NomePaciente, SOL_TipoSanguineo " +
                                            " From TB_Solicitacoes " + 
                                            whereClause;

                SqlCommand queryRecords = new SqlCommand(cmdDonationRecords, conn, transaction);

                queryRecords.Parameters.AddWithValue("@idHemoCentro", idHemocentro);
                queryRecords.Parameters.AddWithValue("@idUser", idHemocentro);

                SqlDataReader recordsRanking = queryRecords.ExecuteReader();
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