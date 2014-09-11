using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Net.Mail;
using System.Net.Mime;

/// <summary>
/// Summary description for SendEmail
/// </summary>
public class SendEmail
{
    public SendEmail()
    {

    }

    #region Público

    public void fillUsersForPushAndEmail(int idSolicitacao)
    {

    }

    public Boolean sendEmails(SqlDataReader destinatarios, SqlDataReader donation)
    {
        MailMessage eMail = new MailMessage();
        SmtpClient eMailClient = new SmtpClient();
        DateTime date = new DateTime();
        try
        {
            // Parte do servidor.
            eMailClient.Credentials = new System.Net.NetworkCredential("contatodoarsp@gmail.com", "USJT@2014"); // Verificando se login é válido
            eMailClient.Port = 587; // Porta utilizada pela gmail.
            eMailClient.Host = "smtp.gmail.com"; // Definição do Provedor que vai disparar os emails
            eMailClient.EnableSsl = true; // Provedor gmail utiliza Server Secured Layer

            // Criação do Email
            eMail.From = new MailAddress("contatodoarsp@gmail.com", "DoarSP", System.Text.Encoding.UTF8); // Adiciona informações sobre o remetente            
            eMail.SubjectEncoding = System.Text.Encoding.UTF8; // Setando o encoding dos caracteres
            eMail.IsBodyHtml = true; // Habilitando o html no e-mail            
            eMail.Priority = MailPriority.High; // Setando a prioridade do e-mail como alta.            


            String body = System.IO.File.ReadAllText(@"D:\doarSP\doarSP_Service\doarSP_Service\Utils\htmlContent.htm");
            body = body.Replace("@nome", destinatarios.GetString(0)).Replace("@qtnBolsas", Convert.ToString(donation.GetInt32(1))).
                Replace("@tipo", donation.GetString(2)).Replace("@hemoCentro", donation.GetString(3)).Replace("@nomePaciente", donation.GetString(0)).
                Replace("@idSolicitacao", Convert.ToString(donation.GetInt32(0))).Replace("@ano", Convert.ToString(date.Year));
            try
            {
                eMail.Subject = "Nova doação"; // Adicionando o assunto ao e-mail
                eMail.Body = body.Replace("@nome", destinatarios.GetString(0)).Replace("@qtnBolsas", Convert.ToString(donation.GetInt32(1)));
                eMail.To.Add(destinatarios.GetString(0));
                eMailClient.Send(eMail);
                eMail.To.Clear();
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }


            return true;

        }
        catch (Exception ex)
        {
            throw new Exception(ex.ToString());
        }
    }

    #endregion
}