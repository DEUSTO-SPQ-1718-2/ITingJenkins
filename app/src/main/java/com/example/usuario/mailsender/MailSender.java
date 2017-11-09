package com.example.usuario.mailsender;

import android.os.AsyncTask;
import android.widget.Toast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by axi_e on 15/10/2017.
 */

public class MailSender extends AsyncTask<String, Integer, String> {

    private String nombreEmisor;
    private String nombreReceptor;
    private String restaurante;
    private String codReserva;
    Logger log = LoggerFactory.getLogger(MailSender.class);

    public MailSender(String nombreEmisor, String nombreReceptor, String restaurante, String codReserva){
        this.nombreEmisor = nombreEmisor;
        this.nombreReceptor = nombreReceptor;
        this.restaurante = restaurante;
        this.codReserva = codReserva;
    }

    @Override
    protected String doInBackground(String... strings) {
        envio_mail(nombreEmisor, nombreReceptor, restaurante, codReserva);
        return "Enviado";
    }

    public void envio_mail(String nombreEmisor, String nombreReceptor, String restaurante, String codReserva){

        try{

            GMailSender sender = new GMailSender("noreply.iting@gmail.com", "ItingPrueba14");

            /*String body = "<div><img src=\"cid:image\"></div>" +
                    "<div><h1><strong>&iexcl;Hola, nombre eCamarero!</strong></h1>\n" +
                    "<p><span style=\"font-size:16px;\">nombre emisor te ha asignado como eCamarero en el restaurante x.</span></p>\n" +
                    "<p><span style=\"font-size:16px;\">El <strong>c&oacute;digo de reserva</strong> es el siguiente: <strong>c&oacute;digo de reserva</strong></span></p>\n" +
                    "<p><span style=\"font-size:16px;\">&iexcl;Corre a confirmar tu asistencia a la app de Iting!</span></p>\n" +
                    "<p><span style=\"font-size:16px;\">Atentamente,</span></p>\n" +
                    "<p><span style=\"font-size:16px;\">---------</span></p>\n" +
                    "<p><em><span style=\"font-size:16px;\">Equipo de Iting</span></em></p></div>";*/

            mail_template a = new mail_template(nombreEmisor, nombreReceptor, restaurante, codReserva);
            String body = a.setEnvio();

            sender.sendMail("[Iting] " + nombreEmisor +   " te ha invitado a comer", body, "noreply.iting@gmail.com", nombreReceptor);

        } catch (Exception e){
            log.warn("Es el primero");
            //System.out.println("Es el primero");
            //Toast.makeText((), "Cuenta de correo electronico incorrecta", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        log.debug("Onpost Execute de Mail Sender.");
        log.debug(s);
        //System.out.println("¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿");
        //System.out.println(s);
        //super.onPostExecute(s);
    }
}
