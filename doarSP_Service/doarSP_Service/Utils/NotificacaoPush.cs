using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;

public class NotificacaoPush
{

    private void pushNotificacao()
    {
        // o id recebido do servidor gcm
		//Palazolo's ID
        String regID = "APA91bHVzWogobpoENowv7U1M4lqrkTidbmJjyjgzLGi0FaHglqM0wFT4WPW82rcmF9UHn_Z3kPdnLvgg9z-Cs5ytda4MIKqqHFNzTsBU2xenA_HJUa1w7kH5iP0yxbx-fb-KM-Swg6BWhxusqoIFlXHyqXGqLPonw";

        // id do goolgle api
        string applicationID = "AIzaSyDU5RwfdGW8d1aFk18pXMW0rlZDUh-Z8EI";

        // id do projeto no google
        string senderID = "595477754580";

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
