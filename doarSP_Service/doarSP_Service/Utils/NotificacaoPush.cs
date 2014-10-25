using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;

public class NotificacaoPush
{

    private void pushNOtificacao()
    {
        // o id recebido do servidor gcm
        String regID = "";

        // id do goolgle api
        string applicationID = "";

        // id do projeto no google
        string senderID = "";

        // mensagem que será enviada.
        string msg = "Teste do crazy life" ;    
      
        WebRequest tRequest = WebRequest.Create("https://android.googleapis.com/gcm/send");           

        tRequest.Method = "post";                                                                                                    

        tRequest.ContentType = " application/x-www-form-urlencoded;charset=UTF-8";                             

        tRequest.Headers.Add(string.Format("Authorization: key={0}", applicationID));                              

        tRequest.Headers.Add(string.Format("Sender: id={0}", senderID));

        // post para o servidor

        string postData =
             "collapse_key=score_update&time_to_live=108&delay_while_idle=1&data.message="
              + msg + "&data.time=" + System.DateTime.Now.ToString() + "&registration_id=" +
                 regID + "";


        Byte[] byteArray = Encoding.UTF8.GetBytes(postData);

        tRequest.ContentLength = byteArray.Length;

        Stream dataStream = tRequest.GetRequestStream();

        dataStream.Write(byteArray, 0, byteArray.Length);

        dataStream.Close();

        WebResponse tResponse = tRequest.GetResponse();

        dataStream = tResponse.GetResponseStream();

        StreamReader tReader = new StreamReader(dataStream);

        // resposta do servidor
        String sResponseFromServer = tReader.ReadToEnd();   

    
        tReader.Close();

        dataStream.Close();
        tResponse.Close();                                                                                                              
    

    }
}
