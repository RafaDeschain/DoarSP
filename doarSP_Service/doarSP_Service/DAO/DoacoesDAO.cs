using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data.Sql;


public class DoacoesDAO
{
    private static String connectionString = "server=.\\SQLEXPRESS; database=doarSP; User ID=sa;Password=123";

    #region Public
    public Boolean insertUserDonation(Doacao objData)
    {
        using (SqlConnection con = new SqlConnection(connectionString))
        {
            con.Open();
            SqlTransaction transaction = con.BeginTransaction("InsertUserDonation");
            try
            {
                String cmdInsert = " insert into TB_Doacoes(DOC_IdSolicitacao, DOC_IdUsuarioDoador, DOC_DtdDataDoacao, DOC_StatusDoacao) " +
                                   "                 values(@idSolicitacao, @idUsuarioDoador, @dtdDataDoacao, @statusDoacao) ";

                SqlCommand newUserDonation = new SqlCommand(cmdInsert, con);
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

    public bool checkInDonation(Doacao objData)
    {
        using (SqlConnection conn = new SqlConnection(connectionString))
        {
            conn.Open();
            SqlTransaction transaction = conn.BeginTransaction("CheckInDonation");

            try
            {
                String cmdUpdateSolcitacao = " update TB_Solicitacoes set SOL_QuantidadeRealizadas = ( Select SOL_QuantidadeRealizadas + 1 " +
                                   "                                                         from TB_Solicitacoes " +
                                   "                                                         where SOL_IdSolicitacao = @idSolicitacao) ";
                SqlCommand checkInCursor = new SqlCommand(cmdUpdateSolcitacao, conn);
                checkInCursor.Parameters.AddWithValue("@idSolicitacao", objData.idSolicitacao);
                checkInCursor.ExecuteNonQuery();

                String cmdUpdateDoacao = " update TB_Doacoes set DOC_StatusDoacao = 3 where DOC_IdDoacao = @idDoacao ";
                checkInCursor.CommandText = cmdUpdateDoacao;
                checkInCursor.Parameters.AddWithValue("@idDoacao", objData.idDoacao);
                checkInCursor.ExecuteNonQuery();

                transaction.Commit();
                return true;
            }
            catch(Exception ex)
            {
                transaction.Rollback();                
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
