package pt.uc.dei.ws.bridges;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.uc.dei.itf.dtos.SessionDto;
import pt.uc.dei.itf.dtos.UserLoggedDto;
import pt.uc.dei.itf.dtos.UserLoginDto;
import pt.uc.dei.itf.dtos.UserNewDto;
import pt.uc.dei.itf.dtos.UserProfileDto;
import pt.uc.dei.itf.dtos.UserUpdateSelfDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.beans.MySessionBean;
import pt.uc.dei.ws.util.ErrorsHandler;

@Stateless
public class UserBridge implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String URI="http://localhost:8080/piscina-ds/user";
	//private final String URI="https://localhost:8443/aclgomes-rnavalho-project-ds/user";

	@Inject
	private MySessionBean mySessionBean;
	
	@Inject
	private ErrorsHandler errorsHandler;

	public UserBridge() {
	}

	public List<UserProfileDto> userAdvancedSearch(String type, String searchText, Date date) {
		
		try {
			List<UserProfileDto> list= null;
			Response response = null;
			if (type.equals("date")){
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				searchText= sdf.format(date);
			}	
			Client client = ClientBuilder.newClient();
			try {
				response = client.target(URI).path("advancedSearch").path(type).path(URLEncoder.encode(searchText, "UTF-8").replace("+", "%20")).request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<UserProfileDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}
	
	public boolean forgotPassword(String email) {
		try {
			Response response = null;
			boolean validEmail=false;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/forgotPassword").request(MediaType.APPLICATION_JSON).put(Entity.json(email), Response.class);	
			if (response.getStatus()==200) {
				validEmail = response.readEntity(Boolean.class);
			}
			client.close();
			return validEmail;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return false;
		}
	}

	public Response confirmRegistration(String token) {
		try {
			Response response = null;
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/confirmRegister").request(MediaType.APPLICATION_JSON).put(Entity.json(token), Response.class);	
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	//TODO mudar este metodo para enviar ou DTO em vez de uma String????????
	public boolean submitNewPassword(String token, String passw) {
		try {
			Response response = null;
			boolean sucess=false;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/resetPassword").request(MediaType.APPLICATION_JSON).header("token", token).put(Entity.json(passw), Response.class);	
			if (response.getStatus()==200) {
				sucess = response.readEntity(Boolean.class);
			}
			client.close();
			if (sucess) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return false;
		}
	}

	public SessionDto loginUser(String email, String password) {
		System.out.println("loginUser1");
		try {
			Response response = null;
			SessionDto sessionDto=null;
			UserLoginDto userLoginDto = new UserLoginDto(email, password);
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/login").request(MediaType.APPLICATION_JSON).put(Entity.json(userLoginDto), Response.class);	
			
			if (response.getStatus()==200) {
				System.out.println("loginUser response.getStatus()==200");
				sessionDto = response.readEntity(SessionDto.class);
			} else {
				System.out.println("loginUser response.getStatus()<> 200");
				List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
				errorsHandler.loginUser(false, errors);
			}
			client.close();
			System.out.println("loginUser2");
			return sessionDto;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}	
	}
	
	public SessionDto loginUserWithGoogle(String googleToken) {
		try {
			Response response = null;
			SessionDto sessionDto=null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/loginWithGoogle").request(MediaType.APPLICATION_JSON).put(Entity.json(googleToken), Response.class);		
			if (response.getStatus()==200) {
				sessionDto = response.readEntity(SessionDto.class);
			} else {
				List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
				errorsHandler.loginUserWithGoogle(false, errors);
			}
			client.close();
			return sessionDto;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}	
	}

	public UserLoggedDto getMyUserData() {
		try {
			UserLoggedDto userLoggedDto = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI);
			userLoggedDto = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(UserLoggedDto.class);
			client.close();
			return userLoggedDto;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public List<UserProfileDto> getAllUsers() {
		try {
			List<UserProfileDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getAll").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<UserProfileDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public List<UserProfileDto> getManagersList() {
		try {
			List<UserProfileDto> list= null ;
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();//TODO Usar ou nao usar SSL???
			Client client = ClientBuilder.newClient();
			response = client.target(URI).path("/getUsersToManager").request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			if (response.getStatus()==200) {
				list = response.readEntity(new GenericType<List<UserProfileDto>>() {});
			}
			client.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			mySessionBean.sendDataServerDownAlert();
			return null;
		}
	}

	public Response createNewUser(UserNewDto userNewDto) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("/create");
			response = myResource.request(MediaType.APPLICATION_JSON).post(Entity.json(userNewDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response updateUserProfile(UserProfileDto userProfileDto) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("/updateProfile");
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).put(Entity.json(userProfileDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	public Response updateSelfProfile(UserUpdateSelfDto userUpdateSelfDto, String token) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("/updateSelf");
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", token).put(Entity.json(userUpdateSelfDto), Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}

	//TODO nao preciso da task para nada ??????
	public Response getAvailableWorkersToTask(int taskId, Date beginDate, Date endDate) {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String begin= sdf.format(beginDate);
			String end= sdf.format(endDate);
			WebTarget myResource = client.target(URI).path("getWorkersToDateInterval").path(taskId+"").path(begin).path(end);
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}


	public Response getWorkRegisterList() {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("/getWorkRegister").path(mySessionBean.getLoggedUser().getEmail());
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}
	
	public Response requestRoleAtribution() {
		try {
			Response response = null;
			//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
			Client client = ClientBuilder.newClient();
			WebTarget myResource = client.target(URI).path("requestRoleAtribution");
			response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
			client.close();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return mySessionBean.sendDataServerDownAlert();
		}
	}


//	public Response getWorkRegisterToTask(int taskId) {
//		Response response = null;
//		//Client client = ClientBuilder.newBuilder().sslContext(getSSLContext()).build();
//		Client client = ClientBuilder.newClient();
//		WebTarget myResource = client.target(URI).path("/getWorkRegisterToTask").path(mySessionBean.getLoggedUser().getEmail()).path(taskId+"");
//		response = myResource.request(MediaType.APPLICATION_JSON).header("token", mySessionBean.getToken()).get(Response.class);
//		client.close();
//		return response;
//	}

	/////////////////////////////////////////////////
	////////////////////// SSL //////////////////////
	/////////////////////////////////////////////////

	private static TrustManager[] trustAllCerts = new X509TrustManager[] {
			new X509TrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} };

	private static SSLContext getSSLContext() {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		} catch (GeneralSecurityException e) {
		}
		return sc;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public static TrustManager[] getTrustAllCerts() {
		return trustAllCerts;
	}

	public MySessionBean getMySessionBean() {
		return mySessionBean;
	}

	public void setMySessionBean(MySessionBean mySessionBean) {
		this.mySessionBean = mySessionBean;
	}

	public static void setTrustAllCerts(TrustManager[] trustAllCerts) {
		UserBridge.trustAllCerts = trustAllCerts;
	}

	public String getURI() {
		return URI;
	}










}
