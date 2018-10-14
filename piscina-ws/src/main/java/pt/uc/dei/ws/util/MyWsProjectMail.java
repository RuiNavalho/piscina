package pt.uc.dei.ws.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MyWsProjectMail {

	@Resource(name = "java:jboss/mail/gmail")
	private Session session;
	
	
	
	private final String ALERT_EMAIL="proj.management.2017@gmail.com";

	//TODO testar o envio assincrone do EMAIL
	@Asynchronous 
	public void sendDataServerDownAlert() {
		String conteudo="<table bgcolor='#E9E9E9' border='0' cellpadding='0' cellspacing='0' width='100%'><tbody> <tr> <td align='center'> <table border='0' cellpadding='0' cellspacing='0' width='650'> <tbody> <tr> <td align='center' bgcolor='#FFFFFF' width='782'> <div id='newsletterBox' style='padding:15px; width:100%; background:#FFF;'> <table bgcolor='#FFFFFF' cellpadding='0' cellspacing='0' height='100%' style='font-family:Arial,Tahoma,serif;' width='550'> <tbody> <tr> <td bgcolor='#FFF' id='newsletter_footer6' style='background:#FFF;color:#999 !important;'> <div style='background:#FFF;'> <table border='0' cellpadding='0' cellspacing='0' width='100%'> <tbody> <tr> <td valign='top' bgcolor='#EFEFEF'><img alt='header' border='0' id='logo' name='fheader' src='http://cloudgirl.pt/wp-content/uploads/logo_blueroots.png' style='display:block' /></td> </tr> </tbody> </table> </div> </td> </tr> <tr> <td>&nbsp;</td> </tr> <tr> <td> <table width='100%' border='0' cellspacing='5' cellpadding='5'> <tbody> <tr> <td scope='col'><div style='background:#FFF;color:#999 !important; font-size:12px'>Dataserver is down - RED ALERT <br />  <a href='http://localhost:8080/aclgomes-rnavalho-project-ws/' style='background: #777; color: white; text-decoration: none'>&nbsp;CLIQUE AQUI&nbsp;</a> <p>Atenciosamente,<br /> A Equipa Blue Roots.</p></div></td> </tr> </tbody> </table> </td> </tr> <tr> <td>&nbsp;</td> </tr> <tr> <td id='newsletter_footer2' style='background:#E9E9E9; height:70px;'> <table border='0' cellpadding='0' cellspacing='0' width='100%'> <tbody> <tr> <td align='center' scope='col' valign='bottom'>&nbsp;</td> <td scope='col' valign='bottom'>&nbsp;</td> </tr> <tr> <td align='center' scope='col' valign='bottom' width='4%'>&nbsp;</td> <td scope='col' style='color:#999; font-size:11px;' valign='bottom' width='96%'> <div style='color:#999 !important; line-height:110%; font-size:11px;'><span style='color:#999; font-size:11px; font-weight:bold; text-transform:uppercase'>Blue Roots</span><span style='color:#999; line-height:110%; font-size:11px;'><br /> <strong>M: </strong><span style='color:#999; line-height:110%; font-size:11px;'>930 000 000</span><br /> <strong>Email:</strong> <span style='color:#999; line-height:110%; font-size:11px;'>proj.management.2017@gmail.com</span><br /> <strong>Website:</strong></span> <span style='color:#999; line-height:110%; font-size:11px;'>www.blueroots.pt</span></div> </td> </tr> <tr> <td align='center' scope='col' valign='bottom'>&nbsp;</td> <td scope='col' valign='bottom'>&nbsp;</td> </tr> </tbody> </table> </td> </tr> <tr> <td align='center' id='footer' style='margin:0px; padding:10px'>&nbsp;</td> </tr> <tr> <td></td> </tr> </tbody> </table> </div> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table>";
		
		boolean sucess=false;
		int i=0;
		//Existem 3 tentativas de envio de email 
		while (!sucess && i<3) {
			try {
				Message message = new MimeMessage(session);
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ALERT_EMAIL));
				message.setContent(conteudo, "text/html; charset=utf-8");
				Transport.send(message);
				sucess=true;
			} catch (MessagingException e) {
				sucess=false;
				Logger.getLogger(MyWsProjectMail.class.getName()).log(Level.WARNING, "Cannot send mail", e);
			}
			i++;
		}
	}

}
