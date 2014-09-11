using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

public class calcDistance
{
    // Esse método calcula a distância entre dois pontos e retorna em forma de Kms
    // lat1, lon1 = Do hemocentro escolhido para solicitação
    // lat2, lon2 = Do aparelho do usuario que é um possível doador.

    public double calcDistances(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        // Calcula a distância que vai ser trabalhada mais para frente.
        double dist = Math.Sin(deg2rad(lat1)) * Math.Sin(deg2rad(lat2)) + Math.Cos(deg2rad(lat1)) * Math.Cos(deg2rad(lat2)) * Math.Cos(deg2rad(theta));
        dist = Math.Acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        // Transforma os valores em quilometragem
        dist = dist * 1.609344;

        return (dist);
    }

    // Aqui retornamos em forma de radianos para o cálculo
    private double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    // Aqui desconvertemos os radianos para calcular a distancia em kms
    private double rad2deg(double rad)
    {
        return (rad / Math.PI * 180.0);
    }
}
